package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.ReplicaDAO;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;



public class ReplicaDAOMySQLJDBCImpl implements ReplicaDAO {

  private final String COUNTER_ID = "replicaId";
  Connection conn;

  public ReplicaDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Replica create(Spettacolo spettacolo, Date inizio) throws DuplicatedObjectException {
    PreparedStatement ps;
    Replica replica = new Replica();
    replica.setSpettacolo(spettacolo);
    replica.setInizio(inizio);

    try {

      String sql
              = " SELECT id_replica "
              + " FROM REPLICA "
              + " WHERE "
              + " replica_deleted = 0 AND "
              + " inizio = ? AND "
              + " id_spettacolo = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1;
      Timestamp ts = new Timestamp(replica.getInizio().getTime());
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      ps.setObject(i++, formatter.format(ts));
      ps.setLong(i++, replica.getSpettacolo().getIdSpettacolo());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("ReplicaDAOJDBCImpl.create: Tentativo di creazione di una replica già esistente.");
      }

      sql = "update counter set counterValue=counterValue+1 where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      ps.executeUpdate();

      sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      resultSet = ps.executeQuery();
      resultSet.next();

      replica.setIdReplica(resultSet.getLong("counterValue"));

      resultSet.close();

      sql
              = " INSERT INTO REPLICA "
              + "   ( id_replica,"
              + "     inizio,"
              + "     id_spettacolo,"
              + "     replica_deleted "
              + "   ) "
              + " VALUES (?,?,?,0)";

      ps = conn.prepareStatement(sql);
      i = 1;

      ps.setLong(i++, replica.getIdReplica());
      ps.setObject(i++, formatter.format(ts));
      ps.setLong(i++, replica.getSpettacolo().getIdSpettacolo());
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return replica;
  }

  @Override
  public void update(Replica replica) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Replica replica) {
    PreparedStatement ps;

    try {

      String sql
              = " UPDATE REPLICA "
              + " SET replica_deleted = 1 "
              + " WHERE "
              + " id_replica = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, replica.getIdReplica());
      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
      replica.setDeleted(rs.getBoolean("replica_deleted"));
    } catch (SQLException sqle) {
    }

    return replica;
  }

}
