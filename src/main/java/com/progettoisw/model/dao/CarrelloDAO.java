package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Carrello;
import com.progettoisw.model.mo.Utente;

import java.util.List;


public interface CarrelloDAO {
    public Carrello create(
            Long carrello_id,
            Utente utente,
            List<Biglietto> biglietti
    );

    public void update(Carrello carrello);

    public void delete(Carrello carrello);

    public Carrello findByUtenteId(Long utenteId);
}
