package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.enums.EConfiguracao;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.HttpUtils;
import br.com.xbrain.eccp2java.util.HttpUtils.Param;
import br.com.xbrain.elastix.DialerAgent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
public class QueueManager {

    private static final Logger LOG = Logger.getLogger(QueueManager.class.getName());

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private String endpoint;

    public static QueueManager create(ElastixEMFs elastixEMFs) {
        QueueManager queueManager = new QueueManager();
        queueManager.elastixEMFs = elastixEMFs;
        String newEndpoint
                = EConfiguracao.IP_QUEUES_RELOAD.getValor()
                + EConfiguracao.PORTA_QUEUES_RELOAD.getValor()
                + EConfiguracao.URL_QUEUES_RELOAD.getValor();
        queueManager.endpoint = newEndpoint;
        return queueManager;
    }

    private ElastixEMFs elastixEMFs;

    private QueueManager() {
    }

    public Queue save(Queue queue) throws ElastixIntegrationException {
        try {
            validate(queue);
            validatorFactory.getValidator().validate(queue.getQueueConfig());
            QueueDAO queueDAO = createQueueDAO();
            Queue savedQueue = queueDAO.create(queue);
            restartAsteriskQueues();
            return savedQueue;
        } catch (ValidationException | IllegalArgumentException ex) {
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.CAN_NOT_SAVE_QUEUE, ex);
        }
    }

    public Queue find(String queueId) throws ElastixIntegrationException {
        return createQueueDAO().find(queueId);
    }

    public void remove(Queue queue) throws ElastixIntegrationException {
        createQueueDAO().remove(queue);
    }

    private void validate(Queue queue) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Queue>> queueErrors = validator.validate(queue);
        Set<ConstraintViolation<QueueConfig>> queueConfigErrors = validator.validate(queue.getQueueConfig());
        int errorCount = queueErrors.size() + queueConfigErrors.size();
        if (errorCount > 0) {
            throw new IllegalArgumentException("Preencha corretamente o objeto QueueConfig");
        }
    }

    public int restartAsteriskQueues() {
        try {
            byte[] hashArray = MessageDigest.getInstance("SHA1").digest("xbrain".getBytes());
            String hash = new BigInteger(1, hashArray).toString(16);
            int code = HttpUtils.doGetReturningStatusCode(endpoint, Param.create("hash", hash));
            validateStatusCode(code);
            return code;
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return 500;
    }

    private void validateStatusCode(int code) {
        if (!(code >= 200 && code < 300)) {
            throw new IllegalArgumentException("Request problem code: " + code);
        }
    }

    private QueueDAO createQueueDAO() {
        return QueueDAO.create(elastixEMFs.getAsteriskEMF());
    }

    public void updateQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        createQueueDAO().updateQueueAgents(queueId, agents);
        restartAsteriskQueues();
    }
    
    public void deleteQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        createQueueDAO().deleteQueueAgents(queueId, agents);
        restartAsteriskQueues();
    }
}
