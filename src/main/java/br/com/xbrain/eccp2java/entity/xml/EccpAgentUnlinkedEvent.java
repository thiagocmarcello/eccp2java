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
 * <?xml version="1.0" encoding="UTF-8"?>
 * <event>
 *  <agentunlinked>
 *     <calltype>outgoing</calltype>
 *     <campaign_id>8</campaign_id>
 *     <call_id>928</call_id>
 *     <phone>4333042204</phone>
 *     <datetime_linkend>2016-01-15 15:49:43</datetime_linkend>
 *     <duration>10.517521858215</duration>
 *     <shortcall>0</shortcall>
 *     <campaignlog_id>1784</campaignlog_id>
 *     <queue>9999</queue>
 *     <agent_number>Agent/4001</agent_number>
 *  </agentunlinked>
 * </event>
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "agentunlinked")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@ToString
public class EccpAgentUnlinkedEvent implements IEccpAgentEvent {

    @Getter
    @Setter
    @XmlElement(name = "calltype")
    private String calltype;

    @Getter
    @Setter
    @XmlElement(name = "call_id")
    private Integer callId;

    @Getter
    @Setter
    @XmlElement(name = "phone")
    private String phone;

    @Getter
    @Setter
    @XmlElement(name = "linkend")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date linkend;

    @Getter
    @Setter
    @XmlElement(name = "duration")
    private Double duration;

    @Getter
    @Setter
    @XmlElement(name = "shortcall")
    private Integer shortcall;

    @Getter
    @Setter
    @XmlElement(name = "campaignlog_id")
    private Integer campaignlogId;

    @Getter
    @Setter
    @XmlElement(name = "queue")
    private Integer queue;

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

}
