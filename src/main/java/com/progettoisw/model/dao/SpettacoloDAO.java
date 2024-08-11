package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

public interface SpettacoloDAO {
    public Spettacolo create(
            Long idSpettacolo,
            Replica[] repliche,
            String nome,
            String genere,
            String compagnia,
            String descrizione
    );

    public void update(Spettacolo spettacolo);

    public void delete(Spettacolo spettacolo);

    public Spettacolo findBySpettacoloId(Long spettacoloId);
}
