package br.com.xbrain.elastix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class DialerAgentTest {

    public DialerAgentTest() {
    }

    @Test
    public void deveCriarOAgenteComPenalidade() {
        String agentName = "Agent/8006,9";
        DialerAgent agent = DialerAgent.create(agentName, Long.MIN_VALUE);
        assertEquals("Agent/8006", agent.getElastixAgentName());
        assertEquals(9, agent.getPenalty());
    }
    
    @Test
    public void deveCriarOAgenteSemPenalidade() {
        String agentName = "Agent/8006";
        DialerAgent agent = DialerAgent.create(agentName, Long.MIN_VALUE);
        assertEquals("Agent/8006", agent.getElastixAgentName());
        assertEquals(0, agent.getPenalty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deveNaoCriarOAgentComRamalInvalido() {
        String agentName = "Agent/80006";
        DialerAgent.create(agentName, Long.MIN_VALUE);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deveNaoCriarOAgentComPrefixoInvalido() {
        String agentName = "Agente/8006";
        DialerAgent.create(agentName, Long.MIN_VALUE);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deveNaoCriarOAgentVazio() {
        String agentName = "";
        DialerAgent.create(agentName, Long.MIN_VALUE);
    }
}