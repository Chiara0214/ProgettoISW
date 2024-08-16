package com.progettoisw.controller;

import com.progettoisw.model.dao.*;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Coupon;
import com.progettoisw.model.mo.Spettacolo;
import com.progettoisw.model.mo.Utente;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CouponManagement {

  private CouponManagement() {
  }

  public static void view(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    List<Coupon> coupons;

    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      CouponDAO couponDAO = daoFactory.getCouponDAO();

      coupons = couponDAO.findAllCoupons();

      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("coupons", coupons);
      request.setAttribute("viewUrl", "couponManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }

  public static void insView(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    Utente loggedUser;

    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("viewUrl", "couponManagement/insCouponView");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }

  public static void insert(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      CouponDAO couponDAO = daoFactory.getCouponDAO();

      String sconto = request.getParameter("sconto");
      String genere = request.getParameter("genere");
      String dataInizio = request.getParameter("dataInizio");
      String dataFine = request.getParameter("dataFine");

      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDataInizio = dateFormat.parse(dataInizio);
        Date parsedDataFine = dateFormat.parse(dataFine);
        couponDAO.create(Integer.parseInt(sconto), genere, parsedDataInizio, parsedDataFine);

      } catch(Exception e) {
        applicationMessage = "Errore nella creazione della replica";
        logger.log(Level.INFO, "Tentativo di inserimento della replica fallito");
      }

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "couponManagement/insCouponView");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) daoFactory.rollbackTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) daoFactory.closeTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }

  public static void delete(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;

    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      String couponId = request.getParameter("couponId");

      CouponDAO couponDAO = daoFactory.getCouponDAO();
      Coupon coupon = couponDAO.findByCouponId(Long.parseLong(couponId));
      couponDAO.delete(coupon);

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("viewUrl", "homeManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) daoFactory.rollbackTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) daoFactory.closeTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }
}
