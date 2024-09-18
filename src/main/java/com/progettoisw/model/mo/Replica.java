package com.progettoisw.model.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.List;

public class Replica {
    private Long idReplica;

    /* N:1 */
    private Spettacolo spettacolo;
    /* 1:N */
    private List<Biglietto> biglietti;
    private Date inizio;
    private Boolean deleted;

    public Long getIdReplica() {
        return idReplica;
    }

    public void setIdReplica(Long idReplica) {
        this.idReplica = idReplica;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "GMT+2")
    public Date getInizio() {
        return inizio;
    }

    public void setInizio(Date inizio) {
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
}
