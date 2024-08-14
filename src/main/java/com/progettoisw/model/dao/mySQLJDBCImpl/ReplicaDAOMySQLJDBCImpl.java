package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.ReplicaDAO;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.sql.*;


public class ReplicaDAOMySQLJDBCImpl implements ReplicaDAO {

  private final String COUNTER_ID = "idReplica";
  Connection conn;

  public ReplicaDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Replica create(Long idReplica, Spettacolo spettacolo, Biglietto[] biglietti, Timestamp inizio) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void update(Replica replica) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Replica replica) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Replica findByReplicaId(Long idReplica) {
    PreparedStatement ps;
    Replica replica = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM REPLICA "
              + " WHERE "
              + "   id_replica = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, idReplica);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        replica = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return replica;
  }

  static Replica read(ResultSet rs) {

    Replica replica = new Replica();

    Spettacolo spettacolo = new Spettacolo();
    replica.setSpettacolo(spettacolo);

    try {
      replica.setIdReplica(rs.getLong("id_replica"));
    } catch (SQLException sqle) {
    }
    try {
      replica.setInizio(rs.getTimestamp("inizio"));
    } catch (SQLException sqle) {
    }
    try {
      replica.getSpettacolo().setIdSpettacolo(rs.getLong("id_spettacolo"));
    } catch (SQLException sqle) {
    }
    try {
      replica.setDeleted(rs.getBoolean("deleted"));
    } catch (SQLException sqle) {
    }

    return replica;
  }

}
