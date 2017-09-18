package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.EccpUtils;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class EccpResponseFactory {
    
    private static final Logger LOG = Logger.getLogger(EccpResponseFactory.class.getName());
 
    private final Document document;
    private Element responseElement;
    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;
    
    public EccpResponseFactory(Document document) throws EccpException {
        this.document = document;
    }
        
    public <T extends IEccpResponse> T createResponse() throws EccpException {
        try {
            Element root = (Element) document.getFirstChild();
            Unmarshaller unmarsh = getUnmarshaller(EccpUtils.getResponseTypeMap().get(root.getFirstChild().getNodeName()));
            IEccpResponse response = IEccpResponse.class.cast(unmarsh.unmarshal(getResponseElement().getFirstChild()));
            configureResponseId(response);
            return (T) response;
        } catch (JAXBException ex) {
           throw EccpException.create(EccpException.Error.CAN_NOT_CREATE_RESPONSE, ex);
        }
    }
    
    private void configureResponseId(IEccpResponse response) throws NumberFormatException, EccpException {
        String idStr = getResponseElement().getAttribute("id");
        Long responseId = isNotNullNeitherNullText(idStr) ? Long.valueOf(idStr) : null;
        response.setId(responseId);
    }

    // TODO: mover para classe utilitária
    public static boolean isNotNullNeitherNullText(String text) {
        return text != null && !"null".equalsIgnoreCase(text);
    }
    
    private Element getResponseElement() throws EccpException {
        if(responseElement == null) {
            responseElement = (Element) document.getFirstChild();
        }
        return responseElement;
    }

    private Unmarshaller getUnmarshaller(Class<?> clss) throws EccpException {
        if(unmarshaller == null) {
            try {
                jaxbContext = JAXBContext.newInstance(clss);
                unmarshaller = jaxbContext.createUnmarshaller();
            } catch (JAXBException ex) {
                throw EccpException.create(EccpException.Error.CAN_NOT_CREATE_UNMARSHALLER, ex);
            }
        }
        return unmarshaller;
    }
}