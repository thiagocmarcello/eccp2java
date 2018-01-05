package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.EccpLoginRequest;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginResponse;
import br.com.xbrain.eccp2java.entity.xml.IEccpRequest;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.DocumentUtils;
import br.com.xbrain.eccp2java.util.StreamUtils;
import lombok.Getter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketConnection implements AutoCloseable {

    public static final Logger LOG = Logger.getLogger(SocketConnection.class.getName());
    public static final long DEFAULT_SOCKET_TIMEOUT = 10000L;
    public static final int DEFAULT_MAX_RETRIES = 3;

    public static SocketConnection connect(EccpClient eccpClient) throws EccpException {
        SocketConnection socketConnection = new SocketConnection(eccpClient);
        socketConnection.connect();
        return socketConnection;
    }

    private Socket socket;

    @Getter
    private String appCookie;

    @Getter
    private InputStream inputStream;

    @Getter
    private OutputStream outputStream;

    private SocketReaderAgent socketReaderAgent;

    @Getter
    private final EccpClient eccpClient;

    private final EccpResponseHeap responseHeap = EccpResponseHeap.instance();

    @Getter
    private final Long timeout;

    private final Transformer transformer = DocumentUtils.createTransformer();

    private SocketConnection(EccpClient eccpClient) {
        this.eccpClient = eccpClient;
        this.timeout = DEFAULT_SOCKET_TIMEOUT;
    }

    public void connect() throws EccpException {
        try {
            socket = new Socket(eccpClient.getElastix().getHost(), eccpClient.getElastix().getPort());
            socket.setSoTimeout(Integer.parseInt(timeout.toString()));
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            startSocketListener();
            login();
        } catch (IOException ex) {
            throw new EccpException("Serviço indisponível", ex);
        }
    }

    private void startSocketListener() {
        LOG.info("Iniciando SocketReaderAgent...");
        if (socketReaderAgent == null) {
            socketReaderAgent = SocketReaderAgent.start(this);
        }
    }

    private void login() throws EccpException {
        EccpLoginRequest loginRequest = EccpLoginRequest.create(
                eccpClient.getElastix().getUser(),
                eccpClient.getElastix().getPassword());
        EccpLoginResponse loginResponse = (EccpLoginResponse) send(loginRequest);
        this.appCookie = loginResponse.getAppCookie();
        if (loginResponse.isFailure()) {
            throw new EccpException("Problema ao conectar com o Elastix: " + loginResponse, null);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public IEccpResponse send(IEccpRequest request) throws EccpException {
        try {
            DOMSource source = parseEccpRequestIntoDomSource(request);
            writeRequestToEccpOutput(source);
        } catch (IllegalArgumentException | IOException | TransformerException ex) {
            throw new EccpException("Não foi possível enviar o request: " + request, ex);
        }

        return waitResponse(request);
    }

    private DOMSource parseEccpRequestIntoDomSource(IEccpRequest request) throws EccpException {
        EccpRequestFactory<?> xmlCreator = EccpRequestFactory.create(request);
        return new DOMSource(xmlCreator.toDocument());
    }

    private void writeRequestToEccpOutput(DOMSource parsedIntoDomRequest) throws IOException, TransformerException {
        StreamResult result1 = new StreamResult(socket.getOutputStream());
        transformer.transform(parsedIntoDomRequest, result1);
    }

    private IEccpResponse waitResponse(IEccpRequest request) {
        int retries = 0;
        IEccpResponse response = null;
        while (retries++ < DEFAULT_MAX_RETRIES) {
            synchronized (responseHeap) {
                response = responseHeap.retrieve(request.getId());
                if (response == null) {
                    try {
                        responseHeap.wait();
                    } catch (InterruptedException ex) {
                        LOG.log(Level.SEVERE, "Pau!", ex);
                    }
                } else {
                    break;
                }
            }
        }
        return response;
    }

    @Override
    public void close() {
        try {
            socketReaderAgent.stop();
            StreamUtils.close(inputStream, outputStream, socket);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Erro ao encerrar a SocketConnection", ex);
        }
    }
}
