package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "logout_response")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpLogoutResponse extends EccpAbstractResponse {

    public static EccpLogoutResponse create(Long id, String status, EccpFailure failure) {
        return new EccpLogoutResponse();
    }
   
}
