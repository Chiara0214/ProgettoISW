package com.progettoisw.controller;

import com.progettoisw.model.dao.*;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Spettacolo;
import com.progettoisw.model.mo.Utente;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestioneManagement {

  private GestioneManagement() {
  }

  public static void view(HttpServletRequest request, HttpServletResponse response) {

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
      request.setAttribute("viewUrl", "gestioneManagement/view");

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

  public static void bigliettiView(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    List<Biglietto> biglietti;

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

      BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();

      biglietti = bigliettoDAO.findAllBiglietti();

      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("biglietti", biglietti);
      request.setAttribute("viewUrl", "gestioneManagement/bigliettiView");

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

  public static void insSpettacoloView(HttpServletRequest request, HttpServletResponse response) {

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
      request.setAttribute("viewUrl", "gestioneManagement/insSpettacoloView");

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

  public static void insertSpettacolo(HttpServletRequest request, HttpServletResponse response) {

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

      SpettacoloDAO spettacoloDAO = daoFactory.getSpettacoloDAO();
      Spettacolo spettacolo = null;

      try {

        spettacolo = spettacoloDAO.create(
                request.getParameter("titolo"),
                request.getParameter("genere"),
                request.getParameter("compagnia"),
                request.getParameter("descrizione"));

      } catch (Exception e) {
        applicationMessage = "Errore nella creazione dello spettacolo";
        logger.log(Level.INFO, "Tentativo di inserimento di spettacolo fallito");
      }

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("spettacolo", spettacolo);
      request.setAttribute("viewUrl", "gestioneManagement/insReplicaView");

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

  public static void insertReplica(HttpServletRequest request, HttpServletResponse response) {

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

      ReplicaDAO replicaDAO = daoFactory.getReplicaDAO();
      String spettacoloId = request.getParameter("spettacoloId");

      SpettacoloDAO spettacoloDAO = daoFactory.getSpettacoloDAO();

      Spettacolo spettacolo = spettacoloDAO.findBySpettacoloId(Long.parseLong(spettacoloId));

      String date = request.getParameter("data");
      String time = request.getParameter("ora");
      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsedDate = dateFormat.parse(date + " " + time); //Mon Aug 26 17:58:00 CEST 2024

        replicaDAO.create(
                  spettacolo,
                  parsedDate);

      } catch(Exception e) {
      applicationMessage = "Errore nella creazione della replica";
      logger.log(Level.INFO, "Tentativo di inserimento della replica fallito");
      }

      spettacolo = spettacoloDAO.findBySpettacoloIdWithDates(Long.parseLong(spettacoloId));

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("spettacolo", spettacolo);
      request.setAttribute("viewUrl", "gestioneManagement/insReplicaView");

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

  public static void insCouponView(HttpServletRequest request, HttpServletResponse response) {

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
      request.setAttribute("viewUrl", "gestioneManagement/insCouponView");

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

  public static void insertCoupon(HttpServletRequest request, HttpServletResponse response) {

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
      request.setAttribute("viewUrl", "gestioneManagement/insCouponView");

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

  public static void deleteSpettacolo(HttpServletRequest request, HttpServletResponse response) {

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

      String spettacoloId = request.getParameter("spettacoloId");

      SpettacoloDAO spettacoloDAO = daoFactory.getSpettacoloDAO();
      Spettacolo spettacolo = spettacoloDAO.findBySpettacoloId(Long.parseLong(spettacoloId));
      spettacoloDAO.delete(spettacolo);

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("spettacolo", spettacolo);
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
