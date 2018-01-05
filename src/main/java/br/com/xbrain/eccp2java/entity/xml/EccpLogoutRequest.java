package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@ToString
@NoArgsConstructor
@XmlRootElement(name = "logout")
@EqualsAndHashCode(callSuper = true) 
@XmlAccessorType(XmlAccessType.FIELD)
public class EccpLogoutRequest extends EccpAbstractRequest {
    
    public static EccpLogoutRequest create() {
        return new EccpLogoutRequest();
    }

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpLogoutResponse.class;
    }
}
