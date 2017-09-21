package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.EccpUtils;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentRequest;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginRequest;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginResponse;
import br.com.xbrain.eccp2java.entity.xml.EccpLogoutAgentRequest;
import br.com.xbrain.eccp2java.entity.xml.EccpLogoutAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.IEccpRequest;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@EqualsAndHashCode(of = "agentNumber")
public class AgentConsole {

    private static final Logger LOG = Logger.getLogger(AgentConsole.class.getName());

    private final EccpClient eccp;

    private SocketConnection connection;

    private long requestId = 1L;

    @Getter
    private final String agentNumber;

    private final String password;

    @Getter
    private final Integer extension;

    @Getter
    private String appCookie;

    @Getter
    private String agentHash;

    public AgentConsole(EccpClient eccp, String agentNumber, String password, Integer extension) {
        this.eccp = eccp;
        this.agentNumber = agentNumber;
        this.password = password;
        this.extension = extension;
    }

    private long nextId() {
        return ++requestId;
    }

    private void connect() throws EccpException {
        LOG.log(Level.INFO, "Connecting agent: {0}.", agentNumber);
        this.connection = eccp.connect();
    }

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    public IEccpResponse send(IEccpRequest request) throws EccpException {
        LOG.log(Level.INFO, "Sending {0}...", request);
        request.setId(nextId());
        IEccpResponse response = connection.send(request);
        return response;
    }

    public EccpLoginResponse connectAgentConsole() throws EccpException {
        connect();
        EccpLoginRequest request = EccpLoginRequest.create(
                eccp.getElastix().getUser(),
                eccp.getElastix().getPassword());
        EccpLoginResponse response = (EccpLoginResponse) send(request);
        appCookie = response.getAppCookie();
        return response;
    }

    public EccpLoginAgentResponse loginAgent() throws EccpException {
        EccpLoginAgentRequest request = EccpLoginAgentRequest.create(
                nextId(),
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
                nextId(),
                agentNumber,
                EccpUtils.generateAgentHash(agentNumber, password, appCookie));
        EccpLogoutAgentResponse response = (EccpLogoutAgentResponse) send(request);
        eccp.removeAgentConsole(this);
        closeConnection();
        return response;
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Problem closing connection.", ex);
        }
    }
}