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
@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode(callSuper = true)
@ToString
public class EccpLoginRequest extends EccpAbstractRequest  {
    
    public static EccpLoginRequest create(String username, String password) {
        return new EccpLoginRequest(username, password);
    }
    
    @Getter
    @Setter
    @XmlElement
    private String username;
    
    @Getter
    @Setter
    @XmlElement 
    private String password;

    private EccpLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private EccpLoginRequest() {}

    @Override
    public Class<? extends IEccpResponse> expectedResponseType() {
        return EccpLoginResponse.class;
    }
}
