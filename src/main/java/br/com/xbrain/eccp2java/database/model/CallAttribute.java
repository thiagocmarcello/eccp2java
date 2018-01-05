package br.com.xbrain.eccp2java.database.model;

import javax.persistence.*;
import java.io.Serializable;


/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@Entity
@Table(name = "call_attribute", catalog = "call_center")
public class CallAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "columna")
    private String columna;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @Column(name = "column_number")
    private int columnNumber;
    @JoinColumn(name = "id_call", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Call call;

    public CallAttribute() {
    }

    public CallAttribute(Integer id) {
        this.id = id;
    }
    

    public CallAttribute(String columna, String value, int columnNumber, Call call) {
        this.value = value;
        this.columna = columna;
        this.columnNumber = columnNumber;
        this.call = call;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
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
        if (!(object instanceof CallAttribute)) {
            return false;
        }
        CallAttribute other = (CallAttribute) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.model.CallAttribute[ id=" + id + " ]";
    }

}
