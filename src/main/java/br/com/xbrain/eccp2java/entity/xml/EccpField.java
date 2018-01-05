package br.com.xbrain.eccp2java.entity.xml;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

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
