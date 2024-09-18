package com.progettoisw.controller;

import com.progettoisw.model.dao.*;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Replica;
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

public class ReplicheManagement {

  private ReplicheManagement() {
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

      } catch(DuplicatedObjectException e) {
        applicationMessage = "Replica già esistente";
        logger.log(Level.INFO, "Tentativo di inserimento di una replica già esistente");
      }

      spettacolo = spettacoloDAO.findBySpettacoloIdWithDates(Long.parseLong(spettacoloId));

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("spettacolo", spettacolo);
      request.setAttribute("viewUrl", "replicheManagement/insReplicaView");

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

      String replicaId = request.getParameter("replicaId");

      ReplicaDAO replicaDAO = daoFactory.getReplicaDAO();
      Replica replica = replicaDAO.findByReplicaId(Long.parseLong(replicaId));

      Long spettacoloId = replica.getSpettacolo().getIdSpettacolo();
      SpettacoloDAO spettacoloDAO = daoFactory.getSpettacoloDAO();

      replicaDAO.delete(replica);

      Spettacolo spettacolo = spettacoloDAO.findBySpettacoloIdWithDates(spettacoloId);

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("spettacolo", spettacolo);
      request.setAttribute("viewUrl", "replicheManagement/insReplicaView");

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
