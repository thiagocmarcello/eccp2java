package br.com.xbrain.eccp2java.database.model;

import br.com.xbrain.elastix.DialerAgent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@ToString
@EqualsAndHashCode(of = {"queueConfig", "queueDetails"})
public class Queue implements Serializable {

    @NotNull
    @Getter
    @Setter
    private QueueConfig queueConfig;

    @NotNull
    @Size(min = 35) // TODO 35 esta hardcoded porque será validado posteriormente
    @Getter
    @Setter
    private List<QueueDetail> queueDetails;
    
    @Transient
    private List<DialerAgent> dialerAgents;

    public Queue() {
    }

    public Queue(QueueConfig queuesConfig, List<QueueDetail> queuesDetails) {
        this.queueConfig = queuesConfig;
        this.queueDetails = queuesDetails;
    }
    
    public List<DialerAgent> getDialerAgents() {
        if(dialerAgents == null) {
            dialerAgents = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(queueDetails)) {
                dialerAgents = createDialerAgents(queueDetails);
            }
        }
        return dialerAgents;
    }

    private List<DialerAgent> createDialerAgents(List<QueueDetail> queueDetails) {
        List<DialerAgent> agents = new ArrayList<>();
        for (QueueDetail queueDetail : queueDetails) {
            if ("member".equals(queueDetail.getQueuesDetailsPK().getKeyword())) {
                agents.add(queueDetail2Agent(queueDetail));
            }
        }
        return agents;
    }

    private DialerAgent queueDetail2Agent(QueueDetail queueDetail) {
        return DialerAgent.create(queueDetail.getQueuesDetailsPK().getData(), 
                Long.valueOf(queueDetail.getQueuesDetailsPK().getId()));
    }
}
