package br.com.xbrain.eccp2java.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@Entity
@Table(name = "agent", catalog = "call_center")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "number")
    private String number;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "estatus")
    private String estatus;
    @Column(name = "eccp_password")
    private String eccpPassword;
    @OneToMany(mappedBy = "agent")
    private Collection<Call> callCollection;

    public Agent() {
    }

    public Agent(Integer id) {
        this.id = id;
    }

    public Agent(Integer id, String type, String number, String name, String password) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEccpPassword() {
        return eccpPassword;
    }

    public void setEccpPassword(String eccpPassword) {
        this.eccpPassword = eccpPassword;
    }

    public Collection<Call> getCallCollection() {
        return callCollection;
    }

    public void setCallCollection(Collection<Call> callCollection) {
        this.callCollection = callCollection;
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
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.com.xbrain.eccp2java.database.model.Agent[ id=" + id + " ]";
    }

}
