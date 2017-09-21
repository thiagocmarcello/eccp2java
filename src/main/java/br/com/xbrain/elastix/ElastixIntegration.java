package br.com.xbrain.elastix;

import br.com.xbrain.eccp2java.database.CampaignManager;
import br.com.xbrain.eccp2java.database.QueueManager;
import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.enums.EConfiguracao;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class ElastixIntegration {

    private static final Logger LOG = Logger.getLogger(ElastixIntegration.class.getName());

    private URI elastixHost;

    private ElastixEMFs elastixEMFs;

    public static ElastixIntegration create(String url, String user, String password) {
        validateCreateParams(url, user, password);
        try {
            ElastixIntegration elastixIntegration = new ElastixIntegration();
            elastixIntegration.elastixHost = new URI(url);
            elastixIntegration.elastixEMFs = ElastixEMFs.create(elastixIntegration.elastixHost.toString(), user, password);
            return elastixIntegration;
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URI inválida. " + ex.getMessage(), ex);
        }
    }

    private static void validateCreateParams(String host, String user, String password) {
        StringBuilder errorMessages = new StringBuilder();
        if (StringUtils.isBlank(host)) {
            errorMessages.append("Host inválido");
        }

        if (StringUtils.isBlank(user)) {
            errorMessages.append("Usuário inválido");
        }

        if (StringUtils.isBlank(password)) {
            errorMessages.append("Senha inválida");
        }

        if (errorMessages.length() > 0) {
            throw new IllegalArgumentException(errorMessages.toString());
        }
    }

    private ElastixIntegration() {
    }

    private QueueManager createQueueManager() {
        return QueueManager.create(elastixEMFs, elastixHost.toString() + ":" + EConfiguracao.PORTA_QUEUES_RELOAD.getValor());
    }

    private CampaignManager createCampaignManager() {
        return CampaignManager.create(elastixEMFs);
    }

    public Campaign createCampaign(DialerCampaign dialerCampaign) throws ElastixIntegrationException {
        createQueueManager().save(dialerCampaign.getQueue());
        return createCampaignManager().save(dialerCampaign.getCampaign());
    }

    public Campaign updateCampaign(Campaign campaign) throws ElastixIntegrationException {
        return createCampaignManager().save(campaign);
    }

    public Campaign getCampaign(Integer id) throws ElastixIntegrationException {
        return createCampaignManager().find(id);
    }

    public Queue getQueue(String id) throws ElastixIntegrationException {
        return createQueueManager().find(id);
    }

    public Queue updateQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        QueueManager queueManager = createQueueManager();
        queueManager.updateQueueAgents(queueId, agents);
        return queueManager.find(queueId);
    }

    public Queue deleteQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        QueueManager queueManager = createQueueManager();
        queueManager.deleteQueueAgents(queueId, agents);
        return queueManager.find(queueId);
    }

    public void deleteCampaign(Campaign campaign) throws ElastixIntegrationException {
        CampaignManager campaignManager = createCampaignManager();
        QueueManager queueManager = createQueueManager();
        Queue queue = queueManager.find(campaign.getQueue());
        campaignManager.remove(campaign);
        queueManager.remove(queue);
    }

    public List<DialerCampaign> getActiveDialerCampaigns() throws ElastixIntegrationException {
        CampaignManager campaignManager = createCampaignManager();
        List<Campaign> campaigns = campaignManager.getActives();
        List<DialerCampaign> dialerCampaigns = new ArrayList<>();
        QueueManager queueManager = createQueueManager();
        for (Campaign campaign : campaigns) {
            Queue queue = queueManager.find(campaign.getQueue());
            DialerCampaign dialerCampaign = DialerCampaign.create(campaign, queue);
            dialerCampaigns.add(dialerCampaign);
        }
        return dialerCampaigns;
    }
}
