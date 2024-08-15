package com.progettoisw.model.dao.mySQLJDBCImpl;

import com.progettoisw.model.dao.SpettacoloDAO;
import com.progettoisw.model.mo.Replica;
import com.progettoisw.model.mo.Spettacolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SpettacoloDAOMySQLJDBCImpl implements SpettacoloDAO {

  private final String COUNTER_ID = "idSpettacolo";
  Connection conn;

  public SpettacoloDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Spettacolo create(String nome, String genere, String compagnia, String descrizione) {
    PreparedStatement ps;
    Spettacolo spettacolo = new Spettacolo();
    spettacolo.setNome(nome);
    spettacolo.setGenere(genere);
    spettacolo.setCompagnia(compagnia);
    spettacolo.setDescrizione(descrizione);

    try {

      String sql
              = " SELECT id_spettacolo "
              + " FROM SPETTACOLO "
              + " WHERE "
              + " deleted ='N' AND "
              + " nome = ? AND "
              + " genere = ? AND "
              + " compagnia = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, spettacolo.getNome());
      ps.setString(i++, spettacolo.getGenere());
      ps.setString(i++, spettacolo.getCompagnia());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        System.out.println("SpettacoloDAOJDBCImpl.create: Tentativo di inserimento di uno spettacolo già esistente.");
      }

      sql
              = " INSERT INTO SPETTACOLO "
              + "   ( nome,"
              + "     genere,"
              + "     compagnia,"
              + "     descrizione,"
              + "     deleted "
              + "   ) "
              + " VALUES (?,?,?,?,0)";

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setString(i++, spettacolo.getNome());
      ps.setString(i++, spettacolo.getGenere());
      ps.setString(i++, spettacolo.getCompagnia());
      ps.setString(i++, spettacolo.getDescrizione());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return spettacolo;
  }

  @Override
  public void update(Spettacolo spettacolo) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Spettacolo spettacolo) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Spettacolo findBySpettacoloId(Long idSpettacolo) {
    PreparedStatement ps;
    Spettacolo spettacolo = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM SPETTACOLO "
              + " WHERE "
              + "   id_spettacolo = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, idSpettacolo);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        spettacolo = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return spettacolo;
  }

  @Override
  public Spettacolo findBySpettacoloIdWithDates(Long spettacoloId) {
    PreparedStatement ps;
    Spettacolo spettacolo = null;
    Replica replica;
    List<Replica> repliche = new ArrayList<Replica>();

    try {
      String sql
              = " SELECT * "
              + "   FROM SPETTACOLO NATURAL JOIN REPLICA "
              + " WHERE "
              + "   deleted  = 'N' AND id_spettacolo = ? ";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, spettacoloId);

      ResultSet resultSet = ps.executeQuery();

      if(resultSet.next()) {
        spettacolo = read(resultSet);
        replica = ReplicaDAOMySQLJDBCImpl.read(resultSet);
        repliche.add(replica);
      }
      while (resultSet.next()) {
        replica = ReplicaDAOMySQLJDBCImpl.read(resultSet);
        repliche.add(replica);
      }
      spettacolo.setRepliche(repliche);

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return spettacolo;
  }

  @Override
  public List<Spettacolo> findByTitoloGenereData(String titolo, String genere, String dataInizio, String dataFine) {
    PreparedStatement ps;
    Spettacolo spettacolo;
    Replica replica;
    List<Spettacolo> spettacoli = new ArrayList<Spettacolo>();

    try {
      String sql
              = " SELECT * "
              + "   FROM SPETTACOLO NATURAL JOIN REPLICA "
              + " WHERE "
              + "   deleted  = 'N' ";
      if (titolo != null && !titolo.isEmpty()) {
        sql += " AND nome LIKE ? ";
      }
      if (genere != null && !genere.isEmpty()) {
        sql += " AND genere = ? ";
      }
      if (dataInizio != null && !dataInizio.isEmpty()) {
        sql += " AND DATE(inizio) >= ? ";
      }
      if (dataFine != null && !dataFine.isEmpty()) {
        sql += " AND DATE(inizio) <= ? ";
      }

      ps = conn.prepareStatement(sql);
      int i = 1;
      if (titolo != null && !titolo.isEmpty()) {
        ps.setString(i++, "%" + titolo + "%");
      }
      if (genere != null && !genere.isEmpty()) {
        ps.setString(i++, genere);
      }
      if (dataInizio != null && !dataInizio.isEmpty()) {
        ps.setString(i++, dataInizio);
      }
      if (dataFine != null && !dataFine.isEmpty()) {
        ps.setString(i++, dataFine);
      }

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        spettacolo = read(resultSet);
        Long spettacoloId = spettacolo.getIdSpettacolo();
        Spettacolo inList = spettacoli.stream().filter(s -> {return s.getIdSpettacolo().equals(spettacoloId);}).findAny().orElse(null);
        replica = ReplicaDAOMySQLJDBCImpl.read(resultSet);

        //se è già presente
        if(inList != null) {
          inList.setRepliche(replica);
        } else {
          //se non è già presente
          List<Replica> repliche = new ArrayList<Replica>();
          repliche.add(replica);
          spettacolo.setRepliche(repliche);
          spettacoli.add(spettacolo);
        }

      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return spettacoli;
  }

  static Spettacolo read(ResultSet rs) {

    Spettacolo spettacolo = new Spettacolo();
    try {
      spettacolo.setIdSpettacolo(rs.getLong("id_spettacolo"));
    } catch (SQLException sqle) {
    }
    try {
      spettacolo.setNome(rs.getString("nome"));
    } catch (SQLException sqle) {
    }
    try {
      spettacolo.setGenere(rs.getString("genere"));
    } catch (SQLException sqle) {
    }
    try {
      spettacolo.setCompagnia(rs.getString("compagnia"));
    } catch (SQLException sqle) {
    }
    try {
      spettacolo.setDescrizione(rs.getString("descrizione"));
    } catch (SQLException sqle) {
    }
    try {
      spettacolo.setDeleted(rs.getBoolean("deleted"));
    } catch (SQLException sqle) {
    }

    return spettacolo;
  }

}
