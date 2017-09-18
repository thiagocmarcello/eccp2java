package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.util.EccpUtils;
import br.com.xbrain.eccp2java.entity.xml.IEccpAgentEvent;
import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import java.util.logging.Level;
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
public class EccpAgentEventFactory {

    private static final Logger LOG = Logger.getLogger(EccpAgentEventFactory.class.getName());

    private Document document;
    private Element responseElement;
    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;

    public <T extends IEccpAgentEvent> T create(Document document) throws EccpException {
        try {
            this.document = document;
            Element root = (Element) document.getFirstChild();
            Unmarshaller unmarsh = getUnmarshaller(getEventClass(root.getFirstChild().getNodeName()));
            IEccpEvent event = IEccpEvent.class.cast(unmarsh.unmarshal(getResponseElement().getFirstChild()));
            return (T) event;
        } catch (ClassNotFoundException | EccpException | JAXBException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw EccpException.create(EccpException.Error.INTERNAL_ERROR, ex);
        }
    }

    private Element getResponseElement() throws EccpException {
        if (responseElement == null) {
            responseElement = (Element) document.getFirstChild();
        }
        return responseElement;
    }

    private Unmarshaller getUnmarshaller(Class<?> clss) throws EccpException {
        if (unmarshaller == null) {
            try {
                jaxbContext = JAXBContext.newInstance(clss);
                unmarshaller = jaxbContext.createUnmarshaller();
            } catch (JAXBException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw EccpException.create(EccpException.Error.CAN_NOT_CREATE_UNMARSHALLER, ex);
            }
        }
        return unmarshaller;
    }

    private Class<?> getEventClass(String eventName) throws ClassNotFoundException {
        if(!EccpUtils.getEventTypeMap().containsKey(eventName)) {
            throw new ClassNotFoundException("Não encontrada a classe de mapeamento do evento " + eventName);
        }
        return EccpUtils.getEventTypeMap().get(eventName);
    }
}