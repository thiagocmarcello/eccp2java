package br.com.xbrain.eccp2java.xml.entity;

import br.com.xbrain.eccp2java.entity.xml.ElastixLoginData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElastixLoginDataTest {

    private final String host = "myHost";
    private final int port = 12345;
    private final String user = "myUser";
    private final String password = "myPassword";

    public ElastixLoginDataTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("of");
        ElastixLoginData result = ElastixLoginData.create(host, port, user, password);
        assertEquals(host, result.getHost());
        assertEquals(port, result.getPort());
        assertEquals(user, result.getUser());
        assertEquals(password, result.getPassword());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        ElastixLoginData result1 = ElastixLoginData.create(host, port, user, password);
        ElastixLoginData result2 = ElastixLoginData.create(host, port, user, password);
        assertEquals(result1, result2);
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ElastixLoginData result1 = ElastixLoginData.create(host, port, user, password);
        ElastixLoginData result2 = ElastixLoginData.create(host, port, user, password);
        assertEquals(result1.hashCode(), result2.hashCode());
    }

}
