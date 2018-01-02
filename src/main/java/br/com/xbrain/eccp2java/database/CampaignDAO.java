package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.elastix.ElastixIntegration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.logging.Logger;

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
            throw new ElastixIntegrationException("Nâo foi possível salvar a campanaha", ex);
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
            throw new ElastixIntegrationException("Não foi possível remover a campanha: " + campaign.getId(), ex);
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
            throw new ElastixIntegrationException("Não foi possível atualizar a campanha: " + campaign.getId(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public Campaign find(Integer id) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Campaign.class, id);
        } catch (PersistenceException ex) {
            throw new ElastixIntegrationException("Não foi possível encontrar a campanha: " + id, ex);
        } finally {
            em.close();
        }
    }

    public List<Campaign> getActives() throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Campaign c WHERE c.estatus = 'A'", Campaign.class)
                    .getResultList();
        } catch (PersistenceException ex) {
            throw new ElastixIntegrationException("Não foi possível carregar as campanhas.", ex);
        } finally {
            em.close();
        }
    }

    public List<Campaign> findPorQueue(String idQueue) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Campaign c WHERE c.queue = :_idQueue", Campaign.class)
                    .setParameter("_idQueue", idQueue)
                    .getResultList();
        } catch (PersistenceException ex) {
            throw new ElastixIntegrationException("Não foi possível encontrar a campanha da fila: " + idQueue, ex);
        } finally {
            em.close();
        }
    }

    public Campaign findLast() throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT MAX(c) FROM Campaign c WHERE c.estatus = 'A'", Campaign.class)
                    .getSingleResult();
        } catch(PersistenceException ex) {
            throw new ElastixIntegrationException("Não foi possível consultar a última campanha", ex);
        }

    }
}
