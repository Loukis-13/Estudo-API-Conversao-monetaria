package com.gft.moedas.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Troca {
    private String moeda1;
    private String moeda2;

    private Double quantidade1;
    private Double quantidade2;

    private Double valorMoeda2;

    private String feitaEm;

    public Troca() {
    }

    public Troca(String moeda1, String moeda2, Double quantidade1, Double quantidade2, Double valorMoeda2, String feitaEm) {
        this.moeda1 = moeda1;
        this.moeda2 = moeda2;
        this.quantidade1 = quantidade1;
        this.quantidade2 = quantidade2;
        this.valorMoeda2 = valorMoeda2;
        this.feitaEm = feitaEm;
    }
}
