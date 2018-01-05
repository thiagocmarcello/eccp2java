package br.com.xbrain.eccp2java.database.connection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElastixEmfs {
    
    private transient EntityManagerFactory asteriskEmf;
    private transient EntityManagerFactory callCenterEmf;

    public enum PersistenceUnitToDatabase {
        CALL_CENTER_PU("br.com.xbrain.Eccp2JavaAsteriskPU", "/call_center"),
        ASTERISK_PU("br.com.xbrain.Eccp2JavaAsteriskPU", "  /asterisk");
        
        @Getter
        final String puName;

        @Getter
        final String databaseName;
        
        PersistenceUnitToDatabase(String puName, String databaseName) {
            this.puName = puName;
            this.databaseName = databaseName;
        }
    }
    
    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String user;

    @Getter
    @Setter
    private String password;
    
    private static final String PROP_KEY_URL = "javax.persistence.jdbc.url";
    private static final String PROP_KEY_USER = "javax.persistence.jdbc.user";
    private static final String PROP_KEY_PASSWORD = "javax.persistence.jdbc.password";
    
    /**
     * NOTE: estou assumindo, por enquanto, que os dois bancos de dados estarão na mesma
     * máquina e sob o mesmo SGBD, mudando apenas o esquema.
     */
    public static ElastixEmfs create(String url, String user, String password) {
        return new ElastixEmfs(url, user, password);
    }

    private ElastixEmfs(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public EntityManagerFactory getAsteriskEmf() {
        if (asteriskEmf == null) {
            asteriskEmf = createEmf(PersistenceUnitToDatabase.ASTERISK_PU);
        }
        return asteriskEmf;
    }
    
    public EntityManagerFactory getCallCenterEmf() {
        if (callCenterEmf == null) {
            callCenterEmf = createEmf(PersistenceUnitToDatabase.CALL_CENTER_PU);
        }
        return callCenterEmf;
    }
        
    private EntityManagerFactory createEmf(PersistenceUnitToDatabase pu2Db) {
        Map<String, String> props = new HashMap<>();
        props.put(PROP_KEY_URL, url + pu2Db.getDatabaseName());
        props.put(PROP_KEY_USER, user);
        props.put(PROP_KEY_PASSWORD, password);
        return Persistence.createEntityManagerFactory(pu2Db.getPuName(), props);
    }
    
    private void writeObject(java.io.ObjectOutputStream oos) throws IOException {
        asteriskEmf = null;
        callCenterEmf = null;
        oos.writeObject(url);
        oos.writeObject(user);
        oos.writeObject(password);
    }

    private void readObject(java.io.ObjectInputStream ois) throws IOException, ClassNotFoundException {
        password = (String) ois.readObject();
        user = (String) ois.readObject();
        url = (String) ois.readObject();
        callCenterEmf = null;
        asteriskEmf = null;
    }
}