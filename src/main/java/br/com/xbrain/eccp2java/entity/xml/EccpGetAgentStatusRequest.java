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
 *
 * e.g.:
 * <request id="4">
 * <getagentstatus>
 * <agent_number>Agent/9000</agent_number>
 * </getagentstatus>
 * </request>
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
@XmlRootElement(name = "getagentstatus")
public class EccpGetAgentStatusRequest extends EccpAbstractRequest {

    public static EccpGetAgentStatusRequest create(String agentNumber) {
        return new EccpGetAgentStatusRequest(agentNumber);
    }

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    private EccpGetAgentStatusRequest() {
    }

    private EccpGetAgentStatusRequest(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpGetAgentStatusResponse.class;
    }

}
