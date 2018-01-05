/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xbrain.eccp2java.xml.entity;

import br.com.xbrain.eccp2java.entity.xml.Elastix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author xbrain
 */
public class ElastixTest {

    private final String host = "myHost";
    private final int port = 12345;
    private final String user = "myUser";
    private final String password = "myPassword";

    public ElastixTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("of");
        Elastix result = Elastix.create(host, port, user, password);
        assertEquals(host, result.getHost());
        assertEquals(port, result.getPort());
        assertEquals(user, result.getUser());
        assertEquals(password, result.getPassword());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Elastix result1 = Elastix.create(host, port, user, password);
        Elastix result2 = Elastix.create(host, port, user, password);
        assertEquals(result1, result2);
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Elastix result1 = Elastix.create(host, port, user, password);
        Elastix result2 = Elastix.create(host, port, user, password);
        assertEquals(result1.hashCode(), result2.hashCode());
    }

}
