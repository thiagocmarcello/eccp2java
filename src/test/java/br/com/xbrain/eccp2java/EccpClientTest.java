package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.exception.EccpException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

public class EccpClientTest {
    
    private EccpClient eccpClient;
    private Elastix elastix;

    @Before
    public void configureEccp() throws EccpException {
        elastix = Mockito.mock(Elastix.class);
        eccpClient = Mockito.mock(EccpClient.class);
        willDoNothing().given(eccpClient).connect();
        given(eccpClient.createAgentConsole("Agent/8020", "123456", 8020))
                .willReturn(
                        new AgentConsole(
                                eccpClient,
                                "Agent/8020",
                                "123456",
                                8020,
                                "AppCookieFake"));
    }

    @Test
    public void criarEccpClientEAgentConsole() throws EccpException {
        eccpClient.connect();
        AgentConsole agentConsole = eccpClient.createAgentConsole(
                "Agent/8020", "123456", 8020);
    }

}