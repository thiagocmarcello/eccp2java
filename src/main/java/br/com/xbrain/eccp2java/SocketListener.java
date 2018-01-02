package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.DocumentUtils;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginRequest;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class SocketListener implements Runnable {

    private static final Logger LOG = Logger.getLogger(SocketListener.class.getName());

    private static final String SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator");

    public static SocketListener create(Elastix elastix, IEccpCallback callback) {
        return new SocketListener(elastix, callback);
    }

    private SocketConnection socketConnection;

    private boolean keepOnRunning = true;

    private final Elastix elastix;

    private final IEccpCallback callback;

    private SocketListener(Elastix elastix, IEccpCallback callback) {
        this.elastix = elastix;
        this.callback = callback;
    }

    void connect() throws EccpException {
        socketConnection = SocketConnection.create(elastix);
        socketConnection.connect();
        login();
    }

    public boolean isConnected() {
        return socketConnection != null && socketConnection.isConnected();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()))) {
            String line;
            StringBuilder result = new StringBuilder();
            int part = 0;
            while (keepOnRunning) {
                try {
                    while ((line = br.readLine()) != null) {
                        result.append(line).append(SYSTEM_LINE_SEPARATOR);
                        LOG.log(Level.INFO, "read-part {0}: {1}", new Object[]{++part, line});
                        if (line.endsWith(SocketConnection.EVENT_END_TAG)) {
                            LOG.log(Level.INFO, "read-full: {0}", result.toString());
                            String eventString = SocketConnection.filterTag(result.toString(), SocketConnection.EVENT_END_TAG);
                            callback.sendEvent(new EccpAgentEventFactory().create(DocumentUtils.parseDocument(eventString)));
                            result = new StringBuilder();
                            part = 0;
                        }
                    }
                } catch (SocketTimeoutException ex) {
                    callback.checkAgentConsoles();
                    LOG.log(Level.INFO, "{0} - SocketListener timedout: {1}",
                            new Object[]{Thread.currentThread().getName(), ex.getMessage()});
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage() == null ? ex.toString() : ex.getMessage(), ex);
        } finally {
            callback.sendEvent(new EccpConnectionClosedEvent());
            socketConnection.disconnect();
        }
    }

    private void login() throws EccpException {
        EccpLoginRequest loginRequest = EccpLoginRequest.create(elastix.getUser(), elastix.getPassword());
        loginRequest.setId(1L);
        EccpLoginResponse loginResponse = (EccpLoginResponse) socketConnection.send(loginRequest);
        if (loginResponse.isFailure()) {
            throw EccpException.create(EccpException.Error.LOGIN_FAILED)
                    .addInfo("code", loginResponse.getFailure().getCode())
                    .addInfo("message", loginResponse.getFailure().getMessage());
        }
    }

    public void stop() {
        socketConnection.disconnect();
        keepOnRunning = false;
    }
}