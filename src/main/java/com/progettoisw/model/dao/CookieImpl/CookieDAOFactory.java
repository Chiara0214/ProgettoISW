package com.progettoisw.model.dao.CookieImpl;

import com.progettoisw.model.dao.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class CookieDAOFactory extends DAOFactory {

  private Map factoryParameters;

  private HttpServletRequest request;
  private HttpServletResponse response;

  public CookieDAOFactory(Map factoryParameters) {
      this.factoryParameters=factoryParameters;
  }

  @Override
  public void beginTransaction() {

    try {
      this.request=(HttpServletRequest) factoryParameters.get("request");
      this.response=(HttpServletResponse) factoryParameters.get("response");;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void commitTransaction() {}

  @Override
  public void rollbackTransaction() {}

  @Override
  public void closeTransaction() {}

  @Override
  public BigliettoDAO getBigliettoDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public CouponDAO getCouponDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ReplicaDAO getReplicaDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public SpettacoloDAO getSpettacoloDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public UtenteDAO getUtenteDAO() {
    return new UtenteDAOCookieImpl(request,response);
  }

  public UsaCouponDAO getUsaCouponDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}