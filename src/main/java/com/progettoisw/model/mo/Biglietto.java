package com.progettoisw.model.mo;

public class Biglietto {
    private Long idBiglietto;

    /* N:1 */
    private Replica replica;
    /* N:1 */
    private Utente utente;
    private String nome;
    private String cognome;
    private String categoria;
    private Posto posto;
    private Boolean deleted;

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Long getIdBiglietto() {
        return idBiglietto;
    }

    public void setIdBiglietto(Long idBiglietto) {
        this.idBiglietto = idBiglietto;
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

    public Posto getPosto() {
        return posto;
    }

    public void setPosto(Posto posto) {
        this.posto = posto;
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
