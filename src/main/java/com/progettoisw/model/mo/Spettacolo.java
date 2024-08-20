package com.progettoisw.model.mo;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class Spettacolo {
    private Long idSpettacolo;

    /* 1:N */
    private List<Replica> repliche;
    private String nome;
    private String genere;
    private String compagnia;
    private String descrizione;
    private String immagine;
    private Boolean deleted;

    public Long getIdSpettacolo() {
        return idSpettacolo;
    }

    public void setIdSpettacolo(Long idSpettacolo) {
        this.idSpettacolo = idSpettacolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(String compagnia) {
        this.compagnia = compagnia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Replica> getRepliche() {
        return repliche;
    }

    @JsonSetter
    public void setRepliche(List<Replica> repliche) {
        this.repliche = repliche;
    }

    public Replica getRepliche(int index) {
        return this.repliche.get(index);
    }

    public void setRepliche(int index, Replica replica) {
        this.repliche.add(index, replica);
    }

    public void setRepliche(Replica replica) {
        this.repliche.add(replica);
    }

}
