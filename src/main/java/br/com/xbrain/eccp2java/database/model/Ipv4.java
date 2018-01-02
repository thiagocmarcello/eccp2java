package br.com.xbrain.eccp2java.database.model;

import lombok.Value;

import java.io.Serializable;
import java.util.Arrays;

@Value
public class Ipv4 implements Serializable {

    public static Ipv4 of(String part1, String part2, String part3, String part4) {
        return new Ipv4(part1, part2, part3, part4);
    }

    public static Ipv4 of(String fullIpv4Address) {
        String[] p = fullIpv4Address.split("\\.");
        return Ipv4.of(p[0], p[1], p[2], p[3]);
    }

    private Ipv4(String part1, String part2, String part3, String part4) {
        if( isNotValid(part1, part2, part3, part4)) {
            throw new IllegalArgumentException("Partes do IP não são válidas.");
        }
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
        this.part4 = part4;
    }

    private boolean isNotValid(String... parts) {
        return Arrays.stream(parts).filter(this::checkPart).count() < 4;
    }

    private boolean checkPart(String part) {
        return part.matches("\\d{1,3}");
    }

    private String part1;

    private String part2;

    private String part3;

    private String part4;

    @Override
    public String toString() {
        return part1 + "." + part2 + "." + part3 + "." + part4;
    }

}