package com.progettoisw.model.dao;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Spettacolo;

import java.util.List;

public interface SpettacoloDAO {
    public Spettacolo create(
            String nome,
            String genere,
            String compagnia,
            String descrizione
    ) throws DuplicatedObjectException;

    public void update(Spettacolo spettacolo) throws DuplicatedObjectException;

    public void delete(Spettacolo spettacolo);

    public Spettacolo findBySpettacoloId(Long spettacoloId);

    public Spettacolo findBySpettacoloIdWithDates(Long spettacoloId);

    public List<Spettacolo> findAll();

    public List<Spettacolo> findByTitoloGenereData(String titolo, String genere, String dataInizio, String dataFine);
}
