package com.progettoisw.model.mo;

public class Biglietto {
    private Long id_biglietto;

    /* N:1 */
    private Replica replica;
    /* N:1 */
    private Utente utente;
    private String nome;
    private String cognome;
    private String categoria;
    private String zona;
    private Integer fila;
    private Integer palco;
    private Integer numero_posto;
    private Boolean deleted;

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Long getId_biglietto() {
        return id_biglietto;
    }

    public void setId_biglietto(Long id_biglietto) {
        this.id_biglietto = id_biglietto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getPalco() {
        return palco;
    }

    public void setPalco(Integer palco) {
        this.palco = palco;
    }

    public Integer getNumero_posto() {
        return numero_posto;
    }

    public void setNumero_posto(Integer numero_posto) {
        this.numero_posto = numero_posto;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Replica getReplica() {
        return replica;
    }

    public void setReplica(Replica replica) {
        this.replica = replica;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
