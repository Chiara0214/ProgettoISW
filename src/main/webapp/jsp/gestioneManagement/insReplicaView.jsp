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
    <script language="javascript">

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <section id="insSpettacoloSection">
        <form name="insReplicaForm" action="Dispatcher" method="post">
          <div class="field">
            <label for="data">Data di inizio</label>
            <input type="date" id="data" name="data"/>
          </div>
          <div class="field">
            <label for="ora">Ora di inizio</label>
            <input type="time" id="ora" name="ora"/>
          </div>
          <div class="field">
            <label>&#160;</label>
            <input type="submit" name="submitButton" class="button" value="Aggiungi"/>
            <input type="button" name="backButton" class="button" value="Annulla"/>
          </div>
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </section>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
