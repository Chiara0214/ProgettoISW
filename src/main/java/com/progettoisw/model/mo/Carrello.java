package com.progettoisw.model.mo;

import java.util.List;

public class Carrello {
    private Long id_carrello;
    private Utente utente;
    private List<Biglietto> biglietti;

    public Long getCarrelloId() {
        return id_carrello;
    }

    public void setCarrelloId(Long id_carrello) {
        this.id_carrello = id_carrello;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public void setBiglietti(Biglietto biglietto) {
        this.biglietti.add(biglietto);
    }
}
