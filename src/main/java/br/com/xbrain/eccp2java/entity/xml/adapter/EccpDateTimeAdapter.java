package br.com.xbrain.eccp2java.entity.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EccpDateTimeAdapter extends XmlAdapter<String, Date> {
    
    private static final SimpleDateFormat YYYY_MM_DD_KK_MM_SS_FORMAT = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

    @Override
    public Date unmarshal(String value) throws Exception {
        return YYYY_MM_DD_KK_MM_SS_FORMAT.parse(value);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return YYYY_MM_DD_KK_MM_SS_FORMAT.format(date);
    }
    
}
