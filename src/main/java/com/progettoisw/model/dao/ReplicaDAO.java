package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.util.Date;

public interface ReplicaDAO {
    public Replica create(
            Spettacolo spettacolo,
            Date inizio
    );

    public void update(Replica replica);

    public void delete(Replica replica);

    public Replica findByReplicaId(Long replicaId);
}
