package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.database.model.Call;
import br.com.xbrain.eccp2java.database.model.CallAttribute;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.DateUtils;
import br.com.xbrain.elastix.DialerCampaign;
import br.com.xbrain.elastix.ElastixIntegration;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author xbrain
 */
public class CampaignManagerTest {
    
    public CampaignManagerTest() {
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
    public void testCreate() {
        System.out.println("of");
        ElastixEMFs eccp2JavaEMF = Mockito.mock(ElastixEMFs.class);
        CampaignManager campaign = CampaignManager.create(eccp2JavaEMF);
        assertNotNull(campaign);
    }

    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        CampaignManager campaignCreator = Mockito.mock(CampaignManager.class);
        
        Campaign campaign = new Campaign();
        campaign.setName("Campanha " + new Date());
        campaign.setDatetimeInit(DateUtils.createDate(2016, 2, 15, 0, 0, 0, 0));
        campaign.setDatetimeEnd(DateUtils.createDate(2016, 3, 15, 0, 0, 0, 0));
        campaign.setDaytimeInit(DateUtils.createTime(8, 0, 0, 0));
        campaign.setDaytimeEnd(DateUtils.createTime(19, 0, 0, 0));
        campaign.setRetries(10);
        campaign.setQueue("1111");
        campaign.setMaxCanales(10);
        campaign.setEstatus("A");
        campaign.setScript("<h1>Ola " + new Date() + "</h1>");
        campaign.setContext("form-internal");
        
        createCall(campaign, "8007", "123456", "Guloso", "Teste");
        createCall(campaign, "8007", "123456", "Pedro Ivo Bubum Guloso", "Teste");
        createCall(campaign, "8006", "123457", "Martoniano Tonhão", "Teste");
        createCall(campaign, "8002", "123456", "Silvio Charlie Brown", "Teste");
        createCall(campaign, "8001", "123456", "Jaumzera", "Teste");
        createCall(campaign, "8007", "123456", "Guloso", "Teste");
        createCall(campaign, "8007", "123456", "Pedro Ivo Bubum Guloso", "Teste");
        createCall(campaign, "8006", "123457", "Martoniano Tonhão", "Teste");
        createCall(campaign, "8002", "123456", "Silvio Charlie Brown", "Teste");
        createCall(campaign, "8001", "123456", "Jaumzera", "Teste");
        createCall(campaign, "8007", "123456", "Guloso", "Teste");
        createCall(campaign, "8007", "123456", "Pedro Ivo Bubum Guloso", "Teste");
        createCall(campaign, "8006", "123457", "Martoniano Tonhão", "Teste");
        createCall(campaign, "8002", "123456", "Silvio Charlie Brown", "Teste");
        createCall(campaign, "8001", "123456", "Jaumzera", "Teste");
        
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("Chamou salvar: " + invocation.getArguments().toString());
                return null;
            }
        }).when(campaignCreator).save(campaign);
        campaignCreator.save(campaign);
    }

    private static void createCall(Campaign campaign, String number, String idHP, String name, String obs) {
        Call call = new Call();
        call.setCampaign(campaign);
        call.setPhone(number);

        campaign.getCalls().add(call);

        CallAttribute callAttribute1 = new CallAttribute("id_hp", idHP, 1, call);
        CallAttribute callAttribute2 = new CallAttribute("nome", name, 2, call);
        CallAttribute callAttribute3 = new CallAttribute("obs", obs, 3, call);

        call.getAttributes().add(callAttribute1);
        call.getAttributes().add(callAttribute2);
        call.getAttributes().add(callAttribute3);
    }
}
