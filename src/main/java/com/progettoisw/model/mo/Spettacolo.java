package com.progettoisw.model.mo;

public class Spettacolo {
    private Long idSpettacolo;

    /* 1:N */
    private Replica[] repliche;
    private String nome;
    private String genere;
    private String compagnia;
    private String descrizione;
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
