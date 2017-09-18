package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import java.io.Serializable;


/**
 *
 * @author joaomassan@xbrain.com.br (joaomassan@xbrain.com.br)
 */
public abstract class AbstractManager<T> implements Serializable { 

    protected ElastixEMFs elastixEMFs;
    
    public AbstractManager(ElastixEMFs elastixEMF) {
        this.elastixEMFs = elastixEMF;
    }
}
