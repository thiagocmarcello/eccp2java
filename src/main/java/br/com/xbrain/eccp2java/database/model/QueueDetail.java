package br.com.xbrain.eccp2java.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@Entity
@Table(name = "queues_details", catalog = "asterisk", schema = "")
@Getter
@Setter
public class QueueDetail implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected QueuesDetailPK queuesDetailsPK;
    
    @Basic(optional = false)
    private int flags;

    public QueueDetail() {
    }

    public QueueDetail(QueuesDetailPK queuesDetailsPK) {
        this.queuesDetailsPK = queuesDetailsPK;
    }

    public QueueDetail(QueuesDetailPK queuesDetailsPK, int flags) {
        this.queuesDetailsPK = queuesDetailsPK;
        this.flags = flags;
    }
    
    public void setQueuesDetailsPK(QueuesDetailPK queuesDetailsPK) {
        this.queuesDetailsPK = queuesDetailsPK;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (queuesDetailsPK != null ? queuesDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueueDetail)) {
            return false;
        }
        QueueDetail other = (QueueDetail) object;
        if ((this.queuesDetailsPK == null && other.queuesDetailsPK != null) || (this.queuesDetailsPK != null && !this.queuesDetailsPK.equals(other.queuesDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.QueuesDetails[ queuesDetailsPK=" + queuesDetailsPK + " ]";
    }

}
