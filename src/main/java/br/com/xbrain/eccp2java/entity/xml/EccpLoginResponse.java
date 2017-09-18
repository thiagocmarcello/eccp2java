package br.com.xbrain.eccp2java.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@XmlRootElement(name = "login_response")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor 
@AllArgsConstructor
public class EccpLoginResponse extends EccpAbstractResponse {
    
    @Getter
    @Setter
    @XmlElement(name = "app_cookie")
    private String appCookie;
    
}
