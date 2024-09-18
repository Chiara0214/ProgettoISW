package com.progettoisw.model.mo;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class Carrello {
    private List<Biglietto> biglietti;

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    @JsonSetter
    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

}
