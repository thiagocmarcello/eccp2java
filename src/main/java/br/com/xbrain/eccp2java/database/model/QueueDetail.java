package br.com.xbrain.eccp2java.database.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "queues_details", catalog = "asterisk")
@Getter
@Setter
@EqualsAndHashCode(of = "queuesDetailsPk")
public class QueueDetail implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected QueuesDetailPk queuesDetailsPk;
    
    @Basic(optional = false)
    private int flags;

    public QueueDetail() {
    }

    public QueueDetail(QueuesDetailPk queuesDetailsPk) {
        this.queuesDetailsPk = queuesDetailsPk;
    }

    public QueueDetail(QueuesDetailPk queuesDetailsPk, int flags) {
        this.queuesDetailsPk = queuesDetailsPk;
        this.flags = flags;
    }
    
    public void setQueuesDetailsPk(QueuesDetailPk queuesDetailsPk) {
        this.queuesDetailsPk = queuesDetailsPk;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.QueuesDetails[ queuesDetailsPk=" + queuesDetailsPk + " ]";
    }

}
