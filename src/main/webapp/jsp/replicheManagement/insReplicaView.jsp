<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>

<%
  int i = 0;
  DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  Spettacolo spettacolo = (Spettacolo) request.getAttribute("spettacolo");
  String menuActiveLink = "Gestione";
  String sidebarActiveLink = "Aggiungi spettacolo";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/repliche.css" type="text/css" media="screen">
    <title>Gestione</title>
    <script language="javascript">

      function goBack() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.insReplicaForm.backButton.addEventListener("click", goBack);
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <div class="container" style="margin-left: 250px;">
        <section id="insReplicaSection">
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
            <input type="hidden" name="spettacoloId" value="<%=spettacolo.getIdSpettacolo()%>"/>
            <input type="hidden" name="controllerAction" value="ReplicheManagement.insertReplica"/>
          </form>
        </section>
        <section class="lista-orari">
          <% if (spettacolo.getRepliche() != null) {
            for (i = 0; i < spettacolo.getRepliche().size(); i++) {%>
          <p><%=df.format(spettacolo.getRepliche(i).getInizio())%></p>
          <form name="deleteForm" method="post" action="Dispatcher">
            <input type="hidden" name="replicaId" value="<%=spettacolo.getRepliche(i).getIdReplica()%>"/>
            <input type="hidden" name="controllerAction" value="ReplicheManagement.delete"/>
            <input type="submit" name="submitButton" class="button" value="Elimina"/>
          </form>
          <%}}%>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
