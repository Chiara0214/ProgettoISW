package com.progettoisw.model.mo;

public class Utente {
    private Long id_utente;

    /* 1:N */
    private Biglietto[] biglietti;
    /* M:N */
    private Coupon[] coupons;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String password;
    private Boolean privilegi;
    private Boolean deleted;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId_utente() {
        return id_utente;
    }

    public void setId_utente(Long id_utente) {
        this.id_utente = id_utente;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPrivilegi() {
        return privilegi;
    }

    public void setPrivilegi(Boolean privilegi) {
        this.privilegi = privilegi;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Biglietto[] getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(Biglietto[] biglietti) {
        this.biglietti = biglietti;
    }

    public Biglietto getBiglietti(int index) {
        return this.biglietti[index];
    }

    public void setBiglietti(int index, Biglietto biglietti) {
        this.biglietti[index] = biglietti;
    }

    public Coupon[] getCoupons() {
        return coupons;
    }

    public void setCoupons(Coupon[] coupons) {
        this.coupons = coupons;
    }

    public Coupon getCoupons(int index) {
        return this.coupons[index];
    }

    public void setCoupons(int index, Coupon coupons) {
        this.coupons[index] = coupons;
    }
}
