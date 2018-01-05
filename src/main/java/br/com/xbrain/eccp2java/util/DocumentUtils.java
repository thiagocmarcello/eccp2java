package br.com.xbrain.eccp2java.util;

import br.com.xbrain.eccp2java.exception.EccpException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class DocumentUtils {

    private static final Logger LOG = Logger.getLogger(DocumentUtils.class.getName());
    
    private static final DocumentBuilderFactory DOCUMENT_BUILD_FACTORY = DocumentBuilderFactory.newInstance();

    public static Document parseDocument(InputStream inputStream) throws EccpException {
        try {
            return createDocumentBuilder().parse(inputStream);
        } catch (SAXException | IOException ex) {
            throw new EccpException("Não foi possível ler o documento a paprtir do inputStream", ex);
        }
    }
    
    public static Document parseDocument(String xml) throws EccpException {
        try {
            return createDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
        } catch (SAXException | IOException ex) {
            throw new EccpException("Não foi possível criar o documento a partir do String", ex);
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
            return transformerFactory.newTransformer();
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
        } catch (IOException | TransformerException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
