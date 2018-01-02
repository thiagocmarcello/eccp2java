package br.com.xbrain.eccp2java.database.connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author joaomassan@xbrain.com.br (xbrain)
 */
public class ElastixEMFs {
    
    private transient EntityManagerFactory asteriskEMF, callCenterEMF;
    
    public enum PersistenceUnitToDatabase {
        CALL_CENTER_PU("br.com.xbrain.Eccp2JavaAsteriskPU", "/call_center"),
        ASTERISK_PU("br.com.xbrain.Eccp2JavaAsteriskPU", "  /asterisk");
        
        @Getter
        String puName, databaseName;
        
        PersistenceUnitToDatabase(String puName, String databaseName) {
            this.puName = puName;
            this.databaseName = databaseName;
        }
    }
    
    @Getter
    @Setter
    private String url, user, password;
    
    private static final String PROP_KEY_URL = "javax.persistence.jdbc.url";
    private static final String PROP_KEY_USER = "javax.persistence.jdbc.user";
    private static final String PROP_KEY_PASSWORD = "javax.persistence.jdbc.password";
    
    /**
     * NOTE: estou assumindo, por enquanto, que os dois bancos de dados estarão na mesma
     * máquina e sob o mesmo SGBD, mudando apenas o esquema.
     */
    public static ElastixEMFs create(String url, String user, String password) {
        return new ElastixEMFs(url, user, password);
    }

    private ElastixEMFs(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public EntityManagerFactory getAsteriskEMF() {
        if(asteriskEMF == null) {
            asteriskEMF = createEMF(PersistenceUnitToDatabase.ASTERISK_PU);
        }
        return asteriskEMF;
    }
    
    public EntityManagerFactory getCallCenterEMF() {
        if(callCenterEMF == null) {
            callCenterEMF = createEMF(PersistenceUnitToDatabase.CALL_CENTER_PU);
        }
        return callCenterEMF;
    }
        
    private EntityManagerFactory createEMF(PersistenceUnitToDatabase pu2Db) {
        Map<String, String> props = new HashMap<>();
        props.put(PROP_KEY_URL, url + pu2Db.getDatabaseName());
        props.put(PROP_KEY_USER, user);
        props.put(PROP_KEY_PASSWORD, password);
        return Persistence.createEntityManagerFactory(pu2Db.getPuName(), props);
    }
    
    private void writeObject(java.io.ObjectOutputStream oos) throws IOException {
        asteriskEMF = null;
        callCenterEMF = null;
        oos.writeObject(url);
        oos.writeObject(user);
        oos.writeObject(password);
    }

    private void readObject(java.io.ObjectInputStream ois) throws IOException, ClassNotFoundException {
        password = (String) ois.readObject();
        user = (String) ois.readObject();
        url = (String) ois.readObject();
        callCenterEMF = null;
        asteriskEMF = null;
    }
}