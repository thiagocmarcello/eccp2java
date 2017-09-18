package br.com.xbrain.eccp2java.entity.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@XmlRootElement(name = "form")
@XmlAccessorType(XmlAccessType.FIELD) 
@EqualsAndHashCode 
@ToString
public class EccpForm {

    public static EccpForm create(Integer id, List<EccpField> fields) {
        return new EccpForm(id, fields);
    }
    
    @Getter
    @Setter
    @XmlAttribute 
    private Integer id;
    
    @Getter
    @Setter
    @XmlElement(name = "field")
    private List<EccpField> fields;

    private EccpForm(Integer id, List<EccpField> fields) {
        this.id = id;
        this.fields = fields;
    }

    public EccpForm() {
    }

}