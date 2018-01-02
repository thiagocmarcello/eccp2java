package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EccpAbstractRequest implements IEccpRequest {

    @Getter
    @XmlTransient
    protected final Long id = SerialRequestIdGenerator.nextId();

}
