package br.com.xbrain.eccp2java.entity.xml;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
@ToString
@EqualsAndHashCode
public class Elastix {

    public static final int DEFAULT_ELASTIX_PORT = 20005;

    public static Elastix create(String host, int port, String user, String password) {
        return new Elastix(host, port, user, password);
    }
    
    @Getter
    private final String host;
    
    @Getter
    private int port = DEFAULT_ELASTIX_PORT;
    
    @Getter
    private final String user;
    
    @Getter
    private final String password;

    private Elastix(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }
}
