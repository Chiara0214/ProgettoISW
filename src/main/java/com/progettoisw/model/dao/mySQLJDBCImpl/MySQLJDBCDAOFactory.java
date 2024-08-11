package com.progettoisw.model.dao.mySQLJDBCImpl;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.progettoisw.model.dao.*;
import com.progettoisw.services.config.Configuration;

public class MySQLJDBCDAOFactory extends DAOFactory {

  private Map factoryParameters;

  private Connection connection;

  public MySQLJDBCDAOFactory(Map factoryParameters) {
    this.factoryParameters=factoryParameters;
  }

  @Override
  public void beginTransaction() {

    try {
      Class.forName(Configuration.DATABASE_DRIVER);
      this.connection = DriverManager.getConnection(Configuration.DATABASE_URL);
      this.connection.setAutoCommit(false);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void commitTransaction() {
    try {
      this.connection.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollbackTransaction() {

    try {
      this.connection.rollback();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void closeTransaction() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public BigliettoDAO getBigliettoDAO() {
    return new BigliettoDAOMySQLJDBCImpl(connection);
  }

  @Override
  public CouponDAO getCouponDAO() {
    return new CouponDAOMySQLJDBCImpl(connection);
  }

  @Override
  public ReplicaDAO getReplicaDAO() {
    return new ReplicaDAOMySQLJDBCImpl(connection);
  }

  @Override
  public SpettacoloDAO getSpettacoloDAO() {
    return new SpettacoloDAOMySQLJDBCImpl(connection);
  }

  @Override
  public UtenteDAO getUtenteDAO() {
    return new UtenteDAOMySQLJDBCImpl(connection);
  }

}