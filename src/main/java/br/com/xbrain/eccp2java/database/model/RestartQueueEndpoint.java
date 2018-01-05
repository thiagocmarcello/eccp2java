package br.com.xbrain.eccp2java.database.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;

@Getter
@EqualsAndHashCode
public class RestartQueueEndpoint {

    public static RestartQueueEndpoint create(Protocol protocol, Ipv4 ip, int port) throws URISyntaxException {
        return new RestartQueueEndpoint(protocol, ip, port);
    }

    public enum Protocol {
        HTTP, HTTPS
    }

    private static final String RELOAD_QUEUES_RESOURCE = "/modules/queues/reload.php";

    private final Ipv4 ip;

    private final int port;

    private final URI uri;

    private RestartQueueEndpoint(Protocol protocol, Ipv4 ip, int port) throws URISyntaxException {
        this.ip = ip;
        this.port = port;
        this.uri = new URI(
                protocol.toString().toLowerCase()
                        + "://" + ip + ":" + port + RELOAD_QUEUES_RESOURCE);
    }

    @Override
    public String toString() {
        return uri.toString();
    }
}
