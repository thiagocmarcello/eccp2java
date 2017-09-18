package br.com.xbrain.eccp2java.entity.xml;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public interface IEccpResponse {
    
    Long getId();
    
    void setId(Long id);
    
    EccpFailure getFailure();
    
    boolean isFailure();
    
}
