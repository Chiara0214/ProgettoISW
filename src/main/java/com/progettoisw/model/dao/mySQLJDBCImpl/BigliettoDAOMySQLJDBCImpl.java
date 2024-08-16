package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.BigliettoDAO;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;
import com.progettoisw.model.mo.Utente;
import com.progettoisw.model.mo.Posto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BigliettoDAOMySQLJDBCImpl implements BigliettoDAO {

  private final String COUNTER_ID = "bigliettoId";
  Connection conn;

  public BigliettoDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Biglietto create(Replica replica, Utente utente, String nome, String cognome, String categoria, Posto posto) {
    PreparedStatement ps;
    Biglietto biglietto = new Biglietto();
    biglietto.setNome(nome);
    biglietto.setCognome(cognome);
    biglietto.setCategoria(categoria);
    biglietto.setPosto(posto);
    biglietto.setReplica(replica);
    biglietto.setUtente(utente);

    try {

      String sql
              = " SELECT id_biglietto "
              + " FROM BIGLIETTO "
              + " WHERE "
              + " biglietto_deleted = 0 AND "
              + " biglietto_nome = ? AND "
              + " biglietto_cognome = ? AND "
              + " id_replica = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, biglietto.getNome());
      ps.setString(i++, biglietto.getCognome());
      ps.setLong(i++, biglietto.getReplica().getIdReplica());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        System.out.println("SpettacoloDAOJDBCImpl.create: Tentativo di inserimento di un biglietto già esistente.");
      }

      sql = "update counter set counterValue=counterValue+1 where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      ps.executeUpdate();

      sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      resultSet = ps.executeQuery();
      resultSet.next();

      biglietto.setIdBiglietto(resultSet.getLong("counterValue"));

      resultSet.close();

      sql
              = " INSERT INTO BIGLIETTO "
              + "   ( id_biglietto,"
              + "     biglietto_nome,"
              + "     biglietto_cognome,"
              + "     categoria,"
              + "     zona,"
              + "     fila,"
              + "     palco,"
              + "     numero_posto,"
              + "     id_replica,"
              + "     id_utente,"
              + "     biglietto_deleted "
              + "   ) "
              + " VALUES (?,?,?,?,?,?,?,?,?,?,0)";

      ps = conn.prepareStatement(sql);
      i = 1;

      System.out.println(biglietto.getIdBiglietto() + " " + biglietto.getNome() + " " + biglietto.getCognome() + " " + biglietto.getCategoria() + " " + biglietto.getPosto().getZona() + " " + biglietto.getPosto().getFila() + " " + biglietto.getPosto().getPalco() + " " +biglietto.getPosto().getNumeroPosto() + " " + biglietto.getReplica().getIdReplica() + " " + biglietto.getUtente().getIdUtente());

      ps.setLong(i++, biglietto.getIdBiglietto());
      ps.setString(i++, biglietto.getNome());
      ps.setString(i++, biglietto.getCognome());
      ps.setString(i++, biglietto.getCategoria());
      ps.setString(i++, biglietto.getPosto().getZona());
      ps.setInt(i++, biglietto.getPosto().getFila());
      ps.setInt(i++, biglietto.getPosto().getPalco());
      ps.setInt(i++, biglietto.getPosto().getNumeroPosto());
      ps.setLong(i++, biglietto.getReplica().getIdReplica());
      ps.setLong(i++, biglietto.getUtente().getIdUtente());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return biglietto;
  }

  @Override
  public void update(Biglietto biglietto) {
    PreparedStatement ps;

    try {

      String sql
              = " SELECT id_biglietto "
              + " FROM BIGLIETTO "
              + " WHERE "
              + " biglietto_deleted = 0 AND "
              + " biglietto_nome = ? AND "
              + " biglietto_cognome = ? AND "
              + " id_replica = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, biglietto.getNome());
      ps.setString(i++, biglietto.getCognome());
      ps.setLong(i++, biglietto.getReplica().getIdReplica());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();

      resultSet.close();

      if (exist) {
        System.out.println("ContactDAOJDBCImpl.create: Tentativo di aggiornamento in un contatto già esistente.");
      }

      sql
              = " UPDATE BIGLIETTO "
              + " SET "
              + "   biglietto_nome = ?, "
              + "   biglietto_cognome = ?, "
              + "   categoria = ?, "
              + "   zona = ?, "
              + "   fila = ?, "
              + "   palco = ?, "
              + "   numero_posto = ? "
              + " WHERE "
              + "   id_biglietto = ? ";

      System.out.println(biglietto.getNome() + " " + biglietto.getCognome() + " " + biglietto.getCategoria() + " " + biglietto.getPosto().getZona() + " " + biglietto.getPosto().getFila() + " " + biglietto.getPosto().getPalco() + " " +biglietto.getPosto().getNumeroPosto() + " " + biglietto.getIdBiglietto());

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setString(i++, biglietto.getNome());
      ps.setString(i++, biglietto.getCognome());
      ps.setString(i++, biglietto.getCategoria());
      ps.setString(i++, biglietto.getPosto().getZona());
      ps.setInt(i++, biglietto.getPosto().getFila());
      ps.setInt(i++, biglietto.getPosto().getPalco());
      ps.setInt(i++, biglietto.getPosto().getNumeroPosto());
      ps.setLong(i++, biglietto.getIdBiglietto());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
  public List<Biglietto> findByReplicaId(Long replicaId) {
    PreparedStatement ps;
    Biglietto biglietto = null;

    List<Biglietto> biglietti = new ArrayList<Biglietto>();

    try {

      String sql
              = " SELECT * "
              + "   FROM BIGLIETTO "
              + " WHERE biglietto_deleted = 0 AND id_replica = ? ";

      ps = conn.prepareStatement(sql);

      ps.setLong(1, replicaId);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        biglietto = read(resultSet);
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

    Posto posto = new Posto();
    biglietto.setPosto(posto);

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
      biglietto.getPosto().setZona(rs.getString("zona"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.getPosto().setFila(rs.getInt("fila"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.getPosto().setPalco(rs.getInt("palco"));
    } catch (SQLException sqle) {
    }
    try {
      biglietto.getPosto().setNumeroPosto(rs.getInt("numero_posto"));
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
