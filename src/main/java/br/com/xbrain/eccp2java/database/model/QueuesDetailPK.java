package br.com.xbrain.eccp2java.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Embeddable;


/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@Embeddable
public class QueuesDetailPK implements Serializable {

    @Basic(optional = false)
    private String id;
    @Basic(optional = false)
    private String keyword;
    @Basic(optional = false)
    private String data;

    public QueuesDetailPK() {
    }

    public QueuesDetailPK(String id, String keyword, String data) {
        this.id = id;
        this.keyword = keyword;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (keyword != null ? keyword.hashCode() : 0);
        hash += (data != null ? data.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueuesDetailPK)) {
            return false;
        }
        QueuesDetailPK other = (QueuesDetailPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.keyword == null && other.keyword != null) || (this.keyword != null && !this.keyword.equals(other.keyword))) {
            return false;
        }
        if ((this.data == null && other.data != null) || (this.data != null && !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.QueuesDetailsPK[ id=" + id + ", keyword=" + keyword + ", data=" + data + " ]";
    }

}
