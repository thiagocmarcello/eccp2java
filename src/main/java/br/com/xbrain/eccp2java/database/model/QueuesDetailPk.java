package br.com.xbrain.eccp2java.database.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class QueuesDetailPk implements Serializable {

    @Basic(optional = false)
    private String id;
    @Basic(optional = false)
    private String keyword;
    @Basic(optional = false)
    private String data;

    public QueuesDetailPk() {
    }

    public QueuesDetailPk(String id, String keyword, String data) {
        this.id = id;
        this.keyword = keyword;
        this.data = data;
    }
}
