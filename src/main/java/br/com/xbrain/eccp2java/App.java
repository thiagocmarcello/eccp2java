package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.exception.EccpException;
import br.com.xbrain.eccp2java.entity.xml.Elastix;
import br.com.xbrain.eccp2java.entity.xml.EccpLoginAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.EccpLogoutAgentResponse;
import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import br.com.xbrain.elastix.DialerCampaign;
import br.com.xbrain.elastix.ElastixIntegration;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.CollectionUtils;

// FIXME após mover tudo para os testes, apagar esta classe
public class App {

    static {
        System.out.println("teste");
    }

    public static App app = new App();

    public App() {
        System.out.println("Test1");
    }

    public static void __main(String[] args) throws ElastixIntegrationException {
        ElastixIntegration elastix = ElastixIntegration.create(
                EConfiguracaoDev.IP_BANCO.getValor() + EConfiguracaoDev.PORTA_BANCO.getValor(),
                EConfiguracaoDev.USUARIO_BANCO.getValor(),
                EConfiguracaoDev.SENHA_BANCO.getValor());
        
        List<DialerCampaign> dialerCampaigns = elastix.getActiveDialerCampaigns();
        if (CollectionUtils.isNotEmpty(dialerCampaigns)) {
            System.out.println("Quantidade de campanhas ativas: " + dialerCampaigns.size());
            for (DialerCampaign dialerCampaign : dialerCampaigns) {
                System.out.println("\tCampanha: " + dialerCampaign.getCampaign());
                System.out.println("\tFila: " + dialerCampaign.getQueue());
                System.out.println("\tQtde agentes: " + CollectionUtils.size(dialerCampaign.getQueue().getDialerAgents()));
                System.out.println("\tAgentes: " + dialerCampaign.getQueue().getDialerAgents());
            }
        }
    }

    public static void _main(String[] args) throws IOException, ElastixIntegrationException {

        try {
            Elastix elastix = Elastix.create("192.168.1.22", 20005, "discadora", "teste");
            EccpClient eccp = new EccpClient(elastix);
            eccp.addEventListener(null, new IEccpEventListener<IEccpEvent>() {

                @Override
                public void onEvent(IEccpEvent event) {
                    System.out.println("\tevent: " + event);
                }
            });

            AgentConsole console = eccp.createAgentConsole("Agent/8002", "8020", 8020);
            EccpLoginAgentResponse loginResponse = console.loginAgent();

            System.out.println("Login agent: " + loginResponse);
            pause("Pressione qualquer tecla para logoff");
            EccpLogoutAgentResponse logoutResponse = console.logoutAgent();
            System.out.println("Logout agent: " + logoutResponse);
            System.out.println("Agent console logout: " + console.logoutAgent());

            pause("Pressione qualquer tecla para criar a campanha");

            //AppTest.createDialerCampaign(25);

            pause("Pressione qualquer tecla para fazer o login novamente");

            console = eccp.createAgentConsole("Agent/8020", "8020", 8020);
            loginResponse = console.loginAgent();
            pause("Pressione qualquer tecla para encerrar");
            console.logoutAgent();
            console.logoutAgent();
            eccp.close();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pause(String text) {
        System.out.println("\n" + text + "...");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
