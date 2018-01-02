package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.exception.EccpException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class AgentConsoleTest {

    @Test
    public void deveConectarAgentConsoleELogar() throws Exception {
        EccpClient client = new EccpClient(
                Elastix.create("192.168.1.22", 20005, "discadora", "teste"));
        client.connect();
        AgentConsole console = client.createAgentConsole("Agent/7006", "7006", 7006);
        EccpLoginAgentResponse loginResponse = console.loginAgent();
        assertThat("Deveria estar fazendo login.",
                loginResponse.getStatus(),
                containsString("logging"));
        client.close();
    }
}