package com.progettoisw.controller;

import com.progettoisw.model.dao.DAOFactory;
import com.progettoisw.model.dao.UtenteDAO;
import com.progettoisw.model.dao.SpettacoloDAO;
import com.progettoisw.model.mo.Utente;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
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

      SpettacoloDAO spettacoloDAO = daoFactory.getSpettacoloDAO();

      try {

        spettacoloDAO.create(
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

}
