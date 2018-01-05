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
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "agentloggedin")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode 
@ToString
public class EccpAgentLoggedInEvent implements IEccpAgentEvent {
    
    @Getter
    @Setter
    @XmlElement(name = "agent")
    private String agentNumber;
    
}
