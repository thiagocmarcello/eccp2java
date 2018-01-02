package br.com.xbrain.eccp2java.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xbrain
 */
public class HttpUtilsTest {

    public HttpUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //    @Test
    public void shouldDoAPostRequestReturningHttpResponseCode() {
        int responseCode = HttpUtils.doPostReturningStatusCode("http://localhost");
        assertEquals(200, responseCode);
    }

    //    @Test
    public void shouldPostReturnA404() {
        int responseCode = HttpUtils.doPostReturningStatusCode("http://localhost/naoexiste");
        assertEquals(404, responseCode);
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void shouldPostNotAcceptNull() {
        HttpUtils.doPostReturningStatusCode(null);
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void shouldPostNotAcceptBlank() {
        HttpUtils.doPostReturningStatusCode("");
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void shouldPostNotAcceptWhiteSpaces() {
        HttpUtils.doPostReturningStatusCode("       ");
    }

    //    @Test
    public void shouldPostUrlStartsWithHttpOrHttps() {
        HttpUtils.doPostReturningStatusCode("http://www.google.com.br");
        HttpUtils.doPostReturningStatusCode("https://www.google.com.br");
        try {
            HttpUtils.doPostReturningStatusCode("www.google.com.br");
        } catch(IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    //    @Test
    public void deveOMetodoGetRetornarCodigoValido() {
        assertEquals(200, HttpUtils.doGetReturningStatusCode("http://localhost", HttpUtils.Param.EMPTY));
    }

    //    @Test
    public void deveOMetodoGetAceitarParametros() {
        assertEquals(200, HttpUtils.doGetReturningStatusCode("http://localhost", HttpUtils.Param.EMPTY));
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void deveOMetodoGetNaoAceitarParametrosNull() {
        assertNotEquals(200, HttpUtils.doGetReturningStatusCode(
                "http://localhost",
                HttpUtils.Param.EMPTY));
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void deveOMetodoGetAceitarURIValida() {
        assertEquals(200, HttpUtils.doGetReturningStatusCode("http://localhost", HttpUtils.Param.EMPTY));
        assertNotEquals(200, HttpUtils.doGetReturningStatusCode("um endereco inválido", HttpUtils.Param.EMPTY));
    }

    //    @Test
    public void deveOMetodoGetAceitarVariosParametros() {
        HttpUtils.Param param1 = HttpUtils.Param.create("chave", "valor");
        HttpUtils.Param param2 = HttpUtils.Param.create("chave", "valor");
        assertEquals(200, HttpUtils.doGetReturningStatusCode("http://localhost", param1, param2));
    }

    //    @Test
    public void deveOMetodoGetNaoExigirParametros() {
        assertEquals(200, HttpUtils.doGetReturningStatusCode("http://localhost"));
    }

    //    @Test
    public void deveOMetodoGetAceitarHttps() {
        assertEquals(200, HttpUtils.doGetReturningStatusCode("https://www.google.com.br"));
    }

    @Test // TODO mover para a classe de teste adqueada e renomear para 'deveReiniciarOAsterisk'
    public void deveOMetodoGetInvocarAURL() {
        int code = HttpUtils.doGetReturningStatusCode("https://192.168.1.23:26000/modules/queues/reload.php",
                HttpUtils.Param.create("hash", "9cbb0bd1a5114bd876ce5680af684e6b18b8d096"));
        assertEquals(200, code);
    }

    //    @Test
    public void deveGerarOHashSha1DoParametro() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            byte[] hashArray = messageDigest.digest("xbrain".getBytes());
            String hash = new BigInteger(1, hashArray).toString(16);
            System.out.println("SHA1 " + hash);
            assertEquals("9cbb0bd1a5114bd876ce5680af684e6b18b8d096", hash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HttpUtilsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
