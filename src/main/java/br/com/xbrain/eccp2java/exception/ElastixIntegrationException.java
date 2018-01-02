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

    @Getter
    private Map<String, Serializable> infos;

    public ElastixIntegrationException(String message, Throwable cause) {
        super(message);
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
    public ElastixIntegrationException addInfo(String key, String message, Object... params) {
        if (message != null) {
            addInfo(key, String.format(message, params));
        }
        return this;
    }
}
