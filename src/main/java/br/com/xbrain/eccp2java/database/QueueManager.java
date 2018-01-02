package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.model.Ipv4;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.database.model.RestartQueueEndpoint;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.HttpUtils;
import br.com.xbrain.eccp2java.util.HttpUtils.Param;
import br.com.xbrain.elastix.DialerAgent;
import java.math.BigInteger;
import java.net.URISyntaxException;
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

public class QueueManager {

    private static final Logger LOG = Logger.getLogger(QueueManager.class.getName());

    public static final String DIGEST_ALGORITHM = "SHA1";

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private RestartQueueEndpoint endpoint;

    public static QueueManager create(ElastixEMFs elastixEMFs, Ipv4 reloadQueueIp, int reloadQueuePort)
            throws ElastixIntegrationException {

        try {
            QueueManager queueManager = new QueueManager();
            queueManager.elastixEMFs = elastixEMFs;
            queueManager.endpoint = RestartQueueEndpoint.create(
                    RestartQueueEndpoint.Protocol.HTTP,
                    reloadQueueIp,
                    reloadQueuePort);

            return queueManager;
        } catch (URISyntaxException ex) {
            throw new ElastixIntegrationException("Erro ao criar QueueManager", ex);
        }
    }

    private ElastixEMFs elastixEMFs;

    private QueueManager() {}

    public Queue save(Queue queue) throws ElastixIntegrationException {
        try {
            validate(queue);
            validatorFactory.getValidator().validate(queue.getQueueConfig());
            QueueDAO queueDAO = createQueueDAO();
            Queue savedQueue = queueDAO.create(queue);
            restartAsteriskQueues();
            return savedQueue;
        } catch (ValidationException | IllegalArgumentException ex) {
            throw new ElastixIntegrationException("Não foi possível salvar a fila: " + ex.getMessage(), ex);
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

    public void restartAsteriskQueues() throws ElastixIntegrationException {
        try {
            byte[] hashArray = MessageDigest.getInstance(DIGEST_ALGORITHM).digest("xbrain".getBytes());
            String hash = new BigInteger(1, hashArray).toString(16);
            int code = HttpUtils.doGetReturningStatusCode(endpoint.toString(), Param.create("hash", hash));
            validateStatusCode(code);
        } catch (NoSuchAlgorithmException ex) {
            throw new ElastixIntegrationException("Não foi possível reiniciar as filas.", ex);
        }
    }

    private void validateStatusCode(int code) throws ElastixIntegrationException {
        if (!(code >= 200 && code < 300)) {
            throw new ElastixIntegrationException("Não foi possível reiniciar as filas. Código: " + code, null);
        }
    }

    private QueueDAO createQueueDAO() {
        return QueueDAO.create(elastixEMFs.getAsteriskEMF());
    }

    public void updateQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        createQueueDAO().updateQueueAgents(queueId, agents);
        restartAsteriskQueues();
    }

    public void deleteQueueAgents(String queueId) throws ElastixIntegrationException {
        createQueueDAO().deleteQueueAgents(queueId);
        restartAsteriskQueues();
    }
}
