package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentRequest;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.EccpLogoutAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

public class AgentConsoleTest {

    @Test
    public void shouldDisconnectAgentConsole() throws Exception {
        EccpClient client = Mockito.mock(EccpClient.class);
        given(client.isConnected()).willReturn(true);
        AgentConsole agentConsole = new AgentConsole(client, "x", "x", 1, "x");
        assertThat("Deveria estar conectado", agentConsole.isConnected(), is(true));
        agentConsole.disconnect();
        assertThat("Deveria estar desconectado", agentConsole.isConnected(), is(false));
    }

    @Test
    public void shouldSend() throws Exception {
        EccpClient client = Mockito.mock(EccpClient.class);
        given(client.isConnected()).willReturn(true);
        given(client.send(Mockito.anyObject())).willReturn(Mockito.mock(IEccpResponse.class));
        AgentConsole agentConsole = new AgentConsole(client, "x", "x", 1, "x");
        agentConsole.send(EccpLoginAgentRequest.create("a", "b","c", 123));
        verify(client.send(Mockito.anyObject()));
    }

    @Test
    public void loginAgent() throws Exception {
        EccpClient client = Mockito.mock(EccpClient.class);
        given(client.isConnected()).willReturn(true);
        given(client.send(anyObject()))
                .willReturn(new EccpLoginAgentResponse());
        AgentConsole agentConsole = new AgentConsole(client, "x", "x", 1, "x");
        assertThat("Deveria ter retornado um loginAgentResponse",
                agentConsole.loginAgent(),
                CoreMatchers.instanceOf(EccpLoginAgentResponse.class));
    }

    @Test
    public void logoutAgent() throws Exception {
        EccpClient client = Mockito.mock(EccpClient.class);
        given(client.isConnected()).willReturn(true);
        given(client.send(anyObject()))
                .willReturn(new EccpLogoutAgentResponse());
        AgentConsole agentConsole = new AgentConsole(client, "x", "x", 1, "x");
        assertThat("Deveria ter retornado um logoutAgentResponse",
                agentConsole.logoutAgent(),
                CoreMatchers.instanceOf(EccpLogoutAgentResponse.class));
    }

    @Test
    public void fireEvent() throws Exception {
    }

    @Test
    public void addEventListener() throws Exception {
    }

}