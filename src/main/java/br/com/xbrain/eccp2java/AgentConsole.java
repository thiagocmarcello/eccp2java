package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.*;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.EccpUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@EqualsAndHashCode(of = "agentNumber")
public class AgentConsole {

    private static final Logger LOG = Logger.getLogger(AgentConsole.class.getName());

    private final EccpClient eccpClient;

    private final String password;

    @Getter
    private final String agentNumber;

    @Getter
    private final Integer extension;

    @Getter
    private final String appCookie;

    @Getter
    private String agentHash;

    private final Set<IEccpEventListener> eventListeners = new HashSet<>();

    AgentConsole(EccpClient eccp, String agentNumber, String password, Integer extension, String appCookie) {
        this.eccpClient = eccp;
        this.agentNumber = agentNumber;
        this.password = password;
        this.extension = extension;
        this.appCookie = appCookie;
    }

    public boolean isConnected() {
        return eccpClient.isConnected();
    }

    public IEccpResponse send(IEccpRequest request) throws EccpException {
        LOG.log(Level.INFO, "Sending {0}...", request);
        return eccpClient.send(request);
    }

    public EccpLoginAgentResponse loginAgent() throws EccpException {
        EccpLoginAgentRequest request = EccpLoginAgentRequest.create(
                agentNumber,
                password,
                appCookie,
                extension);
        agentHash = request.getAgentHash();
        IEccpResponse response = send(request);
        return EccpLoginAgentResponse.class.cast(response);
    }

    public EccpLogoutAgentResponse logoutAgent() throws EccpException {
        EccpLogoutAgentRequest request = EccpLogoutAgentRequest.create(
                agentNumber,
                EccpUtils.generateAgentHash(agentNumber, password, appCookie));
        EccpLogoutAgentResponse response = (EccpLogoutAgentResponse) send(request);
        eccpClient.removeAgentConsole(this);
        return response;
    }

    void fireEvent(IEccpEvent event) {
        eventListeners.forEach(listener -> listener.onEvent(event));
    }

    public void addEventListener(IEccpEventListener listener) {
        eventListeners.add(listener);
    }
}