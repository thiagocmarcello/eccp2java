package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import java.util.EventListener;

/**
 *
 * @author joaomassan@xbrain.com.br
 * @param <T>
 */
public interface IEccpEventListener<T extends IEccpEvent> extends EventListener {
    
    void onEvent(T event);
        
}
