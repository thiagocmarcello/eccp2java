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

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "schedulecall")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@ToString
public class EccpScheduleCallRequest implements IEccpRequest {

    @SuppressWarnings("checkstyle:ParameterNumber")
    public static EccpScheduleCallRequest create(
            Long id,
            String agentNumber,
            String agentHash,
            Date dateInit,
            Date dateEnd,
            Date timeInit,
            Date timeEnd,
            Integer sameagent,
            String newphone,
            String newcontactname) {
        return new EccpScheduleCallRequest(id, agentNumber, agentHash, dateInit, dateEnd, timeInit, timeEnd, sameagent,
                newphone, newcontactname);
    }

    @Getter
    @Setter
    @XmlTransient
    protected Long id;

    @Getter
    @Setter
    @XmlElement(name = "agent_number")
    private String agentNumber;

    @Getter
    @Setter
    @XmlElement(name = "agent_hash")
    private String agentHash;

    @Getter
    @Setter
    @XmlElement(name = "date_init")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date dateInit;

    @Getter
    @Setter
    @XmlElement(name = "date_end")
    @XmlJavaTypeAdapter(EccpDateTimeAdapter.class)
    private Date dateEnd;

    @Getter
    @Setter
    @XmlElement(name = "time_init")
    @XmlJavaTypeAdapter(EccpTimeAdapter.class)
    private Date timeInit;

    @Getter
    @Setter
    @XmlElement(name = "time_end")
    @XmlJavaTypeAdapter(EccpTimeAdapter.class)
    private Date timeEnd;

    @Getter
    @Setter
    @XmlElement(name = "sameagent")
    private Integer sameagent;

    @Getter
    @Setter
    @XmlElement(name = "newphone")
    private String newphone;

    @Getter
    @Setter
    @XmlElement(name = "newcontactname")
    private String newcontactname;

    private EccpScheduleCallRequest(Long id,
                                    String agentNumber,
                                    String agentHash,
                                    Date dateInit,
                                    Date dateEnd,
                                    Date timeInit,
                                    Date timeEnd,
                                    Integer sameagent,
                                    String newphone,
                                    String newcontactname) {
        this.id = id;
        this.agentNumber = agentNumber;
        this.agentHash = agentHash;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.timeInit = timeInit;
        this.timeEnd = timeEnd;
        this.sameagent = sameagent;
        this.newphone = newphone;
        this.newcontactname = newcontactname;
    }

    private EccpScheduleCallRequest() {}

}
