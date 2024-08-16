<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.progettoisw.model.mo.Biglietto" %>

<%
  int i = 0;
  DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
  DateFormat oraFormat = new SimpleDateFormat("HH:mm");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  Boolean gestione = (Boolean) request.getAttribute("gestione");
  List<Biglietto> biglietti = (List<Biglietto>) request.getAttribute("biglietti");
  String menuActiveLink = "Gestione";
  String sidebarActiveLink = "Visualizza biglietti";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/biglietti.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title>Biglietti</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%if (gestione) {%>
      <%@include file="/include/sidebar.inc"%>
      <%}%>
      <div class="container"  style="<%=gestione ? "margin-left: 250px;" : ""%>">
        <section id="listaBiglietti">
          <%for (i = 0; i < biglietti.size(); i++) {%>
            <article class="biglietto">
              <a href="Dispatcher?controllerAction=SpettacoliManagement.viewSpettacolo&selectedSpettacolo=<%=biglietti.get(i).getReplica().getSpettacolo().getIdSpettacolo()%>">
              <h1>Biglietto n. <%=biglietti.get(i).getIdBiglietto()%></h1>
              <section class="dettagliBiglietto">
                <div class="bigliettoCampo"><h2>Acquirente:</h2><p><%=biglietti.get(i).getUtente().getNome()%> <%=biglietti.get(i).getUtente().getCognome()%></p></div>
                <div class="bigliettoCampo"><h2>Nome intestato:</h2><p><%=biglietti.get(i).getNome()%> <%=biglietti.get(i).getCognome()%></p></div>
                <div class="bigliettoCampo"><h2>Categoria:</h2><p><%=biglietti.get(i).getCategoria()%></p></div>
                <div class="bigliettoCampo"><h2>Spettacolo:</h2><p><%=biglietti.get(i).getReplica().getSpettacolo().getIdSpettacolo()%> - <%=biglietti.get(i).getReplica().getSpettacolo().getNome()%></p></div>
                <div class="bigliettoCampo"><h2>Data:</h2><p><%=dataFormat.format(biglietti.get(i).getReplica().getInizio())%></p></div>
                <div class="bigliettoCampo"><h2>Ora:</h2><p><%=oraFormat.format(biglietti.get(i).getReplica().getInizio())%></p></div>
                <div class="bigliettoCampo"><h2>Posto:</h2><p><%=biglietti.get(i).getPosto().getZona()%> <% if (biglietti.get(i).getPosto().getZona().startsWith("palco")){%><%=biglietti.get(i).getPosto().getPalco()%><%}%> - fila <%=biglietti.get(i).getPosto().getFila()%> - posto <%=biglietti.get(i).getPosto().getNumeroPosto()%></p></div>
              </section>
              <form name="modifyForm" method="post" action="Dispatcher">
                <input type="hidden" name="bigliettoId" value="<%=biglietti.get(i).getIdBiglietto()%>"/>
                <input type="hidden" name="controllerAction" value="BigliettiManagement.modifyView"/>
                <input type="submit" name="submitButton" class="button" value="Modifica"/>
              </form>
              </a>
            </article>
          <%}%>
        </section>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
