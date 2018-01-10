package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.*;
import br.com.xbrain.eccp2java.exception.EccpException;
import lombok.Getter;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EccpClient implements IEccpCallback, Serializable, AutoCloseable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(EccpClient.class.getName());

    private final Map<String, AgentConsole> loggedAgentConsoles = new HashMap<>();

    private SocketConnection socketConnection;

    @Getter
    private final ElastixLoginData elastixLoginData;

    public EccpClient(ElastixLoginData elastixLoginData) {
        LOG.info("Iniciando EccpClient...");
        this.elastixLoginData = elastixLoginData;
    }

    public boolean isConnected() {
        return socketConnection != null && socketConnection.isConnected();
    }

    public EccpClient connect() throws EccpException {
        LOG.info("Criando socket de conexão...");
        socketConnection = SocketConnection.connect(this);
        return this;
    }

    public synchronized AgentConsole createAgentConsole(String agentNumber, String password, Integer extension) {
        ensureConnected();
        AgentConsole console = new AgentConsole(
                this,
                agentNumber,
                password,
                extension,
                socketConnection.getAppCookie());
        loggedAgentConsoles.put(agentNumber, console);
        notify();
        return console;
    }

    private void fireEvent(IEccpEvent event) {
        if (event instanceof IEccpAgentEvent) {
            IEccpAgentEvent agentEvent = (IEccpAgentEvent) event;
            String agentNumber = agentEvent.getAgentNumber();
            AgentConsole console = loggedAgentConsoles.get(agentNumber);
            if (console != null) {
                console.fireEvent(agentEvent);
            }
        } else {
            loggedAgentConsoles.values().forEach(console -> console.fireEvent(event));
        }
    }

    public IEccpResponse send(IEccpRequest request) throws EccpException {
        ensureConnected();
        return socketConnection.send(request);
    }

    @Override
    public void onEvent(IEccpEvent event) {
        if (!loggedAgentConsoles.isEmpty()) {
            fireEvent(event);
        }
    }

    public void removeAgentConsole(AgentConsole agentConsole) {
        LOG.log(Level.INFO, "Removendo agentConsole: {0}", new Object[]{agentConsole});

        AgentConsole console = null;
        synchronized (loggedAgentConsoles) {
            console = loggedAgentConsoles.remove(agentConsole.getAgentNumber());
        }

        if (console != null) {
            console.disconnect();
        }
    }

    @Override
    public synchronized void checkAgentConsoles() {
        LOG.info("Sincronizando agentConsoles...");
        if (loggedAgentConsoles.values().stream().filter(AgentConsole::isConnected).count() == 0) {
            try {
                LOG.info("SocketReaderAgent waiting");
                wait();
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void close() throws IOException {
        ensureConnected();
        try {
            LOG.info("Encerrando EccpClient");
            socketConnection.close();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Problema ao encerrar SocketReaderAgent: " + ex.getMessage(), ex);
            System.exit(1);
        }
    }

    private void ensureConnected() {
        if (socketConnection == null || !socketConnection.isConnected()) {
            throw new IllegalStateException("EccpClient não está connectado.");
        }
    }
}
