package com.gft.moedas.DTO.troca;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FazerTrocaDTO {
    private String moeda1;
    private String moeda2;
    private Double quantidade;

    public FazerTrocaDTO() {
    }

    public FazerTrocaDTO(String moeda1, String moeda2, Double quantidade) {
        this.moeda1 = moeda1;
        this.moeda2 = moeda2;
        this.quantidade = quantidade;
    }
}
