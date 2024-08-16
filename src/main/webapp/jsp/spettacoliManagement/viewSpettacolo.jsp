<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>

<%
  int i = 0;
  DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  Spettacolo spettacolo = (Spettacolo) request.getAttribute("spettacolo");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Spettacoli";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/spettacoli.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title><%=spettacolo.getNome()%></title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main class="clearfix" style="background-color: #fbcda2;">
      <section class="spettacolo-info">
        <img src="images/la-bottega-del-caffe.jpg" alt="La bottega del caffè">
        <p><span>Compagnia teatrale:</span> <%=spettacolo.getCompagnia()%></p>
        <p><span>Genere:</span> <%=spettacolo.getGenere()%></p>
        <div class="date-container">
          <h2>Date:</h2>
          <%for (i = 0; i < spettacolo.getRepliche().size(); i++) {%>
          <p><%=df.format(spettacolo.getRepliche(i).getInizio())%></p>
          <%}%>
        </div>
      </section>

      <div class="spettacolo-container">
      <section class="spettacolo-content">
        <h1><%=spettacolo.getNome()%></h1>
        <p><%=spettacolo.getDescrizione()%></p>
      </section>

        <section class="acquisto-biglietto">
          <header class="acquisto-header">
            <h2>Acquista biglietto</h2>
          </header>
          <section class="acquisto-content">
            <label for="replicaId">Seleziona una data:</label>
            <select name="replicaId" id="replicaId" form="buyBigliettoForm" required>
              <%for (i = 0; i < spettacolo.getRepliche().size(); i++) {%>
              <option value="<%=spettacolo.getRepliche(i).getIdReplica()%>"><%=df.format(spettacolo.getRepliche(i).getInizio())%></option>
              <%}%>
            </select>
            <form name="buyBigliettoForm" id="buyBigliettoForm" method="post" action="Dispatcher">
              <input type="hidden" name="controllerAction" value="BigliettiManagement.insView"/>
              <input type="submit" name="submitButton" class="button" value="Procedi all'acquisto"/>
            </form>
          </section>
        </section>

        <%if(loggedOn && loggedUser.getPrivilegi()){%>
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
        <%}%>

      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
