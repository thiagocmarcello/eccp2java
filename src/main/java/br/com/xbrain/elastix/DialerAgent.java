package br.com.xbrain.elastix;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;


/**
 *
 * @author xbrain (joaomassan@xbrain.com.br)
 */
@Data
@ToString
@EqualsAndHashCode
public class DialerAgent implements Serializable {

    @Setter(AccessLevel.NONE)
    private String elastixAgentName;
    
    private Long localId;
    
    private int penalty = 0;
    
    private DialerAgent() {}
    
    public static final DialerAgent create(String elastixAgentName, Long localId) {
        if(!elastixAgentName.matches("Agent\\/[0-9]{4}(,\\d)?")) {
            throw new IllegalArgumentException("Nome do agente inválido: '" + elastixAgentName + "'. Informe um nome como Agent/1001");
        } else if(localId == null) {
            throw new IllegalArgumentException("o id local não pode ser nulo");
        }

        DialerAgent agent = new DialerAgent();

        String[] parts = elastixAgentName.split(",");
        agent.elastixAgentName = parts[0];
        if(parts.length == 2) {
            agent.penalty = Integer.valueOf(parts[1]);
        } 
        
        agent.localId = localId;
        return agent;
    }
    
    public String getElaxtixNameWithPenalty() {
        return elastixAgentName + "," + penalty;
    }

}
