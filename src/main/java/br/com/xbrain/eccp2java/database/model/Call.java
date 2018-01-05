package br.com.xbrain.eccp2java.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@Entity
@Table(name = "calls", catalog = "call_center")
public class Call implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    private String status;
    @Column(name = "uniqueid")
    private String uniqueid;
    @Column(name = "fecha_llamada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlamada;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Basic(optional = false)
    @Column(name = "retries")
    private int retries;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "transfer")
    private String transfer;
    @Column(name = "datetime_entry_queue")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeEntryQueue;
    @Column(name = "duration_wait")
    private Integer durationWait;
    @Basic(optional = false)
    @Column(name = "dnc")
    private int dnc;
    @Column(name = "date_init")
    @Temporal(TemporalType.DATE)
    private Date dateInit;
    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    @Column(name = "time_init")
    @Temporal(TemporalType.TIME)
    private Date timeInit;
    @Column(name = "time_end")
    @Temporal(TemporalType.TIME)
    private Date timeEnd;
    @Column(name = "agent")
    private String agentDescription;
    @Column(name = "failure_cause")
    private Integer failureCause;
    @Column(name = "failure_cause_txt")
    private String failureCauseTxt;
    @Column(name = "datetime_originate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeOriginate;
    @Column(name = "trunk")
    private String trunk;
    @Basic(optional = false)
    @Column(name = "scheduled")
    private boolean scheduled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "call")
    private Collection<CallAttribute> attributes = new ArrayList<>();
    @JoinColumn(name = "id_agent", referencedColumnName = "id")
    @ManyToOne
    private Agent agent;
    @JoinColumn(name = "id_campaign", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Campaign campaign;

    public Call() {
    }

    public Call(Integer id) {
        this.id = id;
    }

    public Call(Integer id, String phone, int retries, int dnc, boolean scheduled) {
        this.id = id;
        this.phone = phone;
        this.retries = retries;
        this.dnc = dnc;
        this.scheduled = scheduled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public Date getFechaLlamada() {
        return fechaLlamada;
    }

    public void setFechaLlamada(Date fechaLlamada) {
        this.fechaLlamada = fechaLlamada;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public Date getDatetimeEntryQueue() {
        return datetimeEntryQueue;
    }

    public void setDatetimeEntryQueue(Date datetimeEntryQueue) {
        this.datetimeEntryQueue = datetimeEntryQueue;
    }

    public Integer getDurationWait() {
        return durationWait;
    }

    public void setDurationWait(Integer durationWait) {
        this.durationWait = durationWait;
    }

    public int getDnc() {
        return dnc;
    }

    public void setDnc(int dnc) {
        this.dnc = dnc;
    }

    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getTimeInit() {
        return timeInit;
    }

    public void setTimeInit(Date timeInit) {
        this.timeInit = timeInit;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getAgentDescription() {
        return agentDescription;
    }

    public void setAgentDescription(String agentDescription) {
        this.agentDescription = agentDescription;
    }

    public Integer getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(Integer failureCause) {
        this.failureCause = failureCause;
    }

    public String getFailureCauseTxt() {
        return failureCauseTxt;
    }

    public void setFailureCauseTxt(String failureCauseTxt) {
        this.failureCauseTxt = failureCauseTxt;
    }

    public Date getDatetimeOriginate() {
        return datetimeOriginate;
    }

    public void setDatetimeOriginate(Date datetimeOriginate) {
        this.datetimeOriginate = datetimeOriginate;
    }

    public String getTrunk() {
        return trunk;
    }

    public void setTrunk(String trunk) {
        this.trunk = trunk;
    }

    public boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public Collection<CallAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<CallAttribute> attributes) {
        this.attributes = attributes;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Call)) {
            return false;
        }
        Call other = (Call) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.model.Call[ id=" + id + " ]";
    }

}
