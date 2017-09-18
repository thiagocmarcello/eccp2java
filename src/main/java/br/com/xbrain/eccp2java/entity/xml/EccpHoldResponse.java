package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * <response id="11">
     <hold_response>
         <success/>
     </hold_response>
  </response>

 * @author xbrain (joaomassan@xbrain.com.br)
 */
@XmlRootElement(name = "hold_response")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpHoldResponse extends EccpAbstractResponse {
    
}
