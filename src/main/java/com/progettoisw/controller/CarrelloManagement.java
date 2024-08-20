package com.progettoisw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progettoisw.model.dao.DAOFactory;
import com.progettoisw.model.dao.ReplicaDAO;
import com.progettoisw.model.dao.UtenteDAO;
import com.progettoisw.model.dao.BigliettoDAO;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.*;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarrelloManagement {

    private CarrelloManagement() {
    }

    public static void view(HttpServletRequest request, HttpServletResponse response) {

        DAOFactory sessionDAOFactory = null;
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
        Carrello shoppingCart = null;
        String applicationMessage = null;

        int i = 0;

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
                shoppingCart = objectMapper.readValue(carrello, Carrello.class);
                System.out.println("Carrello: " + shoppingCart.getBiglietti());

            } catch (Exception e) {
                e.printStackTrace();
            }


            BigliettoDAO bigliettoDAO = daoFactory.getBigliettoDAO();
            ReplicaDAO replicaDAO = daoFactory.getReplicaDAO();

            for(i = 0; i < shoppingCart.getBiglietti().size(); i++){
                Long replicaId = shoppingCart.getBiglietti().get(i).getReplica().getIdReplica();
                Replica replica = replicaDAO.findByReplicaId(replicaId);

                Posto posto = shoppingCart.getBiglietti().get(i).getPosto();

                try {

                    bigliettoDAO.create(
                            replica,
                            loggedUser,
                            shoppingCart.getBiglietti().get(i).getNome(),
                            shoppingCart.getBiglietti().get(i).getCognome(),
                            shoppingCart.getBiglietti().get(i).getCategoria(),
                            posto
                    );


                } catch (DuplicatedObjectException e) {
                    applicationMessage = "Biglietto già esistente";
                    logger.log(Level.INFO, "Tentativo di inserimento di un biglietto già esistente");
                }

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

}
