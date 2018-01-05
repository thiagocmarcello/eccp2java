package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <request id="3">
 *     <logoutagent>
 *         <agent_number>Agent/9000</agent_number>
 *         <agent_hash>XXXXXXXXXXXXXXXXXXXXXXXXX</agent_hash>
 *     </logoutagent>
 * </request>
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "logoutagent")
@XmlAccessorType(XmlAccessType.FIELD)
public class EccpLogoutAgentRequest extends EccpAbstractRequest {
    
    public static EccpLogoutAgentRequest create(String agentNumber,  String agentHash) {
        return new EccpLogoutAgentRequest(agentNumber, agentHash);
    }

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    @Getter
    @Setter
    @XmlElement(name = "agent_hash")
    private String agentHash;

    private EccpLogoutAgentRequest(String agentNumber, String agentHash) {
        this.agentNumber = agentNumber;
        this.agentHash = agentHash;
    }

    private EccpLogoutAgentRequest() {}

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpLogoutAgentResponse.class;
    }

}
