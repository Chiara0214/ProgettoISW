package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.sql.Timestamp;

public interface ReplicaDAO {
    public Replica create(
            Long idReplica,
            Spettacolo spettacolo,
            Biglietto[] biglietti,
            Timestamp inizio
    );

    public void update(Replica replica);

    public void delete(Replica replica);

    public Replica findByReplicaId(Long replicaId);
}
