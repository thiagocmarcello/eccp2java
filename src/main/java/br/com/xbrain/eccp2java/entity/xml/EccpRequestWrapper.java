package br.com.xbrain.eccp2java.entity.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;


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
