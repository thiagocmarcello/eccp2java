package br.com.xbrain.eccp2java.database.model;

import br.com.xbrain.eccp2java.util.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CampaignTest {
    
    /**
     * Cria duas campanhas iguais
     */
    private Campaign campaign1, campaign2;
    
    private Validator validator;
    
    public CampaignTest() {
    }
    
    @Before
    public void setUp() {
        campaign1 = new Campaign();
        campaign1.setId(1);
        campaign1.setName("Campanha " + new Date());
        campaign1.setDatetimeInit(DateUtils.createDate(2016, 2, 15, 0, 0, 0, 0));
        campaign1.setDatetimeEnd(DateUtils.createDate(2016, 3, 15, 0, 0, 0, 0));
        campaign1.setDaytimeInit(DateUtils.createTime(8, 0, 0, 0));
        campaign1.setDaytimeEnd(DateUtils.createTime(19, 0, 0, 0));
        campaign1.setRetries(10);
        campaign1.setQueue("1111");
        campaign1.setMaxCanales(10);
        campaign1.setEstatus("A");
        campaign1.setScript("<h1>Ola " + new Date() + "</h1>");
        campaign1.setContext("form-internal");
        
        campaign2 = new Campaign();
        campaign2.setId(1);
        campaign2.setName("Campanha " + new Date());
        campaign2.setDatetimeInit(DateUtils.createDate(2016, 2, 15, 0, 0, 0, 0));
        campaign2.setDatetimeEnd(DateUtils.createDate(2016, 3, 15, 0, 0, 0, 0));
        campaign2.setDaytimeInit(DateUtils.createTime(8, 0, 0, 0));
        campaign2.setDaytimeEnd(DateUtils.createTime(19, 0, 0, 0));
        campaign2.setRetries(10);
        campaign2.setQueue("1111");
        campaign2.setMaxCanales(10);
        campaign2.setEstatus("A");
        campaign2.setScript("<h1>Ola " + new Date() + "</h1>");
        campaign2.setContext("form-internal");
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testValidate() {
        System.out.println("validate");
        validator.validate(campaign1);
        validator.validate(campaign2);
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals(campaign1, campaign2);
        campaign2.setId(2);
        assertNotEquals(campaign1, campaign2);
        campaign2.setId(1);
        assertEquals(campaign1, campaign2);
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        campaign2.setId(2);
        assertNotEquals(campaign1.hashCode(), campaign2.hashCode());
        campaign2.setId(1);
        assertEquals(campaign1.hashCode(), campaign2.hashCode());
    }
}
