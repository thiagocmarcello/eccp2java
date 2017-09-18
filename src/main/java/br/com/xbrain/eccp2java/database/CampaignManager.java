package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import java.util.List;
import java.util.logging.Logger;

// TODO gerar teste para verificar se o form padrão existe
/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
public class CampaignManager extends AbstractManager {

    private static final Logger LOG = Logger.getLogger(CampaignManager.class.getName());
    
    public static final CampaignManager create(ElastixEMFs elastixEMFs) {
        return new CampaignManager(elastixEMFs);
    }
    
    private CampaignManager(ElastixEMFs elastixEMFs) {
        super(elastixEMFs);
    }

    // Por enquanto usará somente o form padrão da discadora (o único) cujo o id = 1;
    public Campaign save(Campaign campaign) throws ElastixIntegrationException {
        if(campaign.getId() == null) {
            return createCampaignDAO().create(campaign);
        } else {
            return createCampaignDAO().update(campaign);
        }
    }

    public void remove(Campaign campaign) throws ElastixIntegrationException {
        createCampaignDAO().remove(campaign);
    }

    public Campaign find(Integer id) throws ElastixIntegrationException {
        return createCampaignDAO().find(id);
    }

    private CampaignDAO createCampaignDAO() {
        return CampaignDAO.create(elastixEMFs.getCallCenterEMF());
    }
    
    public List<Campaign> getActives() throws ElastixIntegrationException {
        return createCampaignDAO().getActives();
    }
    
    public List<Campaign> hasCampaign(String idQueue) throws ElastixIntegrationException {
        return createCampaignDAO().findPorQueue(idQueue);
    }
}
