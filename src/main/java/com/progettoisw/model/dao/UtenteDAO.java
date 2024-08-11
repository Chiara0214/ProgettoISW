package com.progettoisw.model.dao;

import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Utente;


public interface UtenteDAO {
    public Utente create(
            Long idUtente,
            Biglietto[] biglietti,
            String nome,
            String cognome,
            String email,
            String telefono,
            String password,
            Boolean privilegi
    );

    public void update(Utente utente);

    public void delete(Utente utente);

    public Utente findLoggedUser();

    public Utente findByUtenteId(Long utenteId);
}
