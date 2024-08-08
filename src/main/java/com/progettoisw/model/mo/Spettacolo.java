package com.progettoisw.model.mo;

public class Spettacolo {
    private Long id_spettacolo;

    /* 1:N */
    private Replica[] repliche;
    private String nome;
    private String genere;
    private String compagnia;
    private String descrizione;
    private Boolean deleted;

    public Long getId_spettacolo() {
        return id_spettacolo;
    }

    public void setId_spettacolo(Long id_spettacolo) {
        this.id_spettacolo = id_spettacolo;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Replica[] getRepliche() {
        return repliche;
    }

    public void setRepliche(Replica[] repliche) {
        this.repliche = repliche;
    }

    public Replica getRepliche(int index) {
        return this.repliche[index];
    }

    public void setRepliche(int index, Replica repliche) {
        this.repliche[index] = repliche;
    }

}
