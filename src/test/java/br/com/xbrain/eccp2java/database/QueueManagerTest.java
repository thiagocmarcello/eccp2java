package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEmfs;
import br.com.xbrain.eccp2java.database.model.*;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class QueueManagerTest {

    private static final Logger LOG = Logger.getLogger(QueueManagerTest.class.getName());

    public QueueManagerTest() {
    }

    private ElastixEmfs elastixEmfs;

    @Before
    public void setUp() {
        elastixEmfs = Mockito.mock(ElastixEmfs.class);
        Mockito.when(elastixEmfs.getUrl()).then((InvocationOnMock invocation) -> "http://localhost:3306");
    }

    @Test
    public void testSaveInvalidQueue() throws ElastixIntegrationException {
        QueueManager queueManager = QueueManager.create(
                elastixEmfs,
                Ipv4.of(EConfiguracaoDev.IP_QUEUES_RELOAD.getValor()),
                Integer.parseInt(EConfiguracaoDev.PORTA_QUEUES_RELOAD.getValor()));

        try {
            queueManager.save(createInvalidQueue());
        } catch (ElastixIntegrationException ex) {
            System.out.println(ex.getMessage());
            assertTrue(true);
            return;
        }
        Assert.fail("Se chegou aqui é porque o teste acima falhou.");
    }

    @Test
    public void testSaveInvalidQueueConfig() throws ElastixIntegrationException {
        QueueManager queueManager = QueueManager.create(
                elastixEmfs,
                Ipv4.of(EConfiguracaoDev.IP_QUEUES_RELOAD.getValor()),
                Integer.parseInt(EConfiguracaoDev.PORTA_QUEUES_RELOAD.getValor()));

        try {
            queueManager.save(createInvalidQueueConfig());
        } catch (ElastixIntegrationException ex) {
            assertTrue(true);
            return;
        }
        Assert.fail("Se chegou aqui é porque o teste acima falhou.");
    }

    @Test
    public void testSave() {
        try {
            QueueManager queueManager = Mockito.mock(QueueManager.class);
            Queue queue = createQueue();
            queueManager.save(queue);
            Mockito.verify(queueManager).save(queue);
        } catch (ElastixIntegrationException ex) {
            fail(ex.getMessage());
        }
    }

    public static Queue createInvalidQueue() {
        // A queue vazia é inválida para ser salva
        return new Queue();
    }

    public static Queue createInvalidQueueConfig() {
        Queue queue = createQueue();
        queue.getQueueConfig().setExtension(null);
        return queue;
    }

    public static Queue createQueue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String id = "1001";
        QueueConfig queuesConfig = new QueueConfig();
        queuesConfig.setExtension(id);
        queuesConfig.setDescr("Fila 1001 " + sdf.format(new Date()));
        queuesConfig.setIvrId("none");
        queuesConfig.setCwignore(false);
        queuesConfig.setAgentannounceId(0);
        queuesConfig.setJoinannounceId(0);
        queuesConfig.setQueuewait(false);
        queuesConfig.setUseQueueContext(false);
        queuesConfig.setTogglehint(false);
        queuesConfig.setQnoanswer(false);
        queuesConfig.setCallconfirm(false);
        queuesConfig.setMonitorHeard(0);
        queuesConfig.setMonitorSpoken(0);
        queuesConfig.setCallbackId("none");
        List<QueueDetail> queuesDetailList = createQueueDetailsList(id);
        return new Queue(queuesConfig, queuesDetailList);
    }

    private static List<QueueDetail> createQueueDetailsList(String id) {
        List<QueueDetail> queuesDetailList = new ArrayList<>();
        QueueDetail queuesDetails;
        QueuesDetailPk queuesDetailsPK;
        queuesDetailsPK = new QueuesDetailPk(id, "announce-frequency", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "announce-holdtime", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "announce-position", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "answered_elsewhere", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "autofill", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "autopause", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "autopausebusy", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "autopausedelay", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "autopauseunavail", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "cron_schedule", "never");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "eventmemberstatus", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "eventwhencalled", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "joinempty", "yes");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "leavewhenempty", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "maxlen", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "memberdelay", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "monitor-format", "");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "monitor-join", "yes");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "penaltymemberslimit", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "periodic-announce-frequency", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "queue-callswaiting", "silence/1");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "queue-thankyou", "");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "queue-thereare", "silence/1");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "queue-youarenext", "silence/1");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "reportholdtime", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "retry", "5");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "ringinuse", "yes");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "servicelevel", "60");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "skip_joinannounce", "");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "strategy", "ringall");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "timeout", "15");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "timeoutpriority", "app");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "timeoutrestart", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "weight", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPk(id, "wrapuptime", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        // '8080', 'member', 'Agent/8007,0', '0'
        queuesDetailsPK = new QueuesDetailPk(id, "member", "Agent/8002,0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        return queuesDetailList;
    }
}
