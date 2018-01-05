package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.EccpUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class EccpResponseFactory {
    
    private final Document document;
    private Element responseElement;
    private Unmarshaller unmarshaller;

    public EccpResponseFactory(Document document) throws EccpException {
        this.document = document;
    }

    @SuppressWarnings("unchecked")
    public <T extends IEccpResponse> T createResponse() throws EccpException {
        try {
            Element root = (Element) document.getFirstChild();
            Unmarshaller unmarsh = getUnmarshaller(EccpUtils.getResponseTypeMap().get(root.getFirstChild().getNodeName()));
            IEccpResponse response = IEccpResponse.class.cast(unmarsh.unmarshal(getResponseElement().getFirstChild()));
            configureResponseId(response);
            return (T) response;
        } catch (JAXBException ex) {
           throw new EccpException("Não foi possível criar a resposta", ex);
        }
    }
    
    private void configureResponseId(IEccpResponse response) throws NumberFormatException, EccpException {
        String idStr = getResponseElement().getAttribute("id");
        Long responseId = isNeitherNullNorBlankNorNullTypedString(idStr) ? Long.valueOf(idStr) : null;
        response.setId(responseId);
    }

    private static boolean isNeitherNullNorBlankNorNullTypedString(String text) {
        return text != null && text != "" && !"null".equalsIgnoreCase(text);
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
                JAXBContext jaxbContext = JAXBContext.newInstance(clss);
                unmarshaller = jaxbContext.createUnmarshaller();
            } catch (JAXBException ex) {
                throw new EccpException("Não foi possível criar o unmarshaller", ex);
            }
        }
        return unmarshaller;
    }
}