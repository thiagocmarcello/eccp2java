package br.com.xbrain.eccp2java.entity.xml;


import br.com.xbrain.eccp2java.entity.xml.adapter.EccpDateTimeAdapter;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "agentlinked")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode 
@ToString
public class EccpAgentLinkedEvent implements IEccpAgentEvent {
    
    @Getter
    @Setter
    @XmlElement(name = "calltype")
    private String calltype;
    
    @Getter
    @Setter
    @XmlElement(name = "call_id")
    private Long callId;
    
    @Getter
    @Setter
    @XmlElement(name = "campaign_id")
    private Integer campaignId;
    
    @Getter
    @Setter
    @XmlElement(name = "phone")
    private String phone;
    
    @Getter
    @Setter
    @XmlElement(name = "status")
    private String status;
    
    @Getter
    @Setter
    @XmlElement(name = "uniqueid")
    private String uniqueid;
    
    @Getter
    @Setter
    @XmlElement(name = "datetime_originate")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date originate;
    
    @Getter
    @Setter
    @XmlElement(name = "datetime_originateresponse")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date originateresponse;
    
    @Getter
    @Setter
    @XmlElement(name = "datetime_join")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date join;
    
    @Getter
    @Setter
    @XmlElement(name = "datetime_linkstart")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date linkstart;
    
    @Getter
    @Setter
    @XmlElement(name = "retries")
    private Integer retries;
    
    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;
    
    @Getter
    @Setter
    @XmlElement(name = "trunk")
    private String trunk;
    
    @Getter
    @Setter
    @XmlElementWrapper(name = "call_attributes")
    private List<EccpCallAttribute> attribute;
    
}
