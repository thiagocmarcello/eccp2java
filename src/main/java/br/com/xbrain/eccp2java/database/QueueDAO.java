package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.model.QueueDetail;
import br.com.xbrain.eccp2java.database.model.QueuesDetailPK;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.elastix.DialerAgent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class QueueDAO extends AbstractDAO<Queue, String> {

    private static final Logger LOG = Logger.getLogger(QueueDAO.class.getName());

    public static QueueDAO create(EntityManagerFactory emf) {
        QueueDAO queueDAO = new QueueDAO();
        queueDAO.emf = emf;
        return queueDAO;
    }

    private EntityManagerFactory emf;

    private QueueDAO() {
    }

    @Override
    public Queue create(Queue queue) throws ElastixIntegrationException {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            QueueConfig mergedQueueConfig = entityManager.merge(queue.getQueueConfig());
            List<QueueDetail> mergedQueueDetails = new ArrayList<>();
            for (QueueDetail item : queue.getQueueDetails()) {
                QueueDetail mergedQueueDetail = entityManager.merge(item);
                mergedQueueDetails.add(mergedQueueDetail);
            }
            Queue mergedQueue = new Queue(mergedQueueConfig, mergedQueueDetails);
            entityManager.getTransaction().commit();
            return mergedQueue;
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
            throw new ElastixIntegrationException("Não foi possível salvar a fila: " + queue, ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(Queue queue) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM QueueDetail qd WHERE qd.queuesDetailsPK.id = :_queueConfigId")
                    .setParameter("_queueConfigId", queue.getQueueConfig().getExtension())
                    .executeUpdate();
            em.remove(em.find(QueueConfig.class, queue.getQueueConfig().getExtension()));
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new ElastixIntegrationException("Não foi possível remover a fila.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public Queue update(Queue object) throws ElastixIntegrationException {
        // TODO implementar
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Queue find(String id) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            QueueConfig queueConfig = em.find(QueueConfig.class, id);
            if (queueConfig == null) {
                throw new ElastixIntegrationException("Fila não encontrada", null)
                        .addInfo("message", "A fila '%s' não foi encontrada.", id);
            }

            List<QueueDetail> queueDetails
                    = em.createQuery(
                    "SELECT qd FROM QueueDetail qd WHERE qd.queuesDetailsPK.id = :_queueId",
                    QueueDetail.class)
                    .setParameter("_queueId", queueConfig.getExtension())
                    .getResultList();

            return new Queue(queueConfig, queueDetails);
        } catch (PersistenceException ex) {
            throw new ElastixIntegrationException("Não foi possível encontrar a fila: " + id, ex);
        } finally {
            em.close();
        }
    }

    public void updateQueueAgents(final String queueId, final List<DialerAgent> agents)
            throws ElastixIntegrationException {

        final EntityManager em = emf.createEntityManager();

        Callable deleteAgents = (Callable<Void>) () -> {
            em.createQuery("DELETE FROM QueueDetail qd "
                    + "WHERE qd.queuesDetailsPK.keyword = :_keyword "
                    + "AND qd.queuesDetailsPK.id = :_id ")
                    .setParameter("_keyword", "member")
                    .setParameter("_id", queueId)
                    .executeUpdate();
            return null;
        };

        Callable insertAgents = (Callable<Void>) () -> {
            for (DialerAgent agent : agents) {
                QueuesDetailPK pk = new QueuesDetailPK(queueId, "member", agent.getElaxtixNameWithPenalty());
                QueueDetail queueDetail = new QueueDetail(pk);
                queueDetail.setFlags(0);
                em.persist(queueDetail);
            }

            return null;
        };

        executeInTransaction(em, deleteAgents, insertAgents);
    }

    public void deleteQueueAgents(final String queueId) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        Callable deleteAgents = (Callable<Void>) () -> {
            em.createQuery("DELETE FROM QueueDetail qd "
                    + "WHERE qd.queuesDetailsPK.keyword = :_keyword "
                    + "AND qd.queuesDetailsPK.id = :_id ")
                    .setParameter("_keyword", "member")
                    .setParameter("_id", queueId)
                    .executeUpdate();
            return null;
        };

        executeInTransaction(em, deleteAgents);
    }
}
