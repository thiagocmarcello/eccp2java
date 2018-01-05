package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.exception.ElastixIntegrationException;

import javax.persistence.EntityManager;
import java.util.concurrent.Callable;


/**
 *
 * @author xbrain (joaomassan@xbrain.com.br)
 */
public abstract class AbstractDao<T, P> {

    AbstractDao() {}
    
    public abstract T create(T object) throws ElastixIntegrationException;
    
    public abstract T update(T object) throws ElastixIntegrationException;
    
    public abstract T find(P primaryKey) throws ElastixIntegrationException;
    
    public abstract void remove(T object) throws ElastixIntegrationException;
    
    protected void executeInTransaction(
            EntityManager em,
            Callable<?>... callables) throws ElastixIntegrationException {

        try {
            em.getTransaction().begin();

            for (Callable callable : callables) {
                callable.call();
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new ElastixIntegrationException("Erro ao executar a transação", ex);
        } finally {
            em.close();
        }
    }
}
