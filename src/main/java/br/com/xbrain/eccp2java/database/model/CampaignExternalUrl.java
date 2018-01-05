package br.com.xbrain.eccp2java.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "campaign_external_url", catalog = "call_center")
public class CampaignExternalUrl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "urltemplate")
    private String urltemplate;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "opentype")
    private String opentype;
    @OneToMany(mappedBy = "campaignExternalURL")
    private Collection<Campaign> campaignCollection;

    public CampaignExternalUrl() {
    }

    public CampaignExternalUrl(Integer id) {
        this.id = id;
    }

    public CampaignExternalUrl(Integer id, String urltemplate, String description, boolean active, String opentype) {
        this.id = id;
        this.urltemplate = urltemplate;
        this.description = description;
        this.active = active;
        this.opentype = opentype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrltemplate() {
        return urltemplate;
    }

    public void setUrltemplate(String urltemplate) {
        this.urltemplate = urltemplate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOpentype() {
        return opentype;
    }

    public void setOpentype(String opentype) {
        this.opentype = opentype;
    }

    public Collection<Campaign> getCampaignCollection() {
        return campaignCollection;
    }

    public void setCampaignCollection(Collection<Campaign> campaignCollection) {
        this.campaignCollection = campaignCollection;
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
        if (!(object instanceof CampaignExternalUrl)) {
            return false;
        }
        CampaignExternalUrl other = (CampaignExternalUrl) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.model.CampaignExternalUrl[ id=" + id + " ]";
    }

}
