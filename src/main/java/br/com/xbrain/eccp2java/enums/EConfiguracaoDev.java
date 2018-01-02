package br.com.xbrain.eccp2java.enums;

import lombok.Getter;

/**
 *
 * @author xbrain
 */
public enum EConfiguracaoDev {

    IP_QUEUES_RELOAD("192.168.1.23"),
    PORTA_QUEUES_RELOAD("25256"),
    SENHA_QUEUES_RELOAD("xbrain"),
    PARAM_QUEUES_RELOAD("hash"),
    SENHA_HASH_QUEUES_RELOAD("9cbb0bd1a5114bd876ce5680af684e6b18b8d096"),

    IP_BANCO("jdbc:mysql://192.168.1.22"),
    PORTA_BANCO(":3306"),
    USUARIO_BANCO("root"),
    SENHA_BANCO("l1b3rd4d3");

    @Getter
    private final String valor;

    EConfiguracaoDev(String valor) {
        this.valor = valor;
    }
}
