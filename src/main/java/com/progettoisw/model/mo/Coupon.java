package com.progettoisw.model.mo;

import java.util.Date;

public class Coupon {
    private Long id_coupon;

    /* N:M */
    private Utente[] utenti;
    private Integer sconto;
    private String genere;
    private Date data_inizio;
    private Date data_fine;
    private Boolean deleted;

    public Long getId_coupon() {
        return id_coupon;
    }

    public void setId_coupon(Long id_coupon) {
        this.id_coupon = id_coupon;
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

    public Date getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(Date data_inizio) {
        this.data_inizio = data_inizio;
    }

    public Date getData_fine() {
        return data_fine;
    }

    public void setData_fine(Date data_fine) {
        this.data_fine = data_fine;
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
