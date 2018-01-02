package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;


public class EccpClient implements IEccpCallback, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(EccpClient.class.getName());

    private final Map<Class<? extends IEccpEvent>, Set<IEccpEventListener<IEccpEvent>>> eventListeners = new HashMap<>();

    private final Set<AgentConsole> loggedAgentConsoles = new HashSet<>();

    private SocketListener socketListener;

    private Thread thread;

    @Getter
    private final Elastix elastix;

    public EccpClient(Elastix elastix) {
        LOG.info("Iniciando EccpClient...");
        this.elastix = elastix;
    }

    public final boolean isStarted() {
        return socketListener != null && socketListener.isConnected();
    }

    public final void startSocketListener() throws EccpException {
        LOG.info("Iniciando SocketListener...");
        if (thread != null && thread.isAlive()) {
            throw new IllegalStateException("O listener já foi iniciado");
        }

        if (socketListener == null) {
            socketListener = SocketListener.create(elastix, this);
            socketListener.connect();
        }

        thread = new Thread(socketListener);
        thread.start();
    }

    public SocketConnection connect() throws EccpException {
        LOG.info("Criando socket de conexão...");
        SocketConnection socketConnection = SocketConnection.create(elastix);
        socketConnection.connect();
        return socketConnection;
    }

    public void disconnect() throws EccpException {
        LOG.info("Encerrando EccpClient");
        socketListener.stop();
    }

    public synchronized AgentConsole createAgentConsole(String agentNumber, String password, Integer extension) throws EccpException {
        AgentConsole console = new AgentConsole(this, agentNumber, password, extension);
        console.connectAgentConsole();
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
            for (Iterator<IEccpEventListener<IEccpEvent>> it = eventListeners.get(clss).iterator(); it.hasNext();) {
                IEccpEventListener<IEccpEvent> current = it.next();
                if (current.equals(listener)) {
                    it.remove();
                }
            }
        }
    }

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

    @Override
    public void sendEvent(IEccpEvent event) {
        fireEvent(event);
    }

    void removeAgentConsole(AgentConsole agentConsole) {
        LOG.log(Level.INFO, "Removendo agentConsole: {0}", new Object[]{agentConsole});

        synchronized (loggedAgentConsoles) {
            boolean removed = loggedAgentConsoles.remove(agentConsole);
            LOG.info(removed ? "Removido." : "Não removido.");
        }
    }

    @Override
    public synchronized void checkAgentConsoles() {
        LOG.info("Sincronizando agentConsoles...");
        if (loggedAgentConsoles.stream().filter(AgentConsole::isConnected).count() == 0) {
            try {
                LOG.info("SocketListener waiting.");
                wait();
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }
}
