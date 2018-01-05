package br.com.xbrain.eccp2java.xml.entity.xml.adapter;

import br.com.xbrain.eccp2java.entity.xml.adapter.EccpTimeAdapter;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author xbrain
 */
public class EccpTimeAdapterTest {
    
    private static final Date TEST_DATE;
    private static final String TEST_STRING;
    
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 7);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        TEST_DATE = calendar.getTime();
        TEST_STRING = "11:07:00";
    }
    
    public EccpTimeAdapterTest() {
    }
    
    @Test
    public void shouldReturnCorrespondingStringFormatedTimeObject() throws Exception {
        EccpTimeAdapter adapter = new EccpTimeAdapter();
        String marshaled = adapter.marshal(TEST_DATE);
        assertEquals(TEST_STRING, marshaled);
    }
    
    @Test
    public void shouldReturnCorrespondingDateObject () throws Exception {
        EccpTimeAdapter adapter = new EccpTimeAdapter();
        Date unmarshaled = adapter.unmarshal(TEST_STRING);
        
        Calendar correctDate = Calendar.getInstance();
        correctDate.setTime(TEST_DATE);
        
        Calendar parsedDate = Calendar.getInstance();
        parsedDate.setTime(unmarshaled);
        
        assertEquals(correctDate.get(Calendar.HOUR_OF_DAY), correctDate.get(Calendar.HOUR_OF_DAY));
        assertEquals(correctDate.get(Calendar.MINUTE), correctDate.get(Calendar.MINUTE));
        assertEquals(correctDate.get(Calendar.SECOND), correctDate.get(Calendar.SECOND));
    }
}