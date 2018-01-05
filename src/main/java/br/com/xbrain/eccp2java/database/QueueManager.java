package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEmfs;
import br.com.xbrain.eccp2java.database.model.Ipv4;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.model.RestartQueueEndpoint;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.HttpUtils;
import br.com.xbrain.eccp2java.util.HttpUtils.Param;
import br.com.xbrain.elastix.DialerAgent;

import javax.validation.*;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

public class QueueManager {

    public static final String DIGEST_ALGORITHM = "SHA1";
    public static final int HTTP_200_STATUS = 200;
    public static final int HTTP_300_STATUS = 300;

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private RestartQueueEndpoint endpoint;

    public static QueueManager create(ElastixEmfs elastixEmfs, Ipv4 reloadQueueIp, int reloadQueuePort)
            throws ElastixIntegrationException {

        try {
            QueueManager queueManager = new QueueManager();
            queueManager.elastixEmfs = elastixEmfs;
            queueManager.endpoint = RestartQueueEndpoint.create(
                    RestartQueueEndpoint.Protocol.HTTP,
                    reloadQueueIp,
                    reloadQueuePort);

            return queueManager;
        } catch (URISyntaxException ex) {
            throw new ElastixIntegrationException("Erro ao criar QueueManager", ex);
        }
    }

    private ElastixEmfs elastixEmfs;

    private QueueManager() {}

    public Queue save(Queue queue) throws ElastixIntegrationException {
        try {
            validate(queue);
            validatorFactory.getValidator().validate(queue.getQueueConfig());
            QueueDao queueDao = createQueueDao();
            Queue savedQueue = queueDao.create(queue);
            restartAsteriskQueues();
            return savedQueue;
        } catch (ValidationException | IllegalArgumentException ex) {
            throw new ElastixIntegrationException("Não foi possível salvar a fila: " + ex.getMessage(), ex);
        }
    }

    public Queue find(String queueId) throws ElastixIntegrationException {
        return createQueueDao().find(queueId);
    }

    public void remove(Queue queue) throws ElastixIntegrationException {
        createQueueDao().remove(queue);
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

    @SuppressWarnings({"PMD.MagicNumber", "checkstyle:MagicNumber"})
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
        if (!(code >= HTTP_200_STATUS && code < HTTP_300_STATUS)) {
            throw new ElastixIntegrationException("Não foi possível reiniciar as filas. Código: " + code, null);
        }
    }

    private QueueDao createQueueDao() {
        return QueueDao.create(elastixEmfs.getAsteriskEmf());
    }

    public void updateQueueAgents(String queueId, List<DialerAgent> agents) throws ElastixIntegrationException {
        createQueueDao().updateQueueAgents(queueId, agents);
        restartAsteriskQueues();
    }

    public void deleteQueueAgents(String queueId) throws ElastixIntegrationException {
        createQueueDao().deleteQueueAgents(queueId);
        restartAsteriskQueues();
    }
}
