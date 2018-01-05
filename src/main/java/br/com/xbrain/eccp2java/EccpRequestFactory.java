package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.EccpRequestWrapper;
import br.com.xbrain.eccp2java.entity.xml.IEccpRequest;
import br.com.xbrain.eccp2java.exception.EccpException;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EccpRequestFactory<T extends IEccpRequest> {
    
    private static final Logger LOG = Logger.getLogger(EccpRequestFactory.class.getName());
    
    public static <T extends IEccpRequest> EccpRequestFactory create(T wrapped) throws EccpException {
        EccpRequestFactory<T> xc = new EccpRequestFactory<>();
        xc.wrapped = wrapped;
        xc.marshal();
        return xc;
    }

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private T wrapped;
    private String xmlString;

    private DocumentBuilderFactory getDocumentBuilderFactory() {
        if (documentBuilderFactory == null) {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
        }
        return documentBuilderFactory;
    }

    private DocumentBuilder getDocumentBuilder() throws EccpException {
        if (documentBuilder == null) {
            try {
                documentBuilder = getDocumentBuilderFactory().newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new EccpException("Não foi possível criar o documento", ex);
            }
        }
        return documentBuilder;
    }
    
    private Document createDocument() throws EccpException {
        return getDocumentBuilder().newDocument();
    }

    private void marshal() throws EccpException {
        try {
            document = createDocument();
            EccpRequestWrapper requestWrapper = new EccpRequestWrapper();
            requestWrapper.setRequest(wrapped);
            JAXBContext jc = JAXBContext.newInstance(EccpRequestWrapper.class, wrapped.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(requestWrapper, document);
        } catch (JAXBException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    Document toDocument() {
        return document;
    }
    
    @Override
    public String toString() {
        if (xmlString == null) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                StreamResult result = new StreamResult(output);
                transformer.transform(domSource, result);
                xmlString = output.toString();
            } catch (TransformerException ex) {
                LOG.log(Level.SEVERE, "Problema ao converter DOM Document para String", ex);
            }
        } 
        return xmlString;
    }
}