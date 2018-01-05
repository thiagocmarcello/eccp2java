package br.com.xbrain;

import br.com.xbrain.eccp2java.AgentConsole;
import br.com.xbrain.eccp2java.EccpClient;
import br.com.xbrain.eccp2java.database.CampaignContextEnum;
import br.com.xbrain.eccp2java.database.model.Campaign;
import br.com.xbrain.eccp2java.database.model.Queue;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.EccpLogoutAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.eccp2java.util.DateUtils;
import br.com.xbrain.elastix.Contact;
import br.com.xbrain.elastix.DialerAgent;
import br.com.xbrain.elastix.DialerCampaign;
import br.com.xbrain.elastix.ElastixIntegration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class AppTest {

    private static final Logger LOG = Logger.getLogger(AppTest.class.getName());

    private static final int EXTEN = 8020;
    private static final String PASSWORD = "8020";
    private static final String AGENT = "Agent/8020";
    private static final String QUEUE_ID = "9876";

    public static void __main(String[] args) throws ElastixIntegrationException {
        ElastixIntegration elastix = getElastixIntegration();
        elastix.updateQueueAgents("99", Arrays.asList(DialerAgent.create("Agent/8002", 1L),
                DialerAgent.create("Agent/8006", 2L),
                DialerAgent.create("Agent/8007", 3L)));
    }

    public static void createDialerCampaign(int id) throws ElastixIntegrationException {
        ElastixIntegration elastix = getElastixIntegration();
        List<Contact> contacts = Arrays.asList(Contact.create("8007", "1234"),
                Contact.create("8007", "234"), Contact.create("8007", "456"),
                Contact.create("8007", "342"), Contact.create("8007", "345"),
                Contact.create("8007", "342"), Contact.create("8007", "345"));

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
                        Arrays.asList(DialerAgent.create("Agent/8002", 1L),
                                DialerAgent.create("Agent/8006", 2L)))
                .build();

        Campaign createdCampaign = elastix.createCampaign(dialerCampaign);
        elastix.getCampaign(createdCampaign.getId());
        elastix.getQueue(createdCampaign.getQueue());
    }

    public static void _main(String[] args) throws ElastixIntegrationException {
        ElastixIntegration elastixIntegration = getElastixIntegration();
        Campaign campaign = elastixIntegration.getCampaign(96);
        System.out.println("campaign: " + campaign.toString());
        Queue queue = elastixIntegration.getQueue("4455");
        System.out.println("queue: " + queue.toString());
    }

    public static void main(String[] args) throws IOException, ElastixIntegrationException {
        try {
            ElastixIntegration elastixIntegration = getElastixIntegration();

            List<Contact> contacts = Arrays.asList(Contact.create("8008", "1234"),
                    Contact.create("8008", "234"), Contact.create("8008", "456"),
                    Contact.create("8007", "342"), Contact.create("8007", "345"),
                    Contact.create("8006", "342"), Contact.create("8006", "345"));

            DialerCampaign dialerCampaign = DialerCampaign.builder()
                    .identifiedBy(99)
                    .named("Campanha 99")
                    .from(DateUtils.createDate(2016, 10, 27, 0, 0, 0, 0))
                    .to(DateUtils.createDate(2016, 11, 27, 0, 0, 0, 0))
                    .startingAt("08:00")
                    .endingAt("20:30")
                    .usingContext(CampaignContextEnum.FROM_INTERNAL)
                    .showingScript("<b>Camapanha MxNx</b>")
                    .retrying(5)
                    .dialingTo(contacts)
                    .answeringWith(Arrays.asList(DialerAgent.create("Agent/8006", 1L), DialerAgent.create("Agent/8006", 2L)))
                    .build();

            Campaign campaign = elastixIntegration.createCampaign(dialerCampaign);
            System.out.println("Campaign: " + campaign.toString());

            Elastix elastix = Elastix.create("192.168.1.23", 20005, "discadora", "teste1");
            EccpClient eccp = new EccpClient(elastix);
//            eccp.addEventListener(null, (IEccpEventListener) event -> System.out.println("\tevent: " + event));

            AgentConsole console = eccp.createAgentConsole(AGENT, PASSWORD, EXTEN);
            EccpLoginAgentResponse loginResponse = console.loginAgent();
            System.out.println("Login agent: " + loginResponse);
            System.in.read();
            EccpLogoutAgentResponse logoutResponse = console.logoutAgent();
            System.out.println("Logout agent: " + logoutResponse);
            System.out.println("Agent console logout: " + console.logoutAgent());
            eccp.close();
        } catch (Exception ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("deu merda " + ex.getMessage());
        }
    }

    private static ElastixIntegration getElastixIntegration() {
        ElastixIntegration elastixIntegration = ElastixIntegration.create(
                EConfiguracaoDev.IP_BANCO.getValor()
                        + EConfiguracaoDev.PORTA_BANCO.getValor(), EConfiguracaoDev.USUARIO_BANCO.getValor(),
                EConfiguracaoDev.SENHA_BANCO.getValor());
        return elastixIntegration;
    }
}