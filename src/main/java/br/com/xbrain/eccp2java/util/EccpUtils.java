package br.com.xbrain.eccp2java.util;

import br.com.xbrain.eccp2java.entity.xml.IEccpEvent;
import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;
import org.reflections.Reflections;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class EccpUtils {

    private static final Map<String, Class<? extends IEccpResponse>> RESPONSE_TYPE_MAP;

    private static final Map<String, Class<? extends IEccpEvent>> EVENT_TYPE_MAP;

    static {
        Reflections reflections = new Reflections("br.com.xbrain.eccp2java.entity.xml");
        Map<String, Class<? extends IEccpResponse>> responseTypeMap = new HashMap<>();
        Map<String, Class<? extends IEccpEvent>> eventTypeMap = new HashMap<>();
        for(Class<?> clss : reflections.getTypesAnnotatedWith(XmlRootElement.class)) {
            XmlRootElement ann = clss.getAnnotation(XmlRootElement.class);
            if(IEccpResponse.class.isAssignableFrom(clss)) {
                responseTypeMap.put(ann.name(), (Class<IEccpResponse>) clss);
            } else if(IEccpEvent.class.isAssignableFrom(clss)) {
                eventTypeMap.put(ann.name(), (Class<IEccpEvent>) clss);
            }
        }

        RESPONSE_TYPE_MAP = Collections.unmodifiableMap(responseTypeMap);
        EVENT_TYPE_MAP = Collections.unmodifiableMap(eventTypeMap);
    }

    public static String generateAgentHash(String agentNumber, String password, String cookie) {
        String agentHash = cookie + agentNumber + password;
        try  {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(agentHash.getBytes(), 0, agentHash.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Não foi possível calcular o código hash do agente", ex);
        }
    }

    public static Map<String, Class<? extends IEccpResponse>> getResponseTypeMap() {
        return RESPONSE_TYPE_MAP;
    }
    
    public static Map<String, Class<? extends IEccpEvent>> getEventTypeMap() {
        return EVENT_TYPE_MAP;
    }
            
}
