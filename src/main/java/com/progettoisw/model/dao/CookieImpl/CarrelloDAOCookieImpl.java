package com.progettoisw.model.dao.CookieImpl;

import com.progettoisw.model.dao.CarrelloDAO;
import com.progettoisw.model.dao.UtenteDAO;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Carrello;
import com.progettoisw.model.mo.Utente;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class CarrelloDAOCookieImpl implements CarrelloDAO {

  HttpServletRequest request;
  HttpServletResponse response;

  public CarrelloDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  @Override
  public Carrello create(
          Long carrello_id,
          Utente utente,
          List<Biglietto> biglietti) {

    Carrello carrello = new Carrello();
    carrello.setCarrelloId(carrello_id);
    carrello.setUtente(utente);
    carrello.setBiglietti(biglietti);

    Cookie cookie;
    cookie = new Cookie("carrello", encode(carrello));
    cookie.setPath("/");
    response.addCookie(cookie);

    return carrello;
  }

  @Override
  public void update(Carrello carrello) {
    Cookie cookie;
    cookie = new Cookie("carrello", encode(carrello));
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  @Override
  public void delete(Carrello carrello) {
    Cookie cookie;
    cookie = new Cookie("carrello", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  @Override
  public Carrello findByUtenteId(Long utenteId) {
    return null;
  }


  private String encode(Carrello carrello) {

    String encodedCarrello;
    encodedCarrello = carrello.getCarrelloId() + "#" + carrello.getUtente().getIdUtente();
    return encodedCarrello;

  }

  private Carrello decode(String encodedCarrello) {

    Carrello carrello = new Carrello();

    String[] values = encodedCarrello.split("#");

    carrello.setCarrelloId(Long.parseLong(values[0]));
    carrello.getUtente().setIdUtente(Long.parseLong(values[1]));

    return carrello;

  }
  
}

