package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.database.CampaignContextEnum;
import br.com.xbrain.eccp2java.entity.xml.ElastixLoginData;
import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.DateUtils;
import br.com.xbrain.elastix.Contact;
import br.com.xbrain.elastix.DialerAgent;
import br.com.xbrain.elastix.DialerCampaign;
import br.com.xbrain.elastix.ElastixIntegration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final int DEFAULT_CAMPAING_TEST_ID = 99;
    private static final int DEFAULT_ELASTIX_TEST_PORT = 20005;
    private static final int DEFAULT_AGENT_CONSOLE_TEST_EXTENSION = 7006;

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public static final String ELASTIX_HOST_IP = "192.168.1.22";

    public static void main(String[] args) throws ElastixIntegrationException, EccpException, IOException {
        createDialerCampaign(DEFAULT_CAMPAING_TEST_ID);
        ElastixLoginData elastixLoginData = ElastixLoginData.create(
                ELASTIX_HOST_IP,
                DEFAULT_ELASTIX_TEST_PORT,
                "discadora",
                "teste");
        EccpClient eccpClient = new EccpClient(elastixLoginData);
        eccpClient.connect();
        AgentConsole ac = eccpClient.createAgentConsole(
                "Agent/7006",
                "7006",
                DEFAULT_AGENT_CONSOLE_TEST_EXTENSION);
        ac.addEventListener(new EventListener());
        ac.loginAgent();
        waitUserToPressAnyKey();
        ac.logoutAgent();
        eccpClient.close();
    }

    private static void waitUserToPressAnyKey() throws IOException {
        int read = System.in.read();
        System.out.print("Typed '" + read + "'");
    }

    @SuppressWarnings({"PMD.MagicNumber", "checkstyle:MagicNumber", "checkstyle:MethodLength"})
    private static void createDialerCampaign(int id) throws ElastixIntegrationException {
        ElastixIntegration elastix = getElastixIntegration();

        List<Contact> contacts = Arrays.asList(Contact.create("43991036116", "1234"),
                Contact.create("43991036116", "234"),
                Contact.create("43991036116", "456"),
                Contact.create("43991036116", "342"),
                Contact.create("43991036116", "345"),
                Contact.create("43991036116", "342"),
                Contact.create("43991036116", "345"));

        DialerCampaign dialerCampaign = DialerCampaign.builder()
                .identifiedBy(id)
                .named("Campanha " + id)
                .from(DateUtils.createDate(2018, 1, 1, 0, 0, 0, 0))
                .to(DateUtils.createDate(2018, 1, 31, 0, 0, 0, 0))
                .startingAt("08:00")
                .endingAt("20:30")
                .usingContext(CampaignContextEnum.FROM_INTERNAL)
                .showingScript("<b>Camapanha Willie</b>")
                .retrying(5)
                .dialingTo(contacts)
                .answeringWith(
                        Arrays.asList(
                                DialerAgent.create("Agent/7006", 1L),
                                DialerAgent.create("Agent/7001", 2L),
                                DialerAgent.create("Agent/7002", 3L)))
                .build();

        elastix.createCampaign(dialerCampaign);
    }

    private static ElastixIntegration getElastixIntegration() {
        return ElastixIntegration.create(
                EConfiguracaoDev.IP_BANCO.getValor()
                        + EConfiguracaoDev.PORTA_BANCO.getValor(), EConfiguracaoDev.USUARIO_BANCO.getValor(),
                EConfiguracaoDev.SENHA_BANCO.getValor());
    }
}

@SuppressWarnings("all")
class EventListener implements IEccpEventListener {

    @Override
    public void onEvent(IEccpEvent event) {
        System.out.println(">>> Event: " + event.toString());
    }

}