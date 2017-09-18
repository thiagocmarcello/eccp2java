package br.com.xbrain.eccp2java.enums;

import lombok.Getter;

/**
 *
 * @author xbrain
 */
public enum EConfiguracao {

    IP_QUEUES_RELOAD("https://192.168.1.23"),
    PORTA_QUEUES_RELOAD(":26000"),
    SENHA_QUEUES_RELOAD("xbrain"),
    PARAM_QUEUES_RELOAD("hash"),
    SENHA_HASH_QUEUES_RELOAD("9cbb0bd1a5114bd876ce5680af684e6b18b8d096"),
    URL_QUEUES_RELOAD("/modules/queues/reload.php"),

    IP_BANCO("jdbc:mysql://192.168.1.23"),
    PORTA_BANCO(":3306"),
    USUARIO_BANCO("root"),
    SENHA_BANCO("l1b3rd4d3");

    @Getter
    private final String valor;

    private EConfiguracao(String valor) {
        this.valor = valor;
    }
}
