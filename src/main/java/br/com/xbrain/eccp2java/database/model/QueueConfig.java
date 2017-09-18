package br.com.xbrain.eccp2java.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.experimental.Builder;


/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@Entity
@Table(name = "queues_config", catalog = "asterisk", schema = "")
public class QueueConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull(message = "QueueConfig.extension_field_is_mandatory")
    private String extension = "";
    @Basic(optional = false)
    private String descr = "";
    @Basic(optional = false)
    private String grppre = "";
    @Basic(optional = false)
    private String alertinfo = "";
    @Basic(optional = false)
    private boolean ringing = false;
    @Basic(optional = false)
    private String maxwait = "";
    @Basic(optional = false)
    private String password = "";
    @Basic(optional = false)
    @Column(name = "ivr_id")
    private String ivrId = "";
    @Basic(optional = false)
    private String dest = "";
    @Basic(optional = false)
    private boolean cwignore = false;
    private String qregex;
    @Column(name = "agentannounce_id")
    private Integer agentannounceId;
    @Column(name = "joinannounce_id")
    private Integer joinannounceId;
    private Boolean queuewait;
    @Column(name = "use_queue_context")
    private Boolean useQueueContext;
    private Boolean togglehint;
    private Boolean qnoanswer;
    private Boolean callconfirm;
    @Column(name = "callconfirm_id")
    private Integer callconfirmId;
    @Column(name = "monitor_type")
    private String monitorType;
    @Column(name = "monitor_heard")
    private Integer monitorHeard;
    @Column(name = "monitor_spoken")
    private Integer monitorSpoken;
    @Basic(optional = false)
    @Column(name = "callback_id")
    private String callbackId;

    public QueueConfig() {
    }

    public QueueConfig(String extension) {
        this.extension = extension;
    }

    @Builder
    public QueueConfig(String extension, String descr, String grppre, String alertinfo, boolean ringing, String maxwait, String password, String ivrId, String dest, boolean cwignore, String callbackId) {
        this.extension = extension;
        this.descr = descr;
        this.grppre = grppre;
        this.alertinfo = alertinfo;
        this.ringing = ringing;
        this.maxwait = maxwait;
        this.password = password;
        this.ivrId = ivrId;
        this.dest = dest;
        this.cwignore = cwignore;
        this.callbackId = callbackId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getGrppre() {
        return grppre;
    }

    public void setGrppre(String grppre) {
        this.grppre = grppre;
    }

    public String getAlertinfo() {
        return alertinfo;
    }

    public void setAlertinfo(String alertinfo) {
        this.alertinfo = alertinfo;
    }

    public boolean getRinging() {
        return ringing;
    }

    public void setRinging(boolean ringing) {
        this.ringing = ringing;
    }

    public String getMaxwait() {
        return maxwait;
    }

    public void setMaxwait(String maxwait) {
        this.maxwait = maxwait;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIvrId() {
        return ivrId;
    }

    public void setIvrId(String ivrId) {
        this.ivrId = ivrId;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public boolean getCwignore() {
        return cwignore;
    }

    public void setCwignore(boolean cwignore) {
        this.cwignore = cwignore;
    }

    public String getQregex() {
        return qregex;
    }

    public void setQregex(String qregex) {
        this.qregex = qregex;
    }

    public Integer getAgentannounceId() {
        return agentannounceId;
    }

    public void setAgentannounceId(Integer agentannounceId) {
        this.agentannounceId = agentannounceId;
    }

    public Integer getJoinannounceId() {
        return joinannounceId;
    }

    public void setJoinannounceId(Integer joinannounceId) {
        this.joinannounceId = joinannounceId;
    }

    public Boolean getQueuewait() {
        return queuewait;
    }

    public void setQueuewait(Boolean queuewait) {
        this.queuewait = queuewait;
    }

    public Boolean getUseQueueContext() {
        return useQueueContext;
    }

    public void setUseQueueContext(Boolean useQueueContext) {
        this.useQueueContext = useQueueContext;
    }

    public Boolean getTogglehint() {
        return togglehint;
    }

    public void setTogglehint(Boolean togglehint) {
        this.togglehint = togglehint;
    }

    public Boolean getQnoanswer() {
        return qnoanswer;
    }

    public void setQnoanswer(Boolean qnoanswer) {
        this.qnoanswer = qnoanswer;
    }

    public Boolean getCallconfirm() {
        return callconfirm;
    }

    public void setCallconfirm(Boolean callconfirm) {
        this.callconfirm = callconfirm;
    }

    public Integer getCallconfirmId() {
        return callconfirmId;
    }

    public void setCallconfirmId(Integer callconfirmId) {
        this.callconfirmId = callconfirmId;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public Integer getMonitorHeard() {
        return monitorHeard;
    }

    public void setMonitorHeard(Integer monitorHeard) {
        this.monitorHeard = monitorHeard;
    }

    public Integer getMonitorSpoken() {
        return monitorSpoken;
    }

    public void setMonitorSpoken(Integer monitorSpoken) {
        this.monitorSpoken = monitorSpoken;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (extension != null ? extension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueueConfig)) {
            return false;
        }
        QueueConfig other = (QueueConfig) object;
        if ((this.extension == null && other.extension != null) || (this.extension != null && !this.extension.equals(other.extension))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.QueuesConfig[ extension=" + extension + " ]";
    }

}
