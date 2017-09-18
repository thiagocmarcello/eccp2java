package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEMFs;
import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;
import java.util.concurrent.Callable;
import javax.persistence.EntityManager;


/**
 *
 * @author xbrain (joaomassan@xbrain.com.br)
 */
public abstract class AbstractDAO<T, P> {

    protected AbstractDAO() {}
    
    public abstract T create(T object) throws ElastixIntegrationException;
    
    public abstract T update(T object) throws ElastixIntegrationException;
    
    public abstract T find(P primaryKey) throws ElastixIntegrationException;
    
    public abstract void remove(T object) throws ElastixIntegrationException;
    
    protected void executeTransactional(EntityManager em, Callable<Void>... callables) throws ElastixIntegrationException {
        try {
            em.getTransaction().begin();
            for(Callable callable : callables) {
                callable.call();
            }
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ElastixIntegrationException.create(ElastixIntegrationException.Error.TX_EXECUTION_ERROR, ex);
        } finally {
            em.close();
        }
    }
}
