package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueDetail;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.model.QueuesDetailPK;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.elastix.DialerAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
public class QueueDAO extends AbstractDAO<Queue, String> {

    private static final Logger LOG = Logger.getLogger(QueueDAO.class.getName());

    public static final QueueDAO create(EntityManagerFactory emf) {
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
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_SAVE_QUEUE, ex);
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
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_REMOVE_QUEUE, ex);
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
    public Queue find(String pk) throws ElastixIntegrationException {
        EntityManager em = emf.createEntityManager();
        try {
            QueueConfig queueConfig = em.find(QueueConfig.class, pk);
            if (queueConfig == null) {
                throw ElastixIntegrationException.create(ElastixIntegrationException.Error.QUEUE_NOT_FOUND)
                        .addInfo("message", "A fila '%s' não foi encontrada.", pk);
            }

            List<QueueDetail> queueDetails
                    = em.createQuery("SELECT qd FROM QueueDetail qd WHERE qd.queuesDetailsPK.id = :_queueId")
                    .setParameter("_queueId", queueConfig.getExtension())
                    .getResultList();

            return new Queue(queueConfig, queueDetails);
        } catch (PersistenceException ex) {
            em.getTransaction().rollback();
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_FIND_QUEUE, ex)
                    .addInfo("message", "Houve um erro buscando a fila especificada");
        } finally {
            em.close();
        }
    }

    public void updateQueueAgents(final String queueId, final List<DialerAgent> agents) throws ElastixIntegrationException {
        final EntityManager em = emf.createEntityManager();

        Callable deleteAgents = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                em.createQuery("DELETE FROM QueueDetail qd WHERE qd.queuesDetailsPK.keyword = :_keyword AND qd.queuesDetailsPK.id = :_id ")
                        .setParameter("_keyword", "member")
                        .setParameter("_id", queueId)
                        .executeUpdate();
                return null;
            }
        };

        Callable insertAgents = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (DialerAgent agent : agents) {
                    QueuesDetailPK pk = new QueuesDetailPK(queueId, "member", agent.getElaxtixNameWithPenalty());
                    QueueDetail queueDetail = new QueueDetail(pk);
                    queueDetail.setFlags(0);
                    em.persist(queueDetail);
                }

                return null;
            }
        };

        executeTransactional(em, deleteAgents, insertAgents);
    }

    public void deleteQueueAgents(final String queueId, final List<DialerAgent> agents) throws ElastixIntegrationException {
        final EntityManager em = emf.createEntityManager();

        Callable deleteAgents = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                em.createQuery("DELETE FROM QueueDetail qd WHERE qd.queuesDetailsPK.keyword = :_keyword AND qd.queuesDetailsPK.id = :_id ")
                        .setParameter("_keyword", "member")
                        .setParameter("_id", queueId)
                        .executeUpdate();
                return null;
            }
        };
        executeTransactional(em, deleteAgents);
    }
}
