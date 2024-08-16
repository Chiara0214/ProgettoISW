package com.progettoisw.model.mo;

import java.util.Objects;

public class Posto {
    private String zona;
    private Integer fila;
    private Integer palco;
    private Integer numeroPosto;

    public Posto() {}

    public Posto(String zona, Integer fila, Integer palco, Integer numeroPosto) {
        this.zona = zona;
        this.fila = fila;
        this.palco = palco;
        this.numeroPosto = numeroPosto;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Integer getPalco() {
        return palco;
    }

    public void setPalco(Integer palco) {
        this.palco = palco;
    }

    public Integer getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(Integer numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posto posto = (Posto) o;
        return Objects.equals(getZona(), posto.getZona()) && Objects.equals(getFila(), posto.getFila()) && Objects.equals(getPalco(), posto.getPalco()) && Objects.equals(getNumeroPosto(), posto.getNumeroPosto());
    }

}
