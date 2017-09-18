package br.com.xbrain.eccp2java.database;

import lombok.Getter;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
public enum CampaignContextEnum {
    FROM_INTERNAL("from-internal");

    @Getter
    String value;

    CampaignContextEnum(String value) {
        this.value = value;
    }

    CampaignContextEnum fromValue(String value) {
        for (CampaignContextEnum item : CampaignContextEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Contexto " + value + " não encontrado");
    }
    
    @Override
    public String toString() {
        return getValue();
    }
}