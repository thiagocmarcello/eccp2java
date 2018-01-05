package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEmfs;
import br.com.xbrain.eccp2java.database.model.Call;
import br.com.xbrain.eccp2java.database.model.CallAttribute;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.util.DateUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class CampaignManagerTest {
    
    public CampaignManagerTest() {
    }
    
    @Test
    public void testCreate() {
        System.out.println("of");
        ElastixEmfs eccp2JavaEMF = Mockito.mock(ElastixEmfs.class);
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
        
        Mockito.doAnswer(invocation -> {
            System.out.println("Chamou salvar: " + Arrays.toString(invocation.getArguments()));
            return null;
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
