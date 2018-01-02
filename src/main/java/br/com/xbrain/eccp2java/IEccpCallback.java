package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public interface IEccpCallback {

    void onEvent(IEccpEvent event);
    
    void checkAgentConsoles();
}
