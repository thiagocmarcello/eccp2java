package br.com.xbrain.eccp2java.database;

import lombok.Getter;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
enum CampaignStatusEnum {

    ACTIVE("A"), INACTIVE("I");

    @Getter
    String value;

    CampaignStatusEnum(String value) {
        this.value = value;
    }

    CampaignStatusEnum fromValue(String value) {
        for (CampaignStatusEnum item : CampaignStatusEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Status " + value + " não encontrado");
    }
}
