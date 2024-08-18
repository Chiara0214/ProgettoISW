package com.progettoisw.model.mo;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class Utente {
    private Long idUtente;

    /* 1:N */
    private List<Biglietto> biglietti;
    /* M:N */
    private List<Coupon> coupons;
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

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
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

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    @JsonSetter
    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public Biglietto getBiglietti(int index) {
        return this.biglietti.get(index);
    }

    public void setBiglietti(int index, Biglietto biglietto) {
        this.biglietti.add(index, biglietto);
    }

    public void setBiglietti(Biglietto biglietto) {
        this.biglietti.add(biglietto);
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    @JsonSetter
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Coupon getCoupons(int index) {
        return this.coupons.get(index);
    }

    public void setCoupons(int index, Coupon coupon) {
        this.coupons.add(index, coupon);
    }

    public void setCoupons(Coupon coupon) {
        this.coupons.add(coupon);
    }
}
