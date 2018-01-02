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
 * <event>
 *   <agentfailedlogin>
 *       <agent>Agent/9000</agent>
 *   </agentfailedlogin>
 * </event>
 */
@XmlRootElement(name = "agentfailedlogin")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode 
@ToString
public class EccpAgentFailedLogginEvent implements IEccpAgentEvent {
    @Getter
    @Setter
    @XmlElement(name = "agent")
    private String agentNumber;

}
