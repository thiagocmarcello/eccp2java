package br.com.xbrain.eccp2java.entity.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EccpTimeAdapter extends XmlAdapter<String, Date> {
    
    private static final SimpleDateFormat KK_MM_SS = new SimpleDateFormat("kk:mm:ss");

    @Override
    public Date unmarshal(String value) throws Exception {
        return KK_MM_SS.parse(value);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return KK_MM_SS.format(date);
    }
    
}
