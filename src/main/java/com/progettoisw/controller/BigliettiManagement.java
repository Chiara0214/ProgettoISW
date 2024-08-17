package com.progettoisw.controller;

import com.progettoisw.model.dao.*;
import com.progettoisw.model.mo.*;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BigliettiManagement {

    private BigliettiManagement() {
    }

    public static void view(HttpServletRequest request, HttpServletResponse response) {

        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        List<Biglietto> biglietti;

        Logger logger = LogService.getApplicationLogger();

        try {

            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            daoFactory.beginTransaction();

            BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();

            Boolean gestione = false;

            String s = request.getParameter("gestione");
            if (s != null && s.equals("true")) {
                gestione = true;
            }

            if(gestione) {
                biglietti = bigliettoDAO.findAllBiglietti();
            }
            else {
                biglietti = bigliettoDAO.findBigliettiByUtente(loggedUser);
            }

            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("gestione", gestione);
            request.setAttribute("biglietti", biglietti);
            request.setAttribute("viewUrl", "bigliettiManagement/view");

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

            ReplicaDAO replicaDAO = daoFactory.getReplicaDAO();
            Replica replica = null;

            String replicaId = request.getParameter("replicaId");

            replica = replicaDAO.findByReplicaId(Long.parseLong(replicaId));

            List<Posto> postiOccupati = findPostiOccupati(daoFactory, Long.parseLong(replicaId));

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("replica", replica);
            request.setAttribute("postiOccupati", postiOccupati);
            request.setAttribute("viewUrl", "bigliettiManagement/insBigliettoView");

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

            BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();

            ReplicaDAO replicaDAO = daoFactory.getReplicaDAO();

            String replicaId = request.getParameter("replicaId");
            Replica replica = replicaDAO.findByReplicaId(Long.parseLong(replicaId));

            Posto posto = new Posto();
            posto.setZona(request.getParameter("zona"));
            posto.setFila(Integer.parseInt(request.getParameter("fila")));
            String palco = request.getParameter("palco");
            if(palco!=null && !palco.isEmpty()){
                posto.setPalco(Integer.parseInt(palco));
            } else {
                posto.setPalco(null);
            }
            posto.setNumeroPosto(Integer.parseInt(request.getParameter("numero_posto")));

            try {

                bigliettoDAO.create(
                        replica,
                        loggedUser,
                        request.getParameter("nome"),
                        request.getParameter("cognome"),
                        request.getParameter("categoria"),
                        posto
                );


            } catch (Exception e) {
                applicationMessage = "Errore nella creazione dello spettacolo";
                logger.log(Level.INFO, "Tentativo di inserimento di spettacolo fallito");
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

    public static void modifyView(HttpServletRequest request, HttpServletResponse response) {

        DAOFactory sessionDAOFactory = null;
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

            Long bigliettoId = Long.parseLong(request.getParameter("bigliettoId"));

            BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();
            Biglietto biglietto = bigliettoDAO.findByBigliettoId(bigliettoId);

            Long replicaId = biglietto.getReplica().getIdReplica();

            List<Posto> postiOccupati = findPostiOccupati(daoFactory, replicaId);

            daoFactory.commitTransaction();

            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("biglietto", biglietto);
            request.setAttribute("postiOccupati", postiOccupati);
            request.setAttribute("viewUrl", "bigliettiManagement/insBigliettoView");

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

    public static void modify(HttpServletRequest request, HttpServletResponse response) {

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

            BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();
            Biglietto biglietto = bigliettoDAO.findByBigliettoId(Long.parseLong(request.getParameter("bigliettoId")));

            biglietto.setNome(request.getParameter("nome"));
            biglietto.setCognome(request.getParameter("cognome"));
            biglietto.setCategoria(request.getParameter("categoria"));
            biglietto.getPosto().setZona(request.getParameter("zona"));
            biglietto.getPosto().setFila(Integer.parseInt(request.getParameter("fila")));
            String palco = request.getParameter("palco");
            if(palco != null && !palco.isEmpty()) biglietto.getPosto().setPalco(Integer.parseInt(palco));
            biglietto.getPosto().setNumeroPosto(Integer.parseInt(request.getParameter("numero_posto")));

            try {

                bigliettoDAO.update(biglietto);

            } catch (Exception e) {
                applicationMessage = "Spettacolo già esistente";
                logger.log(Level.INFO, "Tentativo di inserimento di spettacolo già esistente");
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

    private static List<Posto> findPostiOccupati(DAOFactory daoFactory, Long replicaId) {
        List<Posto> postiOccupati = new ArrayList<Posto>();
        List<Biglietto> biglietti;

        BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();
        biglietti = bigliettoDAO.findByReplicaId(replicaId);
        for (Biglietto biglietto : biglietti) {
            postiOccupati.add(biglietto.getPosto());
        }

        return postiOccupati;
    }
}
