Progetto Ingegneria dei Sistemi Web

Analisi dei requisiti:
Si vuole realizzare un applicativo di basi di dati relazionale che memorizza un sistema di acquisto biglietti per un teatro.
Il teatro può ospitare più spettacoli, ciascuno dei quali è identificato da un numero univoco, un nome, un genere tra prosa, danza, opera, concerti e altro per spettacoli che non rientrano nelle categorie precedenti, nome della compagnia teatrale.
Gli spettacoli possono essere ripetuti in date o orari diversi e ogni utente può acquistare uno o più biglietti per una determinata replica di interesse.
Ogni biglietto sarà identificato da un numero identificativo univoco, categoria (ridotto over 65, ridotto under 20, ridotto under 30, intero) e l'identificativo del posto a sedere, che è composto da zona (platea, palco laterale, palco centrale, galleria o loggione) con numero rispettivo di fila, numero del palco se la zona è palco laterale o centrale, e numero identificativo del posto.
In particolare: la platea ha 15 file con 20 posti (numerati da 1 a 20) ciascuna, i palchi laterali sono 36, ognuno con due file con 3 posti numerati da 1 a 3 ciascuna, i palchi centrali sono 24 con due file, ciascuna con 4 posti (numerati da 1 a 4), la galleria ha 2 file da 70 posti ciascuna (numerati da 1 a 70), il loggione ha due file da 50 posti ciascuna, numerati da 1 a 50.
Ogni utente sarà invece identificato da un ID numerico univoco, nome, cognome, indirizzo e-mail e numero di telefono, e un campo “privilegi” che indica se possiedono i privilegi da amministratore.
Ogni utente può usare all’acquisto un coupon, ognuno dei quali può scontare un biglietto del 10%, 15%, 20%, 30% o 50% e può essere valido per un determinato genere di spettacoli o per tutti. Il coupon ha una data di inizio validità e una data di scadenza.
Ogni utente può usare lo stesso coupon solo una volta.

Il diagramma ER comprenderà 5 entità forti (spettacolo, replica, biglietto, utente, coupon).


Funzionalità previste:
1) Visualizzare una lista completa degli spettacoli in programmazione.
2) Possibilità di cercare spettacoli in base al titolo, al genere e/o a un periodo temporale.
3) Visualizzare la descrizione e le informazioni di un singolo spettacolo.
4) Possibilità di acquistare uno o più biglietti (carrello).
5) Possibilità di annullare o modificare l'acquisto di un biglietto (posto, nome intestato).
6) Possibilità per gli utenti amministratori di aggiungere/rimuovere/modificare spettacoli.
8) Possibilità per gli amministratori di visualizzare i biglietti che sono stati comprati.
9) Possibilità per gli amministratori di creare buoni sconto che possono essere usati dagli utenti al momento dell'acquisto.

Schema ER:
![SchemaER](https://github.com/user-attachments/assets/cf2fdd64-c0bc-4653-b67b-92bb552520c1)

Schema Relazionale:
![SchemaRelazionale](https://github.com/user-attachments/assets/6357010e-eebe-42fa-8c76-4f6d019a5193)
