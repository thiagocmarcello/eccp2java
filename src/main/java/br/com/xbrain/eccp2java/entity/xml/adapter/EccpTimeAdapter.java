package br.com.xbrain.eccp2java.entity.xml.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class EccpTimeAdapter extends XmlAdapter<String, Date> {
    
    private static final SimpleDateFormat KK_MM_SS = new SimpleDateFormat("kk:mm:ss");

    @Override
    public Date unmarshal(String v) throws Exception {
        return KK_MM_SS.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return KK_MM_SS.format(v);
    }
    
}
