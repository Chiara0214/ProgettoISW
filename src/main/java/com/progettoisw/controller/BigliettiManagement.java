package com.progettoisw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progettoisw.model.dao.*;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.*;
import com.progettoisw.services.config.Configuration;
import com.progettoisw.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

            /* Se sono nella pagina di gestione visualizzo tutti i biglietti, altrimenti solo quelli dell'utente */
            boolean gestione = false;

            String s = request.getParameter("gestione"); //vedi sidebar.inc
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
            daoFactory.commitTransaction();

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
        Replica replica;
        String couponsJSON = "";

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

            String replicaId = request.getParameter("replicaId");
            replica = replicaDAO.findByReplicaIdWithSpettacolo(Long.parseLong(replicaId));

            List<Posto> postiOccupati = findPostiOccupati(daoFactory, Long.parseLong(replicaId));

            CouponDAO couponDAO = daoFactory.getCouponDAO();
            List<Coupon> coupons = couponDAO.findAllCouponsWithUsers();

            /* Converto la lista di coupon in JSON per utilizzarla poi in JavaScript */
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                couponsJSON = objectMapper.writeValueAsString(coupons);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("replica", replica);
            request.setAttribute("coupons", couponsJSON);
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
        String applicationMessage = "Biglietto acquistato";

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
            Replica replica = replicaDAO.findByReplicaIdWithSpettacolo(Long.parseLong(replicaId));

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


            } catch (DuplicatedObjectException e) {
                applicationMessage = "Biglietto già esistente";
                logger.log(Level.INFO, "Tentativo di inserimento di un biglietto già esistente");
            }

            String couponId = request.getParameter("idCoupon");
            UsaCouponDAO usaCouponDAO = daoFactory.getUsaCouponDAO();

            /* Aggiorno la lista di coupon utilizzati dall'utente */
            if(!couponId.isEmpty()){
                try {

                    usaCouponDAO.create(loggedUser.getIdUtente(), Long.parseLong(couponId));

                } catch (DuplicatedObjectException e) {
                    applicationMessage = "Coupon già utilizzato";
                    logger.log(Level.INFO, "Tentativo di inserimento di un UsaCoupon già esistente");
                }
            }

            SpettacoloDAO spettacoloDAO = daoFactory.getSpettacoloDAO();
            Spettacolo spettacolo = spettacoloDAO.findBySpettacoloIdWithDates(replica.getSpettacolo().getIdSpettacolo());

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("spettacolo", spettacolo);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "spettacoliManagement/viewSpettacolo");

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

            ReplicaDAO replicaDAO = daoFactory.getReplicaDAO();
            Replica replica = replicaDAO.findByReplicaIdWithSpettacolo(replicaId);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("biglietto", biglietto);
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

    public static void modify(HttpServletRequest request, HttpServletResponse response) {

        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        List<Biglietto> biglietti;
        String applicationMessage = "Biglietto modificato";

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
            if(palco != null && !palco.isEmpty() && !palco.equals("null")) {
                biglietto.getPosto().setPalco(Integer.parseInt(palco));
            }
            biglietto.getPosto().setNumeroPosto(Integer.parseInt(request.getParameter("numero_posto")));

            try {

                bigliettoDAO.update(biglietto);

            } catch (DuplicatedObjectException e) {
                applicationMessage = "La persona specificata ha già un biglietto associato per questa replica";
                logger.log(Level.INFO, "Tentativo di modifica in un biglietto già esistente");
            }

            biglietti = bigliettoDAO.findBigliettiByUtente(loggedUser);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("gestione", false);
            request.setAttribute("biglietti", biglietti);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "bigliettiManagement/view");

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
