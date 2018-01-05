package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEmfs;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;

import java.util.List;

// TODO gerar teste para verificar se o form padrão existe
public class CampaignManager extends AbstractManager {

    public static CampaignManager create(ElastixEmfs elastixEmfs) {
        return new CampaignManager(elastixEmfs);
    }
    
    private CampaignManager(ElastixEmfs elastixEmfs) {
        super(elastixEmfs);
    }

    // FIXME Por enquanto usará somente o form padrão da discadora (o único) cujo o id = 1;
    public Campaign save(Campaign campaign) throws ElastixIntegrationException {
        if (campaign.getId() == null) {
            return createCampaignDao().create(campaign);
        } else {
            return createCampaignDao().update(campaign);
        }
    }

    public void remove(Campaign campaign) throws ElastixIntegrationException {
        createCampaignDao().remove(campaign);
    }

    public Campaign find(Integer id) throws ElastixIntegrationException {
        return createCampaignDao().find(id);
    }

    private CampaignDao createCampaignDao() {
        return CampaignDao.create(elastixEmfs.getCallCenterEmf());
    }
    
    public List<Campaign> getActives() throws ElastixIntegrationException {
        return createCampaignDao().getActives();
    }
    
    public List<Campaign> hasCampaign(String idQueue) throws ElastixIntegrationException {
        return createCampaignDao().findPorQueue(idQueue);
    }

    public Campaign findLast() throws ElastixIntegrationException {
        return createCampaignDao().findLast();
    }
}
