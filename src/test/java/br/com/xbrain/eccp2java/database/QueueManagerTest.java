package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.model.QueueDetail;
import br.com.xbrain.eccp2java.database.model.QueuesDetailPK;
import br.com.xbrain.eccp2java.enums.EConfiguracao;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class QueueManagerTest {

    private static final Logger LOG = Logger.getLogger(QueueManagerTest.class.getName());

    public QueueManagerTest() {
    }

    private ElastixEMFs elastixEMFs;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        elastixEMFs = Mockito.mock(ElastixEMFs.class);
        Mockito.when(elastixEMFs.getUrl()).then((InvocationOnMock invocation) -> "http://localhost:3306");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSaveInvalidQueue() {
        QueueManager queueManager = QueueManager.create(elastixEMFs,
                EConfiguracao.IP_QUEUES_RELOAD + ":" + EConfiguracao.PORTA_QUEUES_RELOAD);

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
    public void testSaveInvalidQueueConfig() {
        QueueManager queueManager = QueueManager.create(elastixEMFs,
                EConfiguracao.IP_QUEUES_RELOAD + ":" + EConfiguracao.PORTA_QUEUES_RELOAD);

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
        Queue queue = new Queue(queuesConfig, queuesDetailList);
        return queue;
    }

    private static List<QueueDetail> createQueueDetailsList(String id) {
        List<QueueDetail> queuesDetailList = new ArrayList<>();
        QueueDetail queuesDetails;
        QueuesDetailPK queuesDetailsPK;
        queuesDetailsPK = new QueuesDetailPK(id, "announce-frequency", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "announce-holdtime", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "announce-position", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "answered_elsewhere", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "autofill", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "autopause", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "autopausebusy", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "autopausedelay", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "autopauseunavail", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "cron_schedule", "never");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "eventmemberstatus", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "eventwhencalled", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "joinempty", "yes");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "leavewhenempty", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "maxlen", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "memberdelay", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "monitor-format", "");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "monitor-join", "yes");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "penaltymemberslimit", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "periodic-announce-frequency", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "queue-callswaiting", "silence/1");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "queue-thankyou", "");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "queue-thereare", "silence/1");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "queue-youarenext", "silence/1");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "reportholdtime", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "retry", "5");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "ringinuse", "yes");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "servicelevel", "60");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "skip_joinannounce", "");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "strategy", "ringall");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "timeout", "15");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "timeoutpriority", "app");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "timeoutrestart", "no");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "weight", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        queuesDetailsPK = new QueuesDetailPK(id, "wrapuptime", "0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        // '8080', 'member', 'Agent/8007,0', '0'
        queuesDetailsPK = new QueuesDetailPK(id, "member", "Agent/8002,0");
        queuesDetails = new QueueDetail(queuesDetailsPK, 0);
        queuesDetailList.add(queuesDetails);

        return queuesDetailList;
    }
}
