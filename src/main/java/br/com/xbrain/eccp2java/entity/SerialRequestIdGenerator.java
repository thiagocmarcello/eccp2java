package br.com.xbrain.eccp2java.entity;

public class SerialRequestIdGenerator {

    private static long requestId = 0L;

    public static long nextId() {
        return ++requestId;
    }

}
