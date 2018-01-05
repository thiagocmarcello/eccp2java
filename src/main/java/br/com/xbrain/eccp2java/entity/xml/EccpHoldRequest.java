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
 * <request id="11">
 *     <hold>
 *         <agent_number>Agent/9000</agent_number>
 *         <agent_hash>XXXXXXXXXXXXXXXXXXXXXXXXX</agent_hash>
 *     </hold>
 * </request>
 *
 * @author xbrain (joaomassan@xbrain.com.br)
 */
@XmlRootElement(name = "hold")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class EccpHoldRequest extends EccpAbstractRequest {
    
    @XmlElement(name = "agent_hash")
    private String agentNumber;
    
    @XmlElement(name = "anget_hash")
    private String agentHash;

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpHoldResponse.class;
    }

}
