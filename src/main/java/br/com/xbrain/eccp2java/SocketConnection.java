package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.DocumentUtils;
import br.com.xbrain.eccp2java.util.StreamUtils;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginRequest;
import br.com.xbrain.eccp2java.entity.xml.IEccpRequest;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lombok.Getter;
import org.w3c.dom.Document;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class SocketConnection implements AutoCloseable {

    private static final Logger LOG = Logger.getLogger(SocketConnection.class.getName());

    public static final String RESPONSE_END_TAG = "</response>";

    public static final String EVENT_END_TAG = "</event>";

    public static SocketConnection create(Elastix elastix) {
        return new SocketConnection(elastix);
    }

    private Socket socket;

    @Getter
    private InputStream inputStream;

    @Getter
    private OutputStream outputStream;

    @Getter
    private final Elastix elastix;

    @Getter
    private final Long timeout;

    private SocketConnection(Elastix elastix) {
        this.elastix = elastix;
        this.timeout = 10000L;
    }

    public void connect() throws EccpException {
        try {
            socket = new Socket(elastix.getHost(), elastix.getPort());
            socket.setSoTimeout(Integer.parseInt(timeout.toString()));
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            throw EccpException.create(EccpException.Error.SERVICE_UNAVAILABLE, ex);
        }
    }

    public void disconnect() {
        StreamUtils.close(inputStream, outputStream, socket);
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    @Override
    public void close() throws Exception {
        disconnect();
    }

    public IEccpResponse send(IEccpRequest request) throws EccpException {
        try {
            EccpRequestFactory<EccpLoginRequest> xmlCreator = EccpRequestFactory.create(request);
            Transformer transformer = DocumentUtils.createTransformer();
            DOMSource source = new DOMSource(xmlCreator.toDocument());
            StreamResult result1 = new StreamResult(socket.getOutputStream());
            transformer.transform(source, result1);
            Document responseDocument = DocumentUtils.parseDocument(readResponse());
            //return new EccpResponseFactory(responseDocument).createResponse();
            int retries = 0;
            while (retries++ < 3) {
                try {
                    return request.expectedResponseType().cast(
                            new EccpResponseFactory(responseDocument).createResponse());
                } catch (ClassCastException | EccpException ex) {
                    LOG.log(Level.WARNING,
                            "Tipo inesperado para o response {0}. Retentando: {1}.", new Object[]{ex.getMessage(), retries});
                }
            }
        } catch (IllegalArgumentException | IOException | TransformerException ex) {
            throw EccpException.create(EccpException.Error.CAN_NOT_SEND, ex);
        }

        throw EccpException.create(EccpException.Error.RESPONSE_NOT_FOUND);
    }

    // TODO precisa de um timeout
    private String readResponse() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
                LOG.log(Level.INFO, "read: {0}", line);
                if (line.endsWith(RESPONSE_END_TAG)) {
                    break;
                }
            }
            return filterTag(result.toString(), RESPONSE_END_TAG);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return result.toString();
    }

    public static String filterTag(String response, String filterTag) {
        int begin = response.lastIndexOf("<?xml");
        int end = response.lastIndexOf(filterTag);
        if (begin * end < 0) {
            throw new IllegalArgumentException(response + " não é uma resposta válida");
        }
        return response.substring(begin, end + filterTag.length());
    }
}
