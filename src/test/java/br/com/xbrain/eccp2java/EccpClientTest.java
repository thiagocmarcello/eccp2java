package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.ElastixLoginData;
import br.com.xbrain.eccp2java.exception.EccpException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class EccpClientTest {

    private static final int DEFAULT_ELASTIX_TEST_PORT = 20005;
    private static final int DEFAULT_AGENT_CONSOLE_TEST_EXTENSION = 7006;

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public static final String ELASTIX_HOST_IP = "192.168.1.22";

    private EccpClient client;

    @Before
    public void createEccpClient() {
        client = new EccpClient(ElastixLoginData.create(
                ELASTIX_HOST_IP,
                DEFAULT_ELASTIX_TEST_PORT,
                "discadora",
                "teste"));
    }

    @After
    public void destroyEccpClient() throws IOException {
        if(client != null && client.isConnected()) {
            client.close();
        }
    }

    @Test
    public void shouldConnectAndDisconnect() throws Exception {
        assertThat("Não deveria estar conectado ainda", client.isConnected(), is(false));
        client.connect();
        assertThat("Deveria estar conectado", client.isConnected(), is(true));
        client.close();
        assertThat("Deveria ter desconectado", client.isConnected(), is(false));
    }

    @Test
    public void shouldCreateAndRemoveAnAgentConsole() throws Exception {
        client.connect();
        AgentConsole ac = client.createAgentConsole(
                "Agent/7006",
                "7006",
                DEFAULT_AGENT_CONSOLE_TEST_EXTENSION);

        assertThat("Deveria ter criado um AgentConsole", ac, notNullValue(null));
        assertThat("O agente console deveria estar connectado", ac.isConnected(), is(true));
        client.removeAgentConsole(ac);
        assertThat("O agente console deveria ter sido desconectado", ac.isConnected(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfNotConnected() throws EccpException {
        client.send(null);
    }
};