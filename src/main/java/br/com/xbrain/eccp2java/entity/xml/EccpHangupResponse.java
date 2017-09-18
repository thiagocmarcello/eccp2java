package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "hangup_response")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpHangupResponse extends EccpAbstractResponse {

}
