package com.progettoisw.model.mo;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.List;

public class Coupon {
    private Long idCoupon;

    /* N:M */
    private List<Utente> utenti;
    private Integer sconto;
    private String genere;
    private Date dataInizio;
    private Date dataFine;
    private Boolean deleted;

    public Long getIdCoupon() {
        return idCoupon;
    }

    public void setIdCoupon(Long idCoupon) {
        this.idCoupon = idCoupon;
    }

    public Integer getSconto() {
        return sconto;
    }

    public void setSconto(Integer sconto) {
        this.sconto = sconto;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Utente> getUtenti() {
        return this.utenti;
    }

    @JsonSetter
    public List<Utente> setUtenti(List<Utente> utenti) {
        return this.utenti = utenti;
    }

    public Utente getUtenti(int index) {
        return this.utenti.get(index);
    }

    public void setUtenti(int index, Utente utente) {
        this.utenti.add(index, utente);
    }

    public void setUtenti(Utente utente) {
        this.utenti.add(utente);
    }
}
