package br.com.xbrain.eccp2java.xml.entity.xml.adapter;

import br.com.xbrain.eccp2java.entity.xml.adapter.EccpDateTimeAdapter;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EccpDateTimeAdapterTest {
    
    private static final Date TEST_DATE;
    private static final String TEST_STRING;
    
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 17);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 4);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        TEST_DATE = calendar.getTime();
        TEST_STRING = "2016-02-17 15:04:00";
    }
    
    public EccpDateTimeAdapterTest() {
    }
    
    @Test
    public void shouldReturnCorrespondingStringFormatedDateObject() throws Exception {
        EccpDateTimeAdapter adapter = new EccpDateTimeAdapter();
        String marshaled = adapter.marshal(TEST_DATE);
        assertEquals(TEST_STRING, marshaled);
    }
    
    @Test
    public void shouldReturnCorrespondingDateObject () throws Exception {
        EccpDateTimeAdapter adapter = new EccpDateTimeAdapter();
        Date unmarshaled = adapter.unmarshal(TEST_STRING);
        assertEquals(TEST_DATE, unmarshaled);
    }
}