package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.BigliettoDAO;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;
import com.progettoisw.model.mo.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BigliettoDAOMySQLJDBCImpl implements BigliettoDAO {

  private final String COUNTER_ID = "idBiglietto";
  Connection conn;

  public BigliettoDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Biglietto create(Long idBiglietto, Replica replica, Utente utente, String nome, String cognome, String categoria, String zona, Integer fila, Integer palco, Integer numero_posto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void update(Biglietto biglietto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Biglietto biglietto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Biglietto findByBigliettoId(Long idBiglietto) {
    PreparedStatement ps;
    Biglietto biglietto = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM BIGLIETTO "
              + " WHERE "
              + "   id_biglietto = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, idBiglietto);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        biglietto = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return biglietto;
  }

  @Override
  public List<Biglietto> findBigliettiByUtente(Utente utente) {
    PreparedStatement ps;
    Biglietto biglietto = null;
    Replica replica = null;
    Spettacolo spettacolo = null;

    List<Biglietto> biglietti = new ArrayList<Biglietto>();

    try {

      String sql
              = " SELECT * "
              + "   FROM BIGLIETTO NATURAL JOIN REPLICA NATURAL JOIN SPETTACOLO NATURAL JOIN UTENTE"
              + " WHERE biglietto_deleted = 0 AND id_utente = ? ";

      ps = conn.prepareStatement(sql);

      ps.setLong(1, utente.getIdUtente());

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        biglietto = read(resultSet);
        utente = UtenteDAOMySQLJDBCImpl.read(resultSet);
        replica = ReplicaDAOMySQLJDBCImpl.read(resultSet);
        spettacolo = SpettacoloDAOMySQLJDBCImpl.read(resultSet);
        biglietto.setUtente(utente);
        replica.setSpettacolo(spettacolo);
        biglietto.setReplica(replica);
        biglietti.add(biglietto);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return biglietti;
  }

  @Override
  public List<Biglietto> findAllBiglietti() {
    PreparedStatement ps;
    Biglietto biglietto = null;
    Utente utente = null;
    Replica replica = null;
    Spettacolo spettacolo = null;

    List<Biglietto> biglietti = new ArrayList<Biglietto>();

    try {

      String sql
              = " SELECT * "
              + "   FROM BIGLIETTO NATURAL JOIN REPLICA NATURAL JOIN SPETTACOLO NATURAL JOIN UTENTE"
              + " WHERE biglietto_deleted = 0 ";

      ps = conn.prepareStatement(sql);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        biglietto = read(resultSet);
        utente = UtenteDAOMySQLJDBCImpl.read(resultSet);
        replica = ReplicaDAOMySQLJDBCImpl.read(resultSet);
        spettacolo = SpettacoloDAOMySQLJDBCImpl.read(resultSet);
        biglietto.setUtente(utente);
        replica.setSpettacolo(spettacolo);
        biglietto.setReplica(replica);
        biglietti.add(biglietto);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return biglietti;
  }

  static Biglietto read(ResultSet rs) {

    Biglietto biglietto = new Biglietto();

    Utente utente = new Utente();
    biglietto.setUtente(utente);

    Replica replica = new Replica();
    biglietto.setReplica(replica);

    try {
      biglietto.setIdBiglietto(rs.getLong("id_biglietto"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setNome(rs.getString("biglietto_nome"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setCognome(rs.getString("biglietto_cognome"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setCategoria(rs.getString("categoria"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setZona(rs.getString("zona"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setFila(rs.getInt("fila"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setPalco(rs.getInt("palco"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setNumeroPosto(rs.getInt("numero_posto"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.getUtente().setIdUtente(rs.getLong("id_utente"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.getReplica().setIdReplica(rs.getLong("id_replica"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.setDeleted(rs.getBoolean("biglietto_deleted"));
    } catch (SQLException sqle) {
    }

    return biglietto;
  }

}
