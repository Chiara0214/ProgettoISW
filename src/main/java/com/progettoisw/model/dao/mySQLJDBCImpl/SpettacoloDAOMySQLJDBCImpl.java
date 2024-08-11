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
  public Spettacolo create(Long idSpettacolo, Replica[] repliche, String nome, String genere, String compagnia, String descrizione) {
    throw new UnsupportedOperationException("Not supported yet.");
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
  public List<Spettacolo> findByTitoloGenere(String titolo, String genere) {
    PreparedStatement ps;
    Spettacolo spettacolo;
    List<Spettacolo> spettacoli = new ArrayList<Spettacolo>();

    try {
      System.out.println(genere);
      String sql
              = " SELECT * "
              + "   FROM SPETTACOLO "
              + " WHERE "
              + "   deleted  = 'N' ";
      if (titolo != null && !titolo.isEmpty()) {
        sql += " AND nome LIKE ? ";
      }
      if (genere != null && !genere.isEmpty()) {
        sql += " AND genere = ? ";
      }

      ps = conn.prepareStatement(sql);
      int i = 1;
      if (titolo != null && !titolo.isEmpty()) {
        ps.setString(i++, "%" + titolo + "%");
      }
      if (genere != null && !genere.isEmpty()) {
        ps.setString(i++, genere);
      }

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        spettacolo = read(resultSet);
        spettacoli.add(spettacolo);
      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return spettacoli;
  }

  Spettacolo read(ResultSet rs) {

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
