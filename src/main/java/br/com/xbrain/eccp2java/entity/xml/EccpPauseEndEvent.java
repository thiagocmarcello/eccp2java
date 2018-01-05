package br.com.xbrain.eccp2java.entity.xml;

import br.com.xbrain.eccp2java.entity.xml.adapter.EccpDateTimeAdapter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


/**
 * 
 * <event>
       <pauseend>
           <pause_class>break</pause_class>
           <pause_start>2016-05-25 17:24:08</pause_start>
           <pause_end>2016-05-25 17:25:12</pause_end>
           <pause_duration>64</pause_duration>
           <pause_type>2</pause_type>
           <pause_name>Pausa padrão</pause_name>
           <agent_number>Agent/8006</agent_number>
        </pauseend>
   </event>
 * 
 *
 * @author xbrain (joaomassan@xbrain.com.br)
 */
@XmlRootElement(name = "pauseend")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class EccpPauseEndEvent implements IEccpAgentEvent {

    @XmlElement(name = "pause_class")
    private String pauseClass;
    
    @XmlElement(name = "pause_start")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date pauseStart;
    
    @XmlElement(name = "pause_end")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date pauseEnd;
    
    @XmlElement(name = "pause_duration")
    private Integer pauseDuration;
    
    @XmlElement(name = "pause_type")
    private Integer pauseType;
    
    @XmlElement(name = "pause_name")
    private String pauseName;
    
    @XmlElement(name = "pause_class")
    private String agentNumber;

}
