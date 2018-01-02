package br.com.xbrain.eccp2java;

import br.com.xbrain.eccp2java.entity.xml.IEccpResponse;

import java.util.HashMap;
import java.util.Map;

public class EccpResponseHeap {

    private static EccpResponseHeap instance;

    public static EccpResponseHeap instance() {
        if(instance == null) {
            instance = new EccpResponseHeap();
        }
        return instance;
    }

    private final Map<Long, IEccpResponse> responses = new HashMap<>();

    private EccpResponseHeap() {}

    public synchronized void add(IEccpResponse response) {
        responses.put(response.getId(), response);
        notifyAll();
    }

    public IEccpResponse retrieve(Long requestId) {
        IEccpResponse response;
        synchronized (responses) {
            response = responses.remove(requestId);
        }
        return response;
    }
}
