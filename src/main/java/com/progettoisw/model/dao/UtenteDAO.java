package com.progettoisw.model.dao;

import com.progettoisw.model.dao.exception.DuplicatedObjectException;
import com.progettoisw.model.mo.Biglietto;
import com.progettoisw.model.mo.Utente;


public interface UtenteDAO {
    public Utente create(
            Long idUtente,
            String nome,
            String cognome,
            String email,
            String telefono,
            String password,
            Boolean privilegi
    ) throws DuplicatedObjectException;

    public void update(Utente utente);

    public void delete(Utente utente);

    public Utente findLoggedUser();

    public Utente findByUtenteId(Long utenteId);

    public Utente findByEmail(String email);
}
