package br.com.xbrain.eccp2java.entity.xml;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public interface IEccpRequest {

    Long getId();

    default Class<? extends IEccpResponse> expectedResponseType() {
        return IEccpResponse.class;
    }

}
