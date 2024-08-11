package com.progettoisw.model.mo;

import java.util.Date;

public class Coupon {
    private Long idCoupon;

    /* N:M */
    private Utente[] utenti;
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

    public Utente getUtenti(int index) {
        return this.utenti[index];
    }

    public void setUtenti(int index, Utente utenti) {
        this.utenti[index] = utenti;
    }
}
