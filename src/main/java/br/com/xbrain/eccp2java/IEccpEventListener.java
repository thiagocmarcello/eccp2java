package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import java.util.EventListener;

public interface IEccpEventListener extends EventListener {
    
    void onEvent(IEccpEvent event);
        
}
