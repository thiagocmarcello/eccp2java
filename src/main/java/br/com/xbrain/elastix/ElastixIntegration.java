package br.com.xbrain.elastix;

import br.com.xbrain.eccp2java.database.CampaignManager;
import br.com.xbrain.eccp2java.database.QueueManager;
import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.database.model.Ipv4;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ElastixIntegration {

    private static final Logger LOG = Logger.getLogger(ElastixIntegration.class.getName());

    private Ipv4 elastixHost;

    private ElastixEMFs elastixEMFs;

    public static ElastixIntegration create(String jdbcUrl, String user, String password) {
        validateCreateParams(jdbcUrl, user, password);
        try {
            ElastixIntegration elastixIntegration = new ElastixIntegration();
            elastixIntegration.elastixHost = parseIpFromJdbcUrl(jdbcUrl);
            elastixIntegration.elastixEMFs = ElastixEMFs.create(
                    new URI(jdbcUrl).toString(),
                    user,
                    password);
            return elastixIntegration;
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URI inválida. " + ex.getMessage(), ex);
        }
    }

    private static Ipv4 parseIpFromJdbcUrl(String jdbcUrl) throws URISyntaxException {
        String[] ipParts = jdbcUrl.replaceAll(
                "(?:jdbc:.+://)([\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3})(?::[\\d]{4,6})",
                "$1").split("\\.");
        return Ipv4.of(ipParts[0], ipParts[1], ipParts[2], ipParts[3]);
    }

    private static void validateCreateParams(String host, String user, String password) {
        StringBuilder errorMessages = new StringBuilder();
        if (StringUtils.isBlank(host)) {
            errorMessages.append("\n\tHost inválido");
        }

        if (StringUtils.isBlank(user)) {
            errorMessages.append("\n\tUsuário inválido");
        }

        if (StringUtils.isBlank(password)) {
            errorMessages.append("\n\tSenha inválida");
        }

        if (errorMessages.length() > 0) {
            throw new IllegalArgumentException(errorMessages.toString());
        }
    }

    private ElastixIntegration() {
    }

    private QueueManager createQueueManager() throws ElastixIntegrationException {
        return QueueManager.create(
                elastixEMFs,
                elastixHost,
                Integer.parseInt(EConfiguracaoDev.PORTA_QUEUES_RELOAD.getValor()));
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

    public Campaign getLastSavedCampaign() throws ElastixIntegrationException {
        return createCampaignManager().findLast();
    }

    public Queue getQueue(String id) throws ElastixIntegrationException {
        return createQueueManager().find(id);
    }

    public Queue updateQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        QueueManager queueManager = createQueueManager();
        queueManager.updateQueueAgents(queueId, agents);
        return queueManager.find(queueId);
    }

    public Queue deleteQueueAgents(String queueId) throws ElastixIntegrationException {
        QueueManager queueManager = createQueueManager();
        queueManager.deleteQueueAgents(queueId);
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
