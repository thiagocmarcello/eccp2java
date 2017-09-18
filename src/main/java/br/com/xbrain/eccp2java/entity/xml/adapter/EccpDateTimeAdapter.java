package br.com.xbrain.eccp2java.entity.xml.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class EccpDateTimeAdapter extends XmlAdapter<String, Date> {
    
    private static final SimpleDateFormat YYYY_MM_DD_KK_MM_SS_FORMAT = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

    @Override
    public Date unmarshal(String v) throws Exception {
        return YYYY_MM_DD_KK_MM_SS_FORMAT.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return YYYY_MM_DD_KK_MM_SS_FORMAT.format(v);
    }
    
}
