package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.util.List;

public interface SpettacoloDAO {
    public Spettacolo create(
            Long idSpettacolo,
            List<Replica> repliche,
            String nome,
            String genere,
            String compagnia,
            String descrizione
    );

    public void update(Spettacolo spettacolo);

    public void delete(Spettacolo spettacolo);

    public Spettacolo findBySpettacoloId(Long spettacoloId);

    public Spettacolo findBySpettacoloIdWithDates(Long spettacoloId);

    public List<Spettacolo> findByTitoloGenereData(String titolo, String genere, String dataInizio, String dataFine);
}
