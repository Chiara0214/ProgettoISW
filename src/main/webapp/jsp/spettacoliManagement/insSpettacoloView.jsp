<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>

<%
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Gestione";
  String sidebarActiveLink = "Aggiungi spettacolo";
  Spettacolo spettacolo = (Spettacolo) request.getAttribute("spettacolo");
  String action=(spettacolo != null) ? "modify" : "insert";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/spettacoli.css" type="text/css" media="screen">
    <title>Gestione</title>
    <script language="javascript">
      var status="<%=action%>";

      function submitSpettacolo() {
        var f;
        f = document.insSpettacoloForm;
        f.controllerAction.value = "SpettacoliManagement."+status;
      }

      function goBack() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.insSpettacoloForm.addEventListener("submit", submitSpettacolo);
        document.insSpettacoloForm.backButton.addEventListener("click", goBack);
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main style="background-color: #ffe7cb;">
      <%@include file="/include/sidebar.inc"%>
      <div class="container" style="margin-left: 250px;">
        <section id="insSpettacoloSection">
          <form name="insSpettacoloForm" id="insSpettacoloForm" action="Dispatcher" method="post">
            <div class="field">
              <label for="titolo">Titolo</label>
              <input type="text" id="titolo" name="titolo" value="<%=(action.equals("modify")) ? spettacolo.getNome() : ""%>" required/>
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
              <input type="text" id="compagnia" name="compagnia" value="<%=(action.equals("modify")) ? spettacolo.getCompagnia() : ""%>" required/>
            </div>
            <div class="field">
              <label for="descrizione">Descrizione</label>
              <textarea id="descrizione" name="descrizione"><%=(action.equals("modify")) ? spettacolo.getDescrizione() : ""%></textarea>
            </div>
            <div class="field">
              <label>&#160;</label>
              <input type="submit" name="submitButton" class="button" value="<%=(action.equals("modify")) ? "Continua" : "Aggiungi"%>"/>
              <input type="button" name="backButton" class="button" value="Annulla"/>
            </div>
            <%if (action.equals("modify")) {%>
            <input type="hidden" name="spettacoloId" value="<%=spettacolo.getIdSpettacolo()%>"/>
            <%}%>
            <input type="hidden" name="controllerAction"/>
          </form>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
