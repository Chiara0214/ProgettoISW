package com.progettoisw.model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Utente;
import com.progettoisw.model.dao.UtenteDAO;


public class UtenteDAOMySQLJDBCImpl implements UtenteDAO {

  private final String COUNTER_ID = "idUtente";
  Connection conn;

  public UtenteDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Utente create(Long idUtente, Biglietto[] biglietti, String nome, String cognome, String email, String telefono, String password, Boolean privilegi) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void update(Utente utente) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void delete(Utente utente) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Utente findLoggedUser() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Utente findByUtenteId(Long idUtente) {
    PreparedStatement ps;
    Utente utente = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM UTENTE "
              + " WHERE "
              + "   id_utente = ?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, idUtente);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        utente = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return utente;
  }

  @Override
  public Utente findByEmail(String email) {
    PreparedStatement ps;
    Utente utente = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM UTENTE "
              + " WHERE "
              + "   email = ?";

      ps = conn.prepareStatement(sql);
      ps.setString(1, email);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        utente = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return utente;
  }

  Utente read(ResultSet rs) {

    Utente user = new Utente();
    try {
      user.setIdUtente(rs.getLong("id_utente"));
    } catch (SQLException sqle) {
    }
    try {
      user.setNome(rs.getString("nome"));
    } catch (SQLException sqle) {
    }
    try {
      user.setCognome(rs.getString("cognome"));
    } catch (SQLException sqle) {
    }
    try {
      user.setEmail(rs.getString("email"));
    } catch (SQLException sqle) {
    }
    try {
      user.setTelefono(rs.getString("telefono"));
    } catch (SQLException sqle) {
    }
    try {
      user.setPassword(rs.getString("password"));
    } catch (SQLException sqle) {
    }
    try {
      user.setPrivilegi(rs.getBoolean("privilegi"));
    } catch (SQLException sqle) {
    }
    try {
      user.setDeleted(rs.getBoolean("deleted"));
    } catch (SQLException sqle) {
    }

    return user;
  }

}
