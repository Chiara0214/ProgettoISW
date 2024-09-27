<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.progettoisw.model.mo.Replica" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.Instant" %>

<%
  DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  Spettacolo spettacolo = (Spettacolo) request.getAttribute("spettacolo");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Spettacoli";
  List<Replica> replicheDisponibili = null;
  if(spettacolo != null) {
    replicheDisponibili = spettacolo.getRepliche().stream().filter(replica -> replica.getInizio().toInstant().isAfter(Instant.now())).toList();
  }
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/spettacoli.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title><%= spettacolo != null ? spettacolo.getNome() : "Spettacolo non trovato"%></title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main style="background-color: #fbcda2;">
      <% if (spettacolo == null) {%><h2 style="margin: 20px;">Spettacolo non trovato</h2><%} else {%>
      <%-- Sidebar con le informazioni dello spettacolo --%>
      <section class="spettacolo-info">
        <img src="images/copertine/<%=spettacolo.getImmagine()%>.jpg" alt="Copertina">
        <p><span>Compagnia teatrale:</span> <%=spettacolo.getCompagnia()%></p>
        <p><span>Genere:</span> <%=spettacolo.getGenere()%></p>
        <div class="date-container">
          <h2>Date:</h2>
          <%if(!spettacolo.getRepliche().isEmpty()) {
            for (Replica replica: spettacolo.getRepliche()) {%>
          <p><%=df.format(replica.getInizio())%></p>
          <%}} else {%>
          <p>Nessuna data disponibile</p>
          <%}%>
        </div>
      </section>
      <%-- Descrizione e acquisto biglietti --%>
      <div class="spettacolo-container">
      <section class="spettacolo-content">
        <div class="spettacolo-header">
          <h1><%=spettacolo.getNome()%></h1>
        </div>
        <p><%=spettacolo.getDescrizione().replace("\n", "<br>")%></p>
      </section>
        <%-- Se ci sono repliche disponibili mostro il form per l'acquisto dei biglietti --%>
        <% if(!replicheDisponibili.isEmpty()) {%>
        <section class="acquisto-biglietto">
          <header class="acquisto-header">
            <h2>Acquista biglietto</h2>
          </header>
          <section class="acquisto-content">
            <label for="replicaId">Seleziona una data:</label>
            <select name="replicaId" id="replicaId" form="buyBigliettoForm" required>
              <%for (Replica replica: replicheDisponibili) {%>
              <option value="<%=replica.getIdReplica()%>"><%=df.format(replica.getInizio())%></option>
              <%}%>
            </select>
            <form name="buyBigliettoForm" id="buyBigliettoForm" method="post" action="Dispatcher">
              <input type="hidden" name="spettacoloId" value="<%=spettacolo.getIdSpettacolo()%>"/> <%-- Per il redirect allo spettacolo dopo la registrazione --%>
              <input type="hidden" name="controllerAction" value="<%=loggedOn ? "BigliettiManagement.insView" : "HomeManagement.registrazioneView"%>"/>
              <input type="submit" name="submitButton" class="button" value="Procedi all'acquisto"/>
            </form>
          </section>
        </section>
        <%}%>
        <%-- Pulsanti Elimina spettacolo e Modifica spettacolo per l'amministratore --%>
        <%if(loggedOn && loggedUser.getPrivilegi()){%>
        <div class="button-container">
          <form name="deleteForm" method="post" action="Dispatcher">
            <input type="hidden" name="spettacoloId" value="<%=spettacolo.getIdSpettacolo()%>"/>
            <input type="hidden" name="controllerAction" value="SpettacoliManagement.delete"/>
            <input type="submit" name="submitButton" class="button" value="Elimina spettacolo"/>
          </form>
          <form name="modifyForm" method="post" action="Dispatcher">
            <input type="hidden" name="spettacoloId" value="<%=spettacolo.getIdSpettacolo()%>"/>
            <input type="hidden" name="controllerAction" value="SpettacoliManagement.modifyView"/>
            <input type="submit" name="submitButton" class="button" value="Modifica spettacolo"/>
          </form>
        </div>
        <%}}%>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
