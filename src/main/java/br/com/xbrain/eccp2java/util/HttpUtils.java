package br.com.xbrain.eccp2java.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class HttpUtils {

    private static final Logger LOG = Logger.getLogger(HttpUtils.class.getName());

    public static int doPostReturningStatusCode(String to) {
        try {
            URI uri = new URI(to);
            HttpPost post = new HttpPost(uri);
            return execute(post).getStatusLine().getStatusCode();
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static int doGetReturningStatusCode(String to, Param... params) {
        if (params == null) {
            throw new IllegalArgumentException("Parâmetros não pode ser nulo");
        }
        try {
            URIBuilder uriBuilder = new URIBuilder(to);
            for (Param param : params) {
                uriBuilder.addParameter(param.getKey(), param.getValue());
            }
            HttpGet get = new HttpGet(uriBuilder.toString());
            return execute(get).getStatusLine().getStatusCode();
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("500: URI " + to + " inválida", ex);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } finally  {
            LOG.info("Executou a chamada para " + to);
        }
    }

    private static CloseableHttpResponse execute(HttpRequestBase request) throws IOException {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            CloseableHttpResponse response = httpClient.execute(request);
            response.close();
            return response;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static class Param {

        public static final Param EMPTY = new Param("", "");

        public static Param create(String key, String value) {
            return new Param(key, value);
        }

        @Getter
        @Setter
        private String key;

        @Getter
        @Setter
        private String value;

        private Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
