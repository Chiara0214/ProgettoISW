<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.progettoisw.model.mo.Replica" %>

<%
  Date inizio;
  DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  List<Spettacolo> spettacoli = (List<Spettacolo>) request.getAttribute("spettacoli");
  String menuActiveLink = "Spettacoli";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/spettacoli.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title>Spettacoli</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main style="background-color: #8a3b3b;">
      <%if (spettacoli.isEmpty()) {%><h2 id="not-found">Nessuno spettacolo trovato</h2><%}%>
      <%-- Lista di spettacoli --%>
      <div class="spettacoli-container">
        <%for (Spettacolo spettacolo: spettacoli) {%>
        <article class="spettacolo" id="spettacolo">
          <a href="Dispatcher?controllerAction=SpettacoliManagement.viewSpettacolo&selectedSpettacolo=<%=spettacolo.getIdSpettacolo()%>">
            <img src="images/copertine/<%=spettacolo.getImmagine()%>.jpg" alt="Copertina">
            <section class="spettacolo-details">
              <h1><%= spettacolo.getNome()%></h1>
              <h2><%= spettacolo.getGenere()%></h2>
              <p><%= spettacolo.getCompagnia()%></p>
              <%-- Date dello spettacolo --%>
              <section class="date-spettacolo">
                <%for (Replica replica: spettacolo.getRepliche()) {%>
                <%
                  inizio = replica.getInizio();
                %>
                <p><%=inizio != null ? df.format(inizio) : "Nessuna data disponibile"%></p>
                <%}%>
              </section>
            </section>
          </a>
        </article>
        <%}%>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
