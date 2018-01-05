package br.com.xbrain.eccp2java.entity.xml;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * <response id=identifier>
 *     <failure>
 *         <code>XXX</code>
 *         <message>Some message</message>
 *     </failure>
 * </response>
 *
 * @author joaomassan@xbrain.com.br
 */
@Data
@XmlRootElement(name = "failure")
@XmlAccessorType(XmlAccessType.FIELD) 
public class EccpFailure {

    @Getter
    @Setter
    @XmlElement(name = "code")
    private Integer code;
    
    @Getter
    @Setter
    @XmlElement(name = "message")
    private String message;
    
}

