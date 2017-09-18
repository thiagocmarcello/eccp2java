package br.com.xbrain.elastix;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@ToString
@EqualsAndHashCode
public class Contact implements Serializable {

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String hpId;

    private Contact(String phone, String hpId) {
        this.phone = phone;
        this.hpId = hpId;
    }

    public static final Contact create(String phone, String hpId) {
        if (phone == null) {
            throw new IllegalArgumentException("O telefone do contato não pode ser nulo");
        }

        if (hpId == null) {
            throw new IllegalArgumentException("O id do HP do contato não pode ser nulo");
        }

        return new Contact(phone, hpId);
    }

}
