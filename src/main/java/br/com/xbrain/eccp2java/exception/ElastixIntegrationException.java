package br.com.xbrain.eccp2java.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class ElastixIntegrationException extends Exception {

    public static ElastixIntegrationException create(Error error) {
        return new ElastixIntegrationException(error, new Throwable("Ops"));
    }

    public static ElastixIntegrationException create(Error error, Throwable cause) {
        return new ElastixIntegrationException(error, cause);
    }

    public static enum Error {
        TX_EXECUTION_ERROR,
        
        CAMPAIGN_VALIDATION_ERROR,
        CAMPAIGN_ALREADY_EXISTS,
        CAN_NOT_SAVE_CAMPAIGN,
        CAN_NOT_REMOVE_CAMPAIGN,
        CAN_NOT_FIND_CAMPAIGN,
        CAN_NOT_UPDATE_CAMPAIGN,
        
        QUEUE_NOT_FOUND,
        QUEUE_VALIDATION_ERROR,
        QUEUE_ALREADY_EXISTS,
        CAN_NOT_SAVE_QUEUE,
        CAN_NOT_REMOVE_QUEUE,
        CAN_NOT_FIND_QUEUE,
        CAN_NOT_UPDATE_QUEUE;
    }

    @Getter
    private Map<String, Serializable> infos;

    @Getter
    private final Error error;

    private ElastixIntegrationException(Error error) {
        super(error.toString());
        this.error = error;
    }

    private ElastixIntegrationException(Error error, Throwable cause) {
        super(error.toString());
        this.error = error;
    }

    public ElastixIntegrationException addInfo(String key, Serializable value) {
        if (infos == null) {
            infos = new HashMap<>();
        }
        infos.put(key, value);
        return this;
    }

    /**
     * @see java.lang.String#format(java.lang.String, java.lang.Object...)
     */
    public ElastixIntegrationException addInfo(String key, String message, String... params) {
        if (message != null) {
            addInfo(key, String.format(message, params));
        }
        return this;
    }
}
