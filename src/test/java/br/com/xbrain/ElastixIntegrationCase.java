package br.com.xbrain;

import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.elastix.DialerCampaign;
import br.com.xbrain.elastix.ElastixIntegration;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isNull;

public class ElastixIntegrationCase {

    @Test
    public void deveCriarUmaCampanhaListarEApagar() {
        try {
            ElastixIntegration elastix = ElastixIntegration.create(
                    EConfiguracaoDev.IP_BANCO.getValor() + EConfiguracaoDev.PORTA_BANCO.getValor(),
                    EConfiguracaoDev.USUARIO_BANCO.getValor(),
                    EConfiguracaoDev.SENHA_BANCO.getValor());

            Campaign lastCampaign = elastix.getLastSavedCampaign();
            Integer currentCampaignId = 1;

            if(lastCampaign != null) {
                currentCampaignId++;
            }

            AppTest.createDialerCampaign(currentCampaignId);

            List<DialerCampaign> dialerCampaigns = elastix.getActiveDialerCampaigns();
            if (CollectionUtils.isNotEmpty(dialerCampaigns)) {
                System.out.println("Quantidade de campanhas ativas: " + dialerCampaigns.size());
                for (DialerCampaign dialerCampaign : dialerCampaigns) {
                    System.out.println("\tCampanha: " + dialerCampaign.getCampaign());
                    System.out.println("\tFila: " + dialerCampaign.getQueue());
                    System.out.println("\tQtde agentes: "
                            + CollectionUtils.size(dialerCampaign.getQueue().getDialerAgents()));
                    System.out.println("\tAgentes: " + dialerCampaign.getQueue().getDialerAgents());
                }
            }

            lastCampaign = elastix.getLastSavedCampaign();
            System.out.println("Last saved campaign: " + lastCampaign);
            assertThat("Deveria ter criado a campanha.", lastCampaign, is(notNullValue()));

            elastix.deleteCampaign(lastCampaign);
            lastCampaign = elastix.getCampaign(currentCampaignId);
            assertThat("Deveria ter apagado a campanha.", lastCampaign, is(isNull()));

        } catch (ElastixIntegrationException ex) {
            fail("Problema ao executar o teste.");
            ex.printStackTrace();
        }
    }
}
