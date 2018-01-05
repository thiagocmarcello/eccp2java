package br.com.xbrain.eccp2java.database.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

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
@Table(name = "campaign", catalog = "call_center")
@EqualsAndHashCode(of = "id")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "datetime_init")
    @Temporal(TemporalType.DATE)
    private Date datetimeInit;
    @Basic(optional = false)
    @Column(name = "datetime_end")
    @Temporal(TemporalType.DATE)
    private Date datetimeEnd;
    @Basic(optional = false)
    @Column(name = "daytime_init")
    @Temporal(TemporalType.TIME)
    private Date daytimeInit;
    @Basic(optional = false)
    @Column(name = "daytime_end")
    @Temporal(TemporalType.TIME)
    private Date daytimeEnd;
    @Basic(optional = false)
    @Column(name = "retries")
    private int retries;
    @Column(name = "trunk")
    private String trunk;
    @Basic(optional = false)
    @Column(name = "context")
    private String context;
    @Basic(optional = false)
    @Column(name = "queue")
    private String queue;
    @Basic(optional = false)
    @Column(name = "max_canales")
    private int maxCanales;
    @Column(name = "num_completadas")
    private Integer numCompletadas;
    @Column(name = "promedio")
    private Integer promedio;
    @Column(name = "desviacion")
    private Integer desviacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "script")
    private String script;
    @Basic(optional = false)
    @Column(name = "estatus")
    private String estatus;
    @JoinColumn(name = "id_url", referencedColumnName = "id")
    @ManyToOne
    private CampaignExternalUrl campaignExternalUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campaign", fetch = FetchType.LAZY)
    private Collection<Call> calls = new ArrayList<>();
    @JoinTable(name = "campaign_form", joinColumns = {
        @JoinColumn(name = "id_campaign", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_form", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Form> formCollection = new ArrayList<>();

    public Campaign() {
    }

    public Campaign(Integer id) {
        this.id = id;
    }

    @Builder
    public Campaign(Integer id, String name, Date datetimeInit, Date datetimeEnd, Date daytimeInit, Date daytimeEnd, 
            int retries, String context, String queue, int maxCanales, String script, String estatus) {
        this.id = id;
        this.name = name;
        this.datetimeInit = datetimeInit;
        this.datetimeEnd = datetimeEnd;
        this.daytimeInit = daytimeInit;
        this.daytimeEnd = daytimeEnd;
        this.retries = retries;
        this.context = context;
        this.queue = queue;
        this.maxCanales = maxCanales;
        this.script = script;
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatetimeInit() {
        return datetimeInit;
    }

    public void setDatetimeInit(Date datetimeInit) {
        this.datetimeInit = datetimeInit;
    }

    public Date getDatetimeEnd() {
        return datetimeEnd;
    }

    public void setDatetimeEnd(Date datetimeEnd) {
        this.datetimeEnd = datetimeEnd;
    }

    public Date getDaytimeInit() {
        return daytimeInit;
    }

    public void setDaytimeInit(Date daytimeInit) {
        this.daytimeInit = daytimeInit;
    }

    public Date getDaytimeEnd() {
        return daytimeEnd;
    }

    public void setDaytimeEnd(Date daytimeEnd) {
        this.daytimeEnd = daytimeEnd;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getTrunk() {
        return trunk;
    }

    public void setTrunk(String trunk) {
        this.trunk = trunk;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public int getMaxCanales() {
        return maxCanales;
    }

    public void setMaxCanales(int maxCanales) {
        this.maxCanales = maxCanales;
    }

    public Integer getNumCompletadas() {
        return numCompletadas;
    }

    public void setNumCompletadas(Integer numCompletadas) {
        this.numCompletadas = numCompletadas;
    }

    public Integer getPromedio() {
        return promedio;
    }

    public void setPromedio(Integer promedio) {
        this.promedio = promedio;
    }

    public Integer getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(Integer desviacion) {
        this.desviacion = desviacion;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public CampaignExternalUrl getCampaignExternalUrl() {
        return campaignExternalUrl;
    }

    public void setCampaignExternalUrl(CampaignExternalUrl campaignExternalUrl) {
        this.campaignExternalUrl = campaignExternalUrl;
    }

    public Collection<Call> getCalls() {
        return calls;
    }

    public void setCalls(Collection<Call> calls) {
        this.calls = calls;
    }

    public Collection<Form> getFormCollection() {
        return formCollection;
    }

    public void setFormCollection(Collection<Form> formCollection) {
        this.formCollection = formCollection;
    }

    @Override
    public String toString() {
        return "Campaign{" + "id=" + id + ", name=" + name + ", datetimeInit=" + datetimeInit
                + ", datetimeEnd=" + datetimeEnd + ", daytimeInit=" + daytimeInit + ", daytimeEnd=" + daytimeEnd
                + ", retries=" + retries + ", trunk=" + trunk + ", context=" + context + ", queue=" + queue
                + ", maxCanales=" + maxCanales + ", numCompletadas=" + numCompletadas + ", estatus=" + estatus + '}';
    }
}
