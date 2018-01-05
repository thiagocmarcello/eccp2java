package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.EccpLogoutRequest;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.DocumentUtils;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketReaderAgent implements Runnable {

    private static final Logger LOG = Logger.getLogger(SocketReaderAgent.class.getName());

    private static final String SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator");

    private static final String RESPONSE_END_TAG = "</response>";

    private static final String EVENT_END_TAG = "</event>";
    public static final int DEFAULT_THREAD_FINISHING_WAIT_TIME = 5000;

    static SocketReaderAgent start(SocketConnection socketConnection) {
        if (!socketConnection.isConnected()) {
            throw new IllegalStateException("A socket connection não está inicializada");
        }
        SocketReaderAgent socketReaderAgent = new SocketReaderAgent(socketConnection);
        socketReaderAgent.runningThread = new Thread(socketReaderAgent);
        socketReaderAgent.runningThread.start();
        return socketReaderAgent;
    }

    private final SocketConnection socketConnection;

    private boolean keepOnRunning = true;

    private Thread runningThread;

    private final EccpClient eccpClient;

    private final EccpResponseHeap responseHeap = EccpResponseHeap.instance();

    private SocketReaderAgent(SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
        this.eccpClient = socketConnection.getEccpClient();
    }

    @Override
    @SuppressWarnings("checkstyle:MethodLength")
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()))) {
            String line;
            StringBuilder result = new StringBuilder();
            while (mustKeepOnRunning()) {
                try {
                    while ((line = br.readLine()) != null) {
                        result.append(line).append(SYSTEM_LINE_SEPARATOR);
                        LOG.log(Level.INFO, "read: {1}", new Object[]{line});
                        if (line.endsWith(EVENT_END_TAG)) {
                            parseEvent(result);
                        } else if (line.endsWith(RESPONSE_END_TAG)) {
                            parseResponse(result);
                        }
                    }
                    result = new StringBuilder();
                } catch (SocketTimeoutException ex) {
                    eccpClient.checkAgentConsoles();
                    LOG.log(Level.INFO, "{0} - SocketReaderAgent timedout: {1}",
                            new Object[]{Thread.currentThread().getName(), ex.getMessage()});
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage() == null ? ex.toString() : ex.getMessage(), ex);
        } finally {
            eccpClient.onEvent(new EccpConnectionClosedEvent());
            LOG.info("SocketReaderAgent terminou.");
        }
    }

    private void parseResponse(StringBuilder result) throws EccpException {
        LOG.info("Reading response...");
        String responseString = filterTag(result.toString(), RESPONSE_END_TAG);
        Document responseDocument = DocumentUtils.parseDocument(responseString);
        responseHeap.add(new EccpResponseFactory(responseDocument).createResponse());
    }

    private void parseEvent(StringBuilder result) throws EccpException {
        LOG.log(Level.INFO, "read-full: {0}", result.toString());
        String eventString = filterTag(result.toString(), EVENT_END_TAG);
        eccpClient.onEvent(
                new EccpAgentEventFactory().create(DocumentUtils.parseDocument(eventString)));
    }

    private boolean mustKeepOnRunning() {
        return keepOnRunning && socketConnection.isConnected();
    }

    private static String filterTag(String response, String filterTag) {
        int begin = response.lastIndexOf("<?xml");
        int end = response.lastIndexOf(filterTag);
        if (begin * end < 0) {
            throw new IllegalArgumentException(response + " não é uma resposta válida");
        }
        return response.substring(begin, end + filterTag.length());
    }

    void stop() {
        try {
            keepOnRunning = false;
            sendCloseSignal();
            runningThread.join(DEFAULT_THREAD_FINISHING_WAIT_TIME);
        } catch (Exception ex) {
            LOG.severe("O agente foi interrompido à força: " + ex.getMessage());
        }
    }

    private void sendCloseSignal() throws EccpException {
        socketConnection.send(EccpLogoutRequest.create());
    }
}