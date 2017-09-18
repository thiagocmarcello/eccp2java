package br.com.xbrain.eccp2java.entity.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode 
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EccpField {
    
    @Getter
    @Setter
    @XmlAttribute(name = "id")
    private Integer id;
    
    @Getter
    @Setter
    @XmlAttribute(name = "order")
    private Integer order;
    
    @Getter
    @Setter
    @XmlElement(name = "label")
    private String label;
    
    @Getter
    @Setter
    @XmlElement(name = "type")
    private String type;
    
    @Getter
    @Setter
    @XmlElementWrapper(name = "options")
    @XmlElement(name = "value")
    List<String> values;

}
