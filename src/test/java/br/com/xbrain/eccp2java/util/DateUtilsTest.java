package br.com.xbrain.eccp2java.util;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A data de testes é 2016/03/02 10:53:0'0"
 * @author xbrain
 */
public class DateUtilsTest {
    
    private static final int YEAR = 2016;
    
    /**
     * O índice dos meses na classe Calendar começa em 0 (Janeiro == 0)
     */
    private static final int MONTH = 2;
    
    private static final int DAY_OF_MONTH = 2;
    
    private static final int HOUR_OF_DAY = 10;
    
    private static final int MINUTE = 53;
    
    private static final int SECOND = 0;
    
    private static final int MILLISECOND = 0;
    
    private static final Date COMPARING_DATE;
    
    private static final Time COMPARING_TIME;
    
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, MONTH - 1, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE, SECOND);
        calendar.set(Calendar.MILLISECOND, MILLISECOND);
        COMPARING_DATE = calendar.getTime();
        
        calendar.set(0, 0, 0);
        COMPARING_TIME = new Time(calendar.getTime().getTime());
    }
    
    public DateUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateDate() {
        System.out.println("createDate");
        assertEquals(COMPARING_DATE, DateUtils.createDate(YEAR, MONTH, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND));
    }

    @Test
    public void testCreateTime() {
        System.out.println("createTime");
        assertEquals(COMPARING_TIME, DateUtils.createTime(HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND));
    }
    
}
