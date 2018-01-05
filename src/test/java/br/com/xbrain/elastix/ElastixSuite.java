package br.com.xbrain.elastix;

import br.com.xbrain.eccp2java.enums.EConfiguracaoDev;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 *
 * @author joaomassan@xbrain.com.br
 */
public class ElastixSuite {
    
    public static final int EXTEN = 8002;
    public static final String PASSWORD = "8002";
    public static final String AGENT = "Agent/8002";
    public static final String QUEUE_ID = "7412";

    @Test
    public void deveTerUmPontoDeEntradaPorOndeSeraoChamadasAsRotinasDoElastix() {
        ElastixIntegration elastix = ElastixIntegration.create("jdbc:mysql://192.168.1.22:3306", "root", "root");
        assertNotNull(elastix);
    }

    @Test
    public void deveOPontoDeEntradaReceberOsDadosDeConexaoComOBancoDeDados() {
        ElastixIntegration elastix = ElastixIntegration.create("http://myurl", "root", EConfiguracaoDev.SENHA_BANCO.getValor());
        assertNotNull(elastix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveNaoAceitarHostNull() {
        ElastixIntegration.create(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveNaoAceitarHostBlank() {
        ElastixIntegration.create("     ", null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveNaoAceitarHostUserNull() {
        ElastixIntegration.create("http://myhost", null, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deveNaoAceitarHostUserBlank() {
        ElastixIntegration.create("http://myhost", "      ", null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deveNaoAceitarHostPasswordNull() {
        ElastixIntegration.create("http://myhost", "user", null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deveNaoAceitarHostPasswordBlank() {
        ElastixIntegration.create("http://myhost", "user", "      ");
    }

    @Test
    public void testURIToString() {
        String path = "http://www.google.com.br";
        try {
            URI uri = new URI(path);
            assertEquals(uri.toString(), path);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ElastixSuite.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
}
