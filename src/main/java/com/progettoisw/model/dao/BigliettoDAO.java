package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Utente;

import java.util.List;

public interface BigliettoDAO {
    public Biglietto create(
            Long idBiglietto,
            Replica replica,
            Utente utente,
            String nome,
            String cognome,
            String categoria,
            String zona,
            Integer fila,
            Integer palco,
            Integer numero_posto);

    public void update(Biglietto biglietto);

    public void delete(Biglietto biglietto);

    public Biglietto findByBigliettoId(Long bigliettoId);

    public List<Biglietto> findBigliettiByUtente(Utente utente);

    public List<Biglietto> findAllBiglietti();
}
