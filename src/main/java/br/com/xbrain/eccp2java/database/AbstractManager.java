package br.com.xbrain.eccp2java.database;

import br.com.xbrain.eccp2java.database.connection.ElastixEmfs;

import java.io.Serializable;

public abstract class AbstractManager implements Serializable {

    protected final ElastixEmfs elastixEmfs;
    
    public AbstractManager(ElastixEmfs elastixEmfs) {
        this.elastixEmfs = elastixEmfs;
    }
}
