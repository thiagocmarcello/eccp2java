package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.*;
import br.com.xbrain.eccp2java.exception.EccpException;
import lombok.Getter;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EccpClient implements IEccpCallback, Serializable, AutoCloseable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(EccpClient.class.getName());

    private final Map<Class<? extends IEccpEvent>, Set<IEccpEventListener<IEccpEvent>>> eventListeners = new HashMap<>();

    private final Set<AgentConsole> loggedAgentConsoles = new HashSet<>();

    private SocketConnection socketConnection;

    @Getter
    private final Elastix elastix;

    public EccpClient(Elastix elastix) {
        LOG.info("Iniciando EccpClient...");
        this.elastix = elastix;
    }

    public final boolean isConnected() {
        return socketConnection != null && socketConnection.isConnected();
    }


    public void connect() throws EccpException {
        LOG.info("Criando socket de conexão...");
        socketConnection = SocketConnection.connect(this);
    }

    public synchronized AgentConsole createAgentConsole(String agentNumber, String password, Integer extension)
            throws EccpException {
        AgentConsole console = new AgentConsole(
                this,
                agentNumber,
                password,
                extension,
                socketConnection.getAppCookie());
        loggedAgentConsoles.add(console);
        notify();
        return console;
    }

    public void addEventListener(Class<? extends IEccpEvent> clss, IEccpEventListener<IEccpEvent> listener) {
        synchronized (eventListeners) {
            if (!eventListeners.containsKey(clss)) {
                eventListeners.put(clss, new HashSet<>());
            }
            eventListeners.get(clss).add(listener);
        }
    }

    public void removeEventListener(Class<? extends IEccpEvent> clss, IEccpEventListener<IEccpEvent> listener) {
        if (eventListeners.containsKey(clss)) {
            for (Iterator<IEccpEventListener<IEccpEvent>> it = eventListeners.get(clss).iterator(); it.hasNext(); ) {
                IEccpEventListener<IEccpEvent> current = it.next();
                if (current.equals(listener)) {
                    it.remove();
                }
            }
        }
    }

    // FIXME propagar o evento se for do agente e foda-se o mundo.
    private void fireEvent(IEccpEvent event) {
        if (eventListeners.containsKey(null)) {
            eventListeners.get(null).forEach((item) -> {
                item.onEvent(event);
            });
        }

        if (eventListeners.containsKey(event.getClass())) {
            eventListeners.get(event.getClass()).forEach((item) -> {
                item.onEvent(event);
            });
        }
    }

    public IEccpResponse send(IEccpRequest request) throws EccpException {
        return socketConnection.send(request);
    }

    @Override
    public void onEvent(IEccpEvent event) {
        fireEvent(event);
    }

    void removeAgentConsole(AgentConsole agentConsole) {
        LOG.log(Level.INFO, "Removendo agentConsole: {0}", new Object[]{agentConsole});

        synchronized (loggedAgentConsoles) {
            boolean removed = loggedAgentConsoles.remove(agentConsole);
            LOG.info(removed ? "Removido" : "Não removido");
        }
    }

    @Override
    public synchronized void checkAgentConsoles() {
        LOG.info("Sincronizando agentConsoles...");
        if (loggedAgentConsoles.stream().filter(AgentConsole::isConnected).count() == 0) {
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
        try {
            LOG.info("Encerrando EccpClient");
            socketConnection.close();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Problema ao encerrar SocketReaderAgent: " + ex.getMessage(), ex);
        }
    }
}
