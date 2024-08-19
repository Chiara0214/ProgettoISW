package com.progettoisw.model.dao;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.util.Date;

public interface ReplicaDAO {
    public Replica create(
            Spettacolo spettacolo,
            Date inizio
    ) throws DuplicatedObjectException;

    public void update(Replica replica);

    public void delete(Replica replica);

    public Replica findByReplicaId(Long replicaId);

    public Replica findByReplicaIdWithSpettacolo(Long replicaId);
}
