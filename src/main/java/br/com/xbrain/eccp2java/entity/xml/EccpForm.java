package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

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