package com.progettoisw.model.dao;

import com.progettoisw.model.dao.mySQLJDBCImpl.MySQLJDBCDAOFactory;
import com.progettoisw.model.dao.CookieImpl.CookieDAOFactory;

import java.util.Map;

public abstract class DAOFactory {

  // List of DAO types supported by the factory
  public static final String MYSQLJDBCIMPL = "MySQLJDBCImpl";
  public static final String COOKIEIMPL= "CookieImpl";

  public abstract void beginTransaction();
  public abstract void commitTransaction();
  public abstract void rollbackTransaction();
  public abstract void closeTransaction();
  
  public abstract BigliettoDAO getBigliettoDAO();

  public abstract CouponDAO getCouponDAO();

  public abstract ReplicaDAO getReplicaDAO();

  public abstract SpettacoloDAO getSpettacoloDAO();

  public abstract UtenteDAO getUtenteDAO();

  public abstract UsaCouponDAO getUsaCouponDAO();

  public static DAOFactory getDAOFactory(String whichFactory,Map factoryParameters) {

    if (whichFactory.equals(MYSQLJDBCIMPL)) {
      return new MySQLJDBCDAOFactory(factoryParameters);
    } else if (whichFactory.equals(COOKIEIMPL)) {
      return new CookieDAOFactory(factoryParameters);
    } else {
      return null;
    }
  }
}

