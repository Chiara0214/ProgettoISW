package com.progettoisw.model.dao.CookieImpl;

import com.progettoisw.model.dao.UtenteDAO;
import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Utente;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UtenteDAOCookieImpl implements UtenteDAO {

  HttpServletRequest request;
  HttpServletResponse response;

  public UtenteDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  @Override
  public Utente create(
          Long idUtente,
          String nome,
          String cognome,
          String email,
          String telefono,
          String password,
          Boolean privilegi) throws DuplicatedObjectException {

    Utente loggedUser = new Utente();
    loggedUser.setIdUtente(idUtente);
    loggedUser.setNome(nome);
    loggedUser.setCognome(cognome);
    loggedUser.setPrivilegi(privilegi);

    Cookie cookie;
    cookie = new Cookie("loggedUser", encode(loggedUser));
    cookie.setPath("/");
    response.addCookie(cookie);

    return loggedUser;
  }

  @Override
  public void update(Utente loggedUser) {
    Cookie cookie;
    cookie = new Cookie("loggedUser", encode(loggedUser));
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  @Override
  public void delete(Utente loggedUser) {
    Cookie cookie;
    cookie = new Cookie("loggedUser", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  @Override
  public Utente findLoggedUser() {

    Cookie[] cookies = request.getCookies();
    Utente loggedUser = null;

    if (cookies != null) {
      for (int i = 0; i < cookies.length && loggedUser == null; i++) {
        if (cookies[i].getName().equals("loggedUser")) {
          loggedUser = decode(cookies[i].getValue());
        }
      }
    }

    return loggedUser;

  }

  @Override
  public Utente findByUtenteId(Long utenteId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Utente findByEmail(String email) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  private String encode(Utente loggedUser) {

    String encodedLoggedUser;
    encodedLoggedUser = loggedUser.getIdUtente() + "#" + loggedUser.getNome() + "#" + loggedUser.getCognome() + "#" + loggedUser.getPrivilegi();
    return encodedLoggedUser;

  }

  private Utente decode(String encodedLoggedUser) {

    Utente loggedUser = new Utente();

    String[] values = encodedLoggedUser.split("#");

    loggedUser.setIdUtente(Long.parseLong(values[0]));
    loggedUser.setNome(values[1]);
    loggedUser.setCognome(values[2]);
    loggedUser.setPrivilegi(Boolean.parseBoolean(values[3]));

    return loggedUser;

  }
  
}

