package com.progettoisw.model.dao;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Posto;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Utente;

import java.util.List;

public interface BigliettoDAO {
    public Biglietto create(
            Replica replica,
            Utente utente,
            String nome,
            String cognome,
            String categoria,
            Posto posto) throws DuplicatedObjectException;

    public void update(Biglietto biglietto) throws DuplicatedObjectException;

    public void delete(Biglietto biglietto);

    public Biglietto findByBigliettoId(Long bigliettoId);

    public List<Biglietto> findBigliettiByUtente(Utente utente);

    public List<Biglietto> findByReplicaId(Long replicaId);

    public List<Biglietto> findAllBiglietti();
}
