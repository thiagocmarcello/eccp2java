package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "attribute")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode 
@ToString
public class EccpCallAttribute {
    
    @Getter
    @Setter
    @XmlElement(name = "label")
    private String label;
    
    @Getter
    @Setter
    @XmlElement(name = "value")
    private String value;
    
    @Getter
    @Setter
    @XmlElement(name = "order")
    private Integer order;
    
}
