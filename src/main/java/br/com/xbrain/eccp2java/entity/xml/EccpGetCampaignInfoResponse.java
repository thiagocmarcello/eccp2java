package br.com.xbrain.eccp2java.entity.xml;

import br.com.xbrain.eccp2java.entity.xml.adapter.EccpDateTimeAdapter;
import br.com.xbrain.eccp2java.entity.xml.adapter.EccpTimeAdapter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "getcampaigninfo_response")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpGetCampaignInfoResponse extends EccpAbstractResponse {

    @Getter
    @Setter
    @XmlElement(name = "name")
    private String name;
    
    @Getter
    @Setter
    @XmlElement(name = "type")
    private String type;
    
    @Getter
    @Setter
    @XmlElement(name = "startdate")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date startdate;
    
    @Getter
    @Setter
    @XmlElement(name = "enddate")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date enddate;
    
    @Getter
    @Setter
    @XmlElement(name = "working_time_starttime")
    @XmlJavaTypeAdapter(EccpTimeAdapter.class)
    private Date workingTimeStarttime;
    
    @Getter
    @Setter
    @XmlElement(name = "working_time_endtime")
    @XmlJavaTypeAdapter(EccpTimeAdapter.class)
    private Date workingTimeEndtime;
    
    @Getter
    @Setter
    @XmlElement(name = "queue")
    private Integer queue;
    
    @Getter
    @Setter
    @XmlElement(name = "retries")
    private Integer retries;
    
    @Getter
    @Setter
    @XmlElement(name = "context")
    private String context;
    
    @Getter
    @Setter
    @XmlElement(name = "maxchan")
    private Integer maxchan;
    
    @Getter
    @Setter
    @XmlElement(name = "script")
    private String script;
    
    @Getter
    @Setter
    @XmlElementWrapper(name = "forms")
    @XmlElement(name = "form")
    List<EccpForm> forms;
    
    @Getter
    @Setter
    @XmlElement(name = "status")
    private String status;
  
}
