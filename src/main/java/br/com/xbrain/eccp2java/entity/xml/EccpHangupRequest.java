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
 * <request id="13">
 * <hangup>
 * <agent_number>Agent/9000</agent_number>
 * <agent_hash>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</agent_hash>
 * </hangup>
 * </request>
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "hangup")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpHangupRequest extends EccpAbstractRequest {

    public static EccpHangupRequest create(String agentNumber, String agenHash) {
        return new EccpHangupRequest(agentNumber, agenHash);
    }

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    @Getter
    @Setter
    @XmlElement(name = "agent_hash")
    private String agenHash;

    private EccpHangupRequest(String agentNumber, String agenHash) {
        this.agentNumber = agentNumber;
        this.agenHash = agenHash;
    }

    private EccpHangupRequest() {
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpHangupResponse.class;
    }

}
