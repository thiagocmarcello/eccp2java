package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author xbrain (joaomassan@xbrain.com.br)
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)        
public class EccpRequestWrapper {
 
    @Getter
    @Setter
    @XmlAttribute
    private Long id;
    
    @Getter
    @XmlAnyElement
    private IEccpRequest request;
    
    public void setRequest(IEccpRequest request) {
        this.id = request.getId();
        this.request = request;
    }
}
