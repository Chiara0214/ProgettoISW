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
    <link rel="stylesheet" href="css/gestioneSpettacoli.css" type="text/css" media="screen">
    <title>Gestione</title>
    <script language="javascript">

      function insertSpettacolo() {
        document.insSpettacoloForm.submit();
      }

      function goBack() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.insSpettacoloForm.backButton.addEventListener("click", goBack);
        document.insSpettacoloForm.submitButton.addEventListener("click", insertSpettacolo);
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <section id="insSpettacoloSection">
        <form name="insSpettacoloForm" id="insSpettacoloForm" action="Dispatcher" method="post">
          <div class="field">
            <label for="titolo">Titolo</label>
            <input type="text" id="titolo" name="titolo" required/>
          </div>
          <div class="field">
            <label for="genere">Genere</label>
            <select id="genere" name="genere" form="insSpettacoloForm" required>
              <option value="prosa" selected>Prosa</option>
              <option value="opera">Opera</option>
              <option value="danza">Danza</option>
              <option value="concerti">Concerti</option>
              <option value="altro">Altro</option>
            </select>
          </div>
          <div class="field">
            <label for="compagnia">Compagnia</label>
            <input type="text" id="compagnia" name="compagnia" required/>
          </div>
          <div class="field">
            <label for="descrizione">Descrizione</label>
            <textarea id="descrizione" name="descrizione"></textarea>
          </div>
          <div class="field">
            <label>&#160;</label>
            <input type="submit" name="submitButton" class="button" value="Aggiungi"/>
            <input type="button" name="backButton" class="button" value="Annulla"/>
          </div>
          <input type="hidden" name="controllerAction" value="GestioneManagement.insert"/>
        </form>
      </section>
      <form name="backForm" method="post" action="Dispatcher">
        <input type="hidden" name="controllerAction" value="GestioneManagement.view"/>
      </form>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
