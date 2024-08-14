<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>

<%
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Gestione";
  String sidebarActiveLink = "Aggiungi spettacolo";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/gestione.css" type="text/css" media="screen">
    <title>Gestione</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <section id="insSpettacoloSection">
        <form name="insSpettacoloForm">

          <div class="field">
            <label for="titolo">Titolo</label>
            <input type="text" id="titolo" name="titolo"/>
          </div>
          <div class="field">
            <label for="genere">Genere</label>
            <input type="text" id="genere" name="genere"/>
          </div>
          <div class="field">
            <label for="compagnia">Compagnia</label>
            <input type="text" id="compagnia" name="compagnia"/>
          </div>
          <div class="field">
            <label for="data">Data di inizio</label>
            <input type="date" id="data" name="data"/>
          </div>
          <div class="field">
            <label for="ora">Ora di inizio</label>
            <input type="time" id="ora" name="ora"/>
          </div>
          <div class="field">
            <label for="descrizione">Descrizione</label>
            <textarea id="descrizione" name="descrizione"></textarea>
          </div>
          <div class="field">
            <label>&#160;</label>
            <input type="submit" class="button" value="Aggiungi"/>
            <input type="button" name="backButton" class="button" value="Annulla"/>
          </div>
        </form>
      </section>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
