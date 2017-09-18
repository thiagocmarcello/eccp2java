/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xbrain.eccp2java.util;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import java.util.Map;
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
public class EccpUtilsTest {
    
    public EccpUtilsTest() {
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

    @Test
    public void shouldGenerateSuitableAgentHash() {
        final String complientAgentHash = "e848a906b590c89cc30a67469feae31f"; // MD5-generated hash
        final String agentNumber = "Agent/0001";
        final String password = "0001";
        final String cookie = "myCookie";
        String agentHash = EccpUtils.generateAgentHash(agentNumber, password, cookie);
        assertEquals(complientAgentHash, agentHash);
    }
    
}
