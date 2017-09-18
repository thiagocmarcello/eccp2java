package br.com.xbrain.elastix;

import br.com.xbrain.eccp2java.database.CampaignContextEnum;
import br.com.xbrain.eccp2java.database.model.Call;
import br.com.xbrain.eccp2java.database.model.CallAttribute;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.database.model.Form;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.database.model.QueueConfig;
import br.com.xbrain.eccp2java.database.model.QueueDetail;
import br.com.xbrain.eccp2java.database.model.QueuesDetailPK;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
@ToString
@EqualsAndHashCode(of = {"campaign", "queue"})
public class DialerCampaign implements Serializable {
    
    public static DialerCampaign create(Campaign campaign, Queue queue) {
        DialerCampaign dialerCampaign = new DialerCampaign(campaign, queue);
        return dialerCampaign;
    }

    @Getter
    private final Campaign campaign;

    @Getter
    private final Queue queue;

    private DialerCampaign(Campaign campaign, Queue queue) {
        this.campaign = campaign;
        this.queue = queue;
    }

    public static final DialerCampaignBuilder builder() {
        return new DialerCampaignBuilder();
    }

    public static final class DialerCampaignBuilder {

        private static final Logger LOG = Logger.getLogger(DialerCampaignBuilder.class.getName());

        private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("kk:mm");

        private int id;

        private String campaignName;
        private String script = "";
        private String context = CampaignContextEnum.FROM_INTERNAL.getValue();
        private Date from, to, startingAt, endingAt;
        private Integer retrying;

        private List<Contact> contacts;
        private List<DialerAgent> agents;

        public DialerCampaignBuilder identifiedBy(int id) {
            this.id = id;
            return this;
        }

        public DialerCampaignBuilder named(String campaignName) {
            if (campaignName == null) {
                throw new IllegalArgumentException("O nome da campanha não pode ser nulo");
            }
            this.campaignName = campaignName;
            return this;
        }

        public DialerCampaignBuilder from(Date from) {
            if (from == null) {
                throw new IllegalArgumentException("A data de início não pode ser nula");
            }
            this.from = from;
            return this;
        }

        public DialerCampaignBuilder to(Date to) {
            if (to == null) {
                throw new IllegalArgumentException("A data de término não pode ser nula");
            }

            if (from.after(to)) {
                throw new IllegalArgumentException("A data de término deve ser maior que a data de início");
            }

            this.to = to;
            return this;
        }

        public DialerCampaignBuilder startingAt(String startingTime) {
            try {
                startingAt = HOUR_FORMAT.parse(startingTime);
                return this;
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Hora de início inválida " + startingTime, ex);
            }
        }

        public DialerCampaignBuilder endingAt(String endingTime) {
            try {
                endingAt = HOUR_FORMAT.parse(endingTime);
                if (endingAt.before(startingAt)) {
                    throw new IllegalArgumentException("A hora final deve ser maior que a hora inicial");
                }
                return this;
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Hora de término inválida : " + endingTime, ex);
            }
        }

        public DialerCampaignBuilder usingContext(CampaignContextEnum context) {
            if (context == null) {
                this.context = CampaignContextEnum.FROM_INTERNAL.getValue();
            }
            this.context = context.getValue();
            return this;
        }

        public DialerCampaignBuilder showingScript(String script) {
            this.script = script;
            return this;
        }

        public DialerCampaignBuilder retrying(int retrying) {
            if (retrying < 0) {
                throw new IllegalArgumentException("A quantidade de tentativas deve ser positiva");
            }
            this.retrying = retrying;
            return this;
        }

        public DialerCampaignBuilder dialingTo(List<Contact> contacts) {
            if (CollectionUtils.isEmpty(contacts)) {
                throw new IllegalArgumentException("A campanha deve conter pelo menos 1 contato");
            }

            this.contacts = Collections.unmodifiableList(contacts);
            return this;
        }

        public DialerCampaignBuilder answeringWith(List<DialerAgent> agents) {
            if (CollectionUtils.isEmpty(agents)) {
                throw new IllegalArgumentException("A campanha deve conter pelo menos 1 agente");
            }
            this.agents = Collections.unmodifiableList(agents);
            return this;
        }

        public DialerCampaign build() {
            try {
                Field[] fields = this.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.get(this) == null) {
                        throw new IllegalStateException(field.getName() + " não pode ser nulo");
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                throw new IllegalStateException(ex.getMessage());
            }

            return new DialerCampaign(createCampaign(), createQueue());
        }

        private Campaign createCampaign() {
            Campaign campaign = Campaign.builder().id(null)
                    .name(campaignName)
                    .datetimeInit(from)
                    .datetimeEnd(to)
                    .daytimeInit(startingAt)
                    .daytimeEnd(endingAt)
                    .retries(retrying)
                    .context(context)
                    .queue(String.valueOf(id))
                    .script(script)
                    .maxCanales(10)         // TODO remover hard code
                    .estatus("A").build();  // TODO criar enum para situação

            // TODO verificar como vai ficar a situação dos formulários também
            Form form = new Form(1);
            form.setCampaignCollection(Arrays.asList(campaign));
            campaign.setFormCollection(Arrays.asList(form));

            for (Contact contact : contacts) {
                createCall(campaign, contact.getPhone(), contact.getHpId(), "", "");
            }

            return campaign;
        }

        private static void createCall(Campaign campaign, String number, String idHP, String name, String obs) {
            Call call = new Call();
            call.setCampaign(campaign);
            call.setPhone(number);

            campaign.getCalls().add(call);

            CallAttribute callAttribute1 = new CallAttribute("id_hp", idHP, 1, call);
            CallAttribute callAttribute2 = new CallAttribute("nome", name, 2, call);
            CallAttribute callAttribute3 = new CallAttribute("obs", obs, 3, call);

            call.getAttributes().add(callAttribute1);
            call.getAttributes().add(callAttribute2);
            call.getAttributes().add(callAttribute3);
        }

        private Queue createQueue() {
            Queue queue = new Queue();
            queue.setQueueConfig(createQueueConfig(String.valueOf(id)));
            queue.setQueueDetails(createQueueDetailsList(String.valueOf(id)));
            return queue;
        }

        public QueueConfig createQueueConfig(String id) {
            QueueConfig queuesConfig = new QueueConfig();
            queuesConfig.setExtension(id);
            queuesConfig.setDescr("Fila " + id);
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
            return queuesConfig;
        }

        // TODO melhorar o design dessa bagaça
        private List<QueueDetail> createQueueDetailsList(String id) {
            List<QueueDetail> queueDetails = new ArrayList<>();
            queueDetails.add(createQueueDetail(id, "announce-frequency", "0"));
            queueDetails.add(createQueueDetail(id, "announce-holdtime", "no"));
            queueDetails.add(createQueueDetail(id, "announce-position", "no"));
            queueDetails.add(createQueueDetail(id, "answered_elsewhere", "0"));
            queueDetails.add(createQueueDetail(id, "autofill", "no"));
            queueDetails.add(createQueueDetail(id, "autopause", "no"));
            queueDetails.add(createQueueDetail(id, "autopausebusy", "no"));
            queueDetails.add(createQueueDetail(id, "autopausedelay", "0"));
            queueDetails.add(createQueueDetail(id, "autopauseunavail", "no"));
            queueDetails.add(createQueueDetail(id, "cron_schedule", "never"));
            queueDetails.add(createQueueDetail(id, "eventmemberstatus", "no"));
            queueDetails.add(createQueueDetail(id, "eventwhencalled", "no"));
            queueDetails.add(createQueueDetail(id, "joinempty", "yes"));
            queueDetails.add(createQueueDetail(id, "leavewhenempty", "no"));
            queueDetails.add(createQueueDetail(id, "maxlen", "0"));
            queueDetails.add(createQueueDetail(id, "memberdelay", "0"));
            queueDetails.add(createQueueDetail(id, "monitor-format", ""));
            queueDetails.add(createQueueDetail(id, "monitor-join", "yes"));
            queueDetails.add(createQueueDetail(id, "penaltymemberslimit", "0"));
            queueDetails.add(createQueueDetail(id, "periodic-announce-frequency", "0"));
            queueDetails.add(createQueueDetail(id, "queue-callswaiting", "silence/1"));
            queueDetails.add(createQueueDetail(id, "queue-thankyou", ""));
            queueDetails.add(createQueueDetail(id, "queue-thereare", "silence/1"));
            queueDetails.add(createQueueDetail(id, "queue-youarenext", "silence/1"));
            queueDetails.add(createQueueDetail(id, "reportholdtime", "no"));
            queueDetails.add(createQueueDetail(id, "retry", "5"));
            queueDetails.add(createQueueDetail(id, "ringinuse", "yes"));
            queueDetails.add(createQueueDetail(id, "servicelevel", "60"));
            queueDetails.add(createQueueDetail(id, "skip_joinannounce", ""));
            //queueDetails.add(createQueueDetail(id, "strategy", "ringall"));
            queueDetails.add(createQueueDetail(id, "strategy", "leastrecent"));
            queueDetails.add(createQueueDetail(id, "timeout", "15"));
            queueDetails.add(createQueueDetail(id, "timeoutpriority", "app"));
            queueDetails.add(createQueueDetail(id, "timeoutrestart", "no"));
            queueDetails.add(createQueueDetail(id, "weight", "0"));
            queueDetails.add(createQueueDetail(id, "wrapuptime", "0"));

            for (DialerAgent agent : agents) {
                queueDetails.add(createQueueDetail(id, "member", agent.getElaxtixNameWithPenalty()));
            }

            return queueDetails;
        }

        private QueueDetail createQueueDetail(String queueId, String keyword, String value) {
            QueuesDetailPK queuesDetailsPK = new QueuesDetailPK(queueId, keyword, value);
            QueueDetail queueDetail = new QueueDetail();
            queueDetail.setQueuesDetailsPK(queuesDetailsPK);
            return queueDetail;
        }
    }
}