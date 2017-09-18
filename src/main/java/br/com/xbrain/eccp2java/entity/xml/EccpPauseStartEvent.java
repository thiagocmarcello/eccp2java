package br.com.xbrain.eccp2java.entity.xml;

import br.com.xbrain.eccp2java.entity.xml.adapter.EccpDateTimeAdapter;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *<event>
   <pausestart>
      <pause_class>break</pause_class>
      <pause_type>2</pause_type>
      <pause_name>Pausa padrão</pause_name>
      <pause_start>2016-05-25 16:55:11</pause_start>
      <agent_number>Agent/8006</agent_number>
   </pausestart>
 *</event>
 * 
 * @author xbrain (joaomassan@xbrain.com.br)
 */
@XmlRootElement(name = "pausestart")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class EccpPauseStartEvent implements IEccpAgentEvent {
    
    @XmlElement(name = "pause_class")
    private String pauseClass;
    
    @XmlElement(name = "pause_type")
    private Integer pauseType;
    
    @XmlElement(name = "pause_name")
    private String pauseName;

    @XmlElement(name = "pause_start")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date pauseStart;
    
    @XmlElement(name = "agent_number")
    private String agentNumber;
            
}
