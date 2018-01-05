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
 *
 * <request id="11">
 * <pauseagent>
 * <agent_number>Agent/9000</agent_number>
 * <agent_hash>XXXXXXXXXXXXXXXXXXXXXXXXX</agent_hash>
 * <pause_type>3</pause_type>
 * </pauseagent>
 * </request>
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "pauseagent")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpPauseAgentRequest extends EccpAbstractRequest {

    public static EccpPauseAgentRequest create(String agentNumber, String agentHash, Integer pauseType) {
        return new EccpPauseAgentRequest(agentNumber, agentHash, pauseType);
    }

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    @Getter
    @Setter
    @XmlElement(name = "agent_hash")
    private String agentHash;

    @Getter
    @Setter
    @XmlElement(name = "pause_type")
    private Integer pauseType = 1;

    private EccpPauseAgentRequest(String agentNumber, String agentHash, Integer pauseType) {
        this.agentNumber = agentNumber;
        this.agentHash = agentHash;
        this.pauseType = pauseType;
    }

    private EccpPauseAgentRequest() {
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpPauseAgentResponse.class;
    }

}
