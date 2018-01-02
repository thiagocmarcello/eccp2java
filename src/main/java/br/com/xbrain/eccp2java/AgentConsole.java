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
    private String appCookie;

    @Getter
    private String agentHash;

    public AgentConsole(EccpClient eccp, String agentNumber, String password, Integer extension, String appCookie) {
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
        IEccpResponse response = eccpClient.send(request);
        return response;
    }

    // FIXME isso deveria estar dentro do EccpClient
//    public EccpLoginResponse connectAgentConsole() throws EccpException {
//        connectEccp();
//        EccpLoginRequest request = EccpLoginRequest.start(
//                eccpClient.getElastix().getUser(),
//                eccpClient.getElastix().getPassword());
//        EccpLoginResponse response = (EccpLoginResponse) send(request);
//        appCookie = response.getAppCookie();
//        return response;
//    }

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
}