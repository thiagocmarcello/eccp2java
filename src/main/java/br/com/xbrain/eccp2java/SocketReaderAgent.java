package br.com.xbrain.eccp2java;

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


    public static SocketReaderAgent start(SocketConnection socketConnection) {
        if(!socketConnection.isConnected()) {
            throw new IllegalStateException("A socket connection não está inicializada");
        }
        SocketReaderAgent socketReaderAgent = new SocketReaderAgent(socketConnection);
        socketReaderAgent.runningThread = new Thread(socketReaderAgent);
        socketReaderAgent.runningThread.start();
        return socketReaderAgent;
    }

    private SocketConnection socketConnection;

    private boolean keepOnRunning = true;

    private Thread runningThread;

    private final EccpClient eccpClient;

    private final EccpResponseHeap responseHeap = EccpResponseHeap.instance();

    private SocketReaderAgent(SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
        this.eccpClient = socketConnection.getEccpClient();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()))) {
            String line;
            StringBuilder result = new StringBuilder();
            int part = 0;
            while (keepOnRunning) try {
                while ((line = br.readLine()) != null) {
                    result.append(line).append(SYSTEM_LINE_SEPARATOR);
                    LOG.log(Level.INFO, "read-part {0}: {1}", new Object[]{++part, line});
                    if (line.endsWith(EVENT_END_TAG)) {
                        LOG.log(Level.INFO, "read-full: {0}", result.toString());
                        String eventString = filterTag(result.toString(), EVENT_END_TAG);
                        eccpClient.onEvent(
                                new EccpAgentEventFactory().create(DocumentUtils.parseDocument(eventString)));
                        result = new StringBuilder();
                        part = 0;
                    } else if (line.endsWith(RESPONSE_END_TAG)) {
                        LOG.info("Reading response...");
                        String responseString = filterTag(result.toString(), RESPONSE_END_TAG);
                        Document responseDocument = DocumentUtils.parseDocument(responseString);
                        responseHeap.add(new EccpResponseFactory(responseDocument).createResponse());
                    }
                }
            } catch (SocketTimeoutException ex) {
                eccpClient.checkAgentConsoles();
                LOG.log(Level.INFO, "{0} - SocketReaderAgent timedout: {1}",
                        new Object[]{Thread.currentThread().getName(), ex.getMessage()});
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage() == null ? ex.toString() : ex.getMessage(), ex);
        } finally {
            eccpClient.onEvent(new EccpConnectionClosedEvent());
            socketConnection.close();
        }
    }

    private static String filterTag(String response, String filterTag) {
        int begin = response.lastIndexOf("<?xml");
        int end = response.lastIndexOf(filterTag);
        if (begin * end < 0) {
            throw new IllegalArgumentException(response + " não é uma resposta válida");
        }
        return response.substring(begin, end + filterTag.length());
    }

    public void stop() throws Exception {
        keepOnRunning = false;
    }
}