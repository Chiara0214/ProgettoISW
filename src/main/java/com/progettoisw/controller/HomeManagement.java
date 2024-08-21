package com.progettoisw.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;

import com.progettoisw.model.mo.Utente;
import com.progettoisw.model.dao.DAOFactory;
import com.progettoisw.model.dao.UtenteDAO;

public class HomeManagement {

  private HomeManagement() {
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
      request.setAttribute("viewUrl", "homeManagement/view");

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

  public static void logon(HttpServletRequest request, HttpServletResponse response) {

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

      String email = request.getParameter("email");
      String password = request.getParameter("password");

      UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
      Utente utente = utenteDAO.findByEmail(email);

      if (utente == null || !utente.getPassword().equals(password)) {
        sessionUserDAO.delete(null);
        applicationMessage = "Email e password errati!";
        loggedUser=null;
      } else {
        loggedUser = sessionUserDAO.create(utente.getIdUtente(), utente.getNome(), utente.getCognome(), null, null, null, utente.getPrivilegi());
      }

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
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

  public static void logout(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    
    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      sessionUserDAO.delete(null);

      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",false);
      request.setAttribute("loggedUser", null);
      request.setAttribute("viewUrl", "homeManagement/view");

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

  public static void registrazioneView(HttpServletRequest request, HttpServletResponse response) {

    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);

      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "homeManagement/registrazioneView");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      throw new RuntimeException(e);
    }

  }

  public static void registrazione(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente utente;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();

    try {

      Map sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      String nome = request.getParameter("nome");
      String cognome = request.getParameter("cognome");
      String email = request.getParameter("email");
      String telefono = request.getParameter("telefono");
      String password = request.getParameter("password");

      UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
      utente = utenteDAO.create(null, nome, cognome, email, telefono, password, null);

      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();

      Utente loggedUser = sessionUserDAO.create(utente.getIdUtente(), utente.getNome(), utente.getCognome(), null, null, null, utente.getPrivilegi());

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      applicationMessage = "Registrato";

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "homeManagement/view");

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

}
