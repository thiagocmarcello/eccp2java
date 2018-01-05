package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "agentlinked")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true) 
@ToString
public class EccpDialRequest extends EccpAbstractRequest {

    public static EccpDialRequest create(String dial) {
        return new EccpDialRequest(dial);
    }
    
    @Getter
    @Setter
    @XmlElement(name = "dial")
    private String dial;

    public EccpDialRequest(String dial) {
        this.dial = dial;
    }

    public EccpDialRequest() {}

    
}
