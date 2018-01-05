package br.com.xbrain.eccp2java.entity.xml;

import br.com.xbrain.eccp2java.util.EccpUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "loginagent")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpLoginAgentRequest extends EccpAbstractRequest {

    public static EccpLoginAgentRequest create(
            String agentNumber,
            String password,
            String appCookie,
            Integer extension) {

        return new EccpLoginAgentRequest(agentNumber, password, appCookie, extension);
    }

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    @Getter
    @Setter
    @XmlElement(name = "password")
    private String password;

    @XmlTransient
    private String appCookie;

    /**
     * Gerado automaticamente a partir do appCookie.
     */
    @XmlElement(name = "agent_hash")
    private String agentHash;

    @Getter
    @Setter
    @XmlElement(name = "extension")
    private Integer extension;

    public final String getAgentHash() {
        if (agentHash == null) {
            agentHash = EccpUtils.generateAgentHash(agentNumber, password, appCookie);
        }
        return agentHash;
    }

    private EccpLoginAgentRequest() {
    }

    private EccpLoginAgentRequest(String agentNumber, String password, String appCookie, Integer extension) {
        this.agentNumber = agentNumber;
        this.password = password;
        this.appCookie = appCookie;
        this.extension = extension;
        this.agentHash = getAgentHash();
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpLoginAgentResponse.class;
    }
}
