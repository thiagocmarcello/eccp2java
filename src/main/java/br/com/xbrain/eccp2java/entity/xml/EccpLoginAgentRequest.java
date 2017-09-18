package br.com.xbrain.eccp2java.entity.xml;

import br.com.xbrain.eccp2java.util.EccpUtils;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "loginagent")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpLoginAgentRequest extends EccpAbstractRequest {

    public static EccpLoginAgentRequest create(Long id, String agentNumber, String password, String appCookie,
            Integer extension) {
        return new EccpLoginAgentRequest(id, agentNumber, password, appCookie, extension);
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
     * Gerado automaticamente a partir do appCookie
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

    private EccpLoginAgentRequest(Long id, String agentNumber, String password, String appCookie, Integer extension) {
        this.id = id;
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
