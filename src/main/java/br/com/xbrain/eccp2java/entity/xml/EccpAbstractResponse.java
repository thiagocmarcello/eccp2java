package br.com.xbrain.eccp2java.entity.xml;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@ToString
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EccpAbstractResponse implements IEccpResponse, Serializable {

    @Getter
    @Setter
    @XmlTransient
    protected Long id;

    @Getter
    @Setter
    @XmlElement(name = "status")
    protected String status;

    @Getter
    @Setter
    protected EccpFailure failure;

    @Override
    public boolean isFailure() {
        return failure != null;
    }

    public EccpAbstractResponse() {
    }

    public EccpAbstractResponse(String status, EccpFailure failure) {
        this.status = status;
        this.failure = failure;
    }

}
