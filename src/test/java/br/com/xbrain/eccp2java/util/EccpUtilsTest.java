/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xbrain.eccp2java.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author xbrain
 */
public class EccpUtilsTest {
    
    public EccpUtilsTest() {
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
