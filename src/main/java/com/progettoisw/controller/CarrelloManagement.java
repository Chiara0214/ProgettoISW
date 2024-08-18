package com.progettoisw.controller;

import com.progettoisw.model.dao.BigliettoDAO;
import com.progettoisw.model.dao.DAOFactory;
import com.progettoisw.model.dao.ReplicaDAO;
import com.progettoisw.model.dao.UtenteDAO;
import com.progettoisw.model.mo.*;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarrelloManagement {

    private CarrelloManagement() {
    }

    public static void view(HttpServletRequest request, HttpServletResponse response) {

        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try {

            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "carrelloManagement/view");

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

            String carrello = request.getParameter("carrello");
            System.out.println(carrello);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Carrello shoppingCart = objectMapper.readValue(carrello, Carrello.class);
                System.out.println("Carrello: " + shoppingCart.getBiglietti());

            } catch (Exception e) {
                e.printStackTrace();
            }

            /* --------------- */

            /*BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();

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
            }*/

            /* --------------- */

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
}
