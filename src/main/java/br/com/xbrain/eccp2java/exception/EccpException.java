package br.com.xbrain.eccp2java.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class EccpException extends Exception {

    public static EccpException create(Error error) {
        return new EccpException(error, new Throwable("Ops"));
    }

    public static EccpException create(Error error, Throwable cause) {
        return new EccpException(error, cause);
    }

    public static enum Error {
        LOGIN_FAILED,
        CLASS_NOT_FOUND,
        INTERNAL_ERROR,
        SERVICE_UNAVAILABLE,
        CAN_NOT_CREATE_MARSHALLER,
        CAN_NOT_CREATE_UNMARSHALLER,
        CAN_NOT_CREATE_DOCUMENT,
        CAN_NOT_CREATE_RESPONSE,
        CAN_NOT_SEND,
        RESPONSE_NOT_FOUND;
    }

    @Getter
    private Map<String, Serializable> infos;

    @Getter
    private final Error error;

    private EccpException(Error error) {
        super(error.toString());
        this.error = error;
    }

    private EccpException(Error error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public EccpException addInfo(String key, Serializable value) {
        if (infos == null) {
            infos = new HashMap<>();
        }
        infos.put(key, value);
        return this;
    }
}
