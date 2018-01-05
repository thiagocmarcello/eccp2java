package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;

public interface IEccpCallback {

    void onEvent(IEccpEvent event);
    
    void checkAgentConsoles();
}
