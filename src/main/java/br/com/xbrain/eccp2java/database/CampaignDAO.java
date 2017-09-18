package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
public class CampaignDAO extends AbstractDAO<Campaign, Integer> {

    private static final Logger LOG = Logger.getLogger(CampaignDAO.class.getName());

    public static CampaignDAO create(EntityManagerFactory emf) {
        return new CampaignDAO(emf);
    }

    private final EntityManagerFactory emf;

    private CampaignDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Campaign create(Campaign campaign) throws ElastixIntegrationException {
        ValidatorFactory validator = Validation.buildDefaultValidatorFactory();
        validator.getValidator().validate(campaign);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(campaign);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_SAVE_CAMPAIGN, ex);
        } finally {
            em.close();
        }
        return campaign;
    }

    @Override
    public void remove(Campaign campaign) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Campaign mergedCampaign = em.getReference(Campaign.class, campaign.getId());
            em.remove(mergedCampaign);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_REMOVE_CAMPAIGN, ex);
        } finally {
            em.close();
        }
    }

    @Override
    public Campaign update(Campaign campaign) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Campaign mergedCampaign = em.merge(campaign);
            em.getTransaction().commit();
            return mergedCampaign;
        } catch (PersistenceException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_UPDATE_CAMPAIGN, ex);
        } finally {
            em.close();
        }
    }

    @Override
    public Campaign find(Integer pk) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Campaign.class, pk);
        } catch (PersistenceException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_FIND_CAMPAIGN, ex);
        } finally {
            em.close();
        }
    }

    public List<Campaign> getActives() throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Campaign c WHERE c.estatus = 'A'").getResultList();
        } catch (PersistenceException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_FIND_CAMPAIGN, ex);
        } finally {
            em.close();
        }
    }

    public List<Campaign> findPorQueue(String idQueue) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Campaign c WHERE c.queue = '" + idQueue + "'").getResultList();
        } catch (PersistenceException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_FIND_CAMPAIGN, ex);
        } finally {
            em.close();
        }
    }
}
