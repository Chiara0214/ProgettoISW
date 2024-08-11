package com.progettoisw.model.mo;

import java.sql.Timestamp;

public class Replica {
    private Long idReplica;

    /* N:1 */
    private Spettacolo spettacolo;
    /* 1:N */
    private Biglietto[] biglietti;
    private Timestamp inizio;
    private Boolean deleted;

    public Long getIdReplica() {
        return idReplica;
    }

    public void setIdReplica(Long idReplica) {
        this.idReplica = idReplica;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Spettacolo getSpettacolo() {
        return spettacolo;
    }

    public void setSpettacolo(Spettacolo spettacolo) {
        this.spettacolo = spettacolo;
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
}
