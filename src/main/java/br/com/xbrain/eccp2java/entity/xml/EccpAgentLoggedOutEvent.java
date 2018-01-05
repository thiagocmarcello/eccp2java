package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <event>
 *   <agentloggedout>
 *       <agent>Agent/9000</agent>
 *        <queues>
 *            <queue>8001</queue>
 *           <queue>8000</queue>
 *       </queues>
 *   </agentloggedout>
 * </event>
 * 
 * @author joaomassan@xbrain.com.br
 */

@XmlRootElement(name = "agentloggedout")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode 
@ToString
public class EccpAgentLoggedOutEvent implements IEccpAgentEvent {
    
    @Getter
    @Setter
    @XmlElement(name = "agent")
    private String agentNumber;
    
    @Getter
    @Setter
    @XmlElementWrapper(name = "queues")
    private List<Integer> queues ;
    
}
