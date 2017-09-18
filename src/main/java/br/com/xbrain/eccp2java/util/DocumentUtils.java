package br.com.xbrain.eccp2java.util;

import br.com.xbrain.eccp2java.exception.EccpException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class DocumentUtils {

    private static final Logger LOG = Logger.getLogger(DocumentUtils.class.getName());
    
    public static final DocumentBuilderFactory DOCUMENT_BUILD_FACTORY = DocumentBuilderFactory.newInstance();

    public static Document parseDocument(InputStream inputStream) throws EccpException {
        try {
            return createDocumentBuilder().parse(inputStream);
        } catch (SAXException | IOException ex) {
            throw EccpException.create(EccpException.Error.CAN_NOT_CREATE_DOCUMENT, ex);
        }
    }
    
    public static Document parseDocument(String xml) throws EccpException {
        try {
            return createDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
        } catch (SAXException | IOException ex) {
            throw EccpException.create(EccpException.Error.CAN_NOT_CREATE_DOCUMENT, ex);
        }
    }

    public static void toXml(Object obj) {
        Document doc = createDocumentBuilder().newDocument();
        Element root = doc.createElement("<request>");
        root.setAttribute("id", null);
    }

    public static Document createDocument() {
        return createDocumentBuilder().newDocument();
    }

    public static DocumentBuilder createDocumentBuilder() {
        try {
            return DOCUMENT_BUILD_FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("Não foi possível criar o document builder", ex);
        }
    }
    
    public static Transformer createTransformer()  {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            return transformer;
        } catch (TransformerConfigurationException ex) {
            throw new RuntimeException("Problema ao criar o transformer");
        }
    }

    public static void printDocument(Document doc, OutputStream out) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
        } catch(IOException | TransformerException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
