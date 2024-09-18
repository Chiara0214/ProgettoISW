package com.progettoisw.model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Utente;
import com.progettoisw.model.dao.UtenteDAO;


public class UtenteDAOMySQLJDBCImpl implements UtenteDAO {

  private final String COUNTER_ID = "utenteId";
  Connection conn;

  public UtenteDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Utente create(Long idUtente, String nome, String cognome, String email, String telefono, String password, Boolean privilegi) throws DuplicatedObjectException {
    PreparedStatement ps;
    Utente utente = new Utente();
    utente.setNome(nome);
    utente.setCognome(cognome);
    utente.setEmail(email);
    utente.setTelefono(telefono);
    utente.setPassword(password);
    utente.setPrivilegi(false);

    try {

      String sql
              = " SELECT id_utente "
              + " FROM UTENTE "
              + " WHERE "
              + " utente_deleted = 0 AND "
              + " email = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, utente.getEmail());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("UtenteDAOJDBCImpl.create: Tentativo di inserimento di un utente già esistente.");
      }

      sql = "update counter set counterValue=counterValue+1 where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      ps.executeUpdate();

      sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      resultSet = ps.executeQuery();
      resultSet.next();

      utente.setIdUtente(resultSet.getLong("counterValue"));

      resultSet.close();

      sql
              = " INSERT INTO UTENTE "
              + "   ( id_utente,"
              + "     utente_nome,"
              + "     utente_cognome,"
              + "     email,"
              + "     telefono,"
              + "     password,"
              + "     privilegi,"
              + "     utente_deleted "
              + "   ) "
              + " VALUES (?,?,?,?,?,?,?,0)";

      ps = conn.prepareStatement(sql);
      i = 1;

      ps.setLong(i++, utente.getIdUtente());
      ps.setString(i++, utente.getNome());
      ps.setString(i++, utente.getCognome());
      ps.setString(i++, utente.getEmail());
      ps.setString(i++, utente.getTelefono());
      ps.setString(i++, utente.getPassword());
      ps.setBoolean(i++, utente.getPrivilegi());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return utente;
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

  static Utente read(ResultSet rs) {

    Utente user = new Utente();
    try {
      user.setIdUtente(rs.getLong("id_utente"));
    } catch (SQLException sqle) {
    }
    try {
      user.setNome(rs.getString("utente_nome"));
    } catch (SQLException sqle) {
    }
    try {
      user.setCognome(rs.getString("utente_cognome"));
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
