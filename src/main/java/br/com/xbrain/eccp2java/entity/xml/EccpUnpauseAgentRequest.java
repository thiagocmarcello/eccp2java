package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Example:
 * <request id="12">
 *   <unpauseagent>
 *       <agent_number>Agent/9000</agent_number>
 *       <agent_hash>XXXXXXXXXXXXXXXXXXXXXXXXX</agent_hash>
 *   </unpauseagent>
 * </request>
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "unpauseagent")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpUnpauseAgentRequest extends EccpAbstractRequest {

    public static EccpUnpauseAgentRequest create(String agentNumber, String agentHash) {
        return new EccpUnpauseAgentRequest(agentNumber, agentHash);
    }
    
    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    @Getter
    @Setter
    @XmlElement(name = "agent_hash")
    private String agentHash;

    private EccpUnpauseAgentRequest() { }

    private EccpUnpauseAgentRequest(String agentNumber, String agentHash) {
        this.agentNumber = agentNumber;
        this.agentHash = agentHash;
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpUnpauseAgentResponse.class;
    }
    
}
