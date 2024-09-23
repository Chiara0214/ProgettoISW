<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.progettoisw.model.mo.Biglietto" %>

<%
  DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
  DateFormat oraFormat = new SimpleDateFormat("HH:mm");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  boolean gestione = (Boolean) request.getAttribute("gestione");
  List<Biglietto> biglietti = (List<Biglietto>) request.getAttribute("biglietti");
  String menuActiveLink;
  if(gestione) {
    menuActiveLink = "Gestione";
  } else {
    menuActiveLink = "I miei biglietti";
  }
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
        <%if (biglietti.isEmpty()) {%><h2 id="nessun-biglietto">Nessun biglietto acquistato</h2><%}%>
        <section id="listaBiglietti">
          <%for (Biglietto biglietto: biglietti) {%>
            <article class="biglietto">
              <a href="Dispatcher?controllerAction=SpettacoliManagement.viewSpettacolo&selectedSpettacolo=<%=biglietto.getReplica().getSpettacolo().getIdSpettacolo()%>">
              <h1>Biglietto n. <%=biglietto.getIdBiglietto()%></h1>
              <section class="dettagliBiglietto">
                <div class="bigliettoCampo"><h2>Acquirente:</h2><p><%=biglietto.getUtente().getNome()%> <%=biglietto.getUtente().getCognome()%></p></div>
                <div class="bigliettoCampo"><h2>Nome intestato:</h2><p><%=biglietto.getNome()%> <%=biglietto.getCognome()%></p></div>
                <div class="bigliettoCampo"><h2>Categoria:</h2><p><%=biglietto.getCategoria()%></p></div>
                <div class="bigliettoCampo"><h2>Spettacolo:</h2><p><%=biglietto.getReplica().getSpettacolo().getIdSpettacolo()%> - <%=biglietto.getReplica().getSpettacolo().getNome()%></p></div>
                <div class="bigliettoCampo"><h2>Data:</h2><p><%=dataFormat.format(biglietto.getReplica().getInizio())%></p></div>
                <div class="bigliettoCampo"><h2>Ora:</h2><p><%=oraFormat.format(biglietto.getReplica().getInizio())%></p></div>
                <div class="bigliettoCampo"><h2>Posto:</h2><p><%=biglietto.getPosto().getZona()%> <% if (biglietto.getPosto().getZona().startsWith("Palco")){%><%=biglietto.getPosto().getPalco()%><%}%> - Fila <%=biglietto.getPosto().getFila()%> - Posto <%=biglietto.getPosto().getNumeroPosto()%></p></div>
              </section>
              <%if (!gestione) {%>
              <div class="buttons-wrapper">
                <form name="modifyForm" method="post" action="Dispatcher">
                  <input type="hidden" name="bigliettoId" value="<%=biglietto.getIdBiglietto()%>"/>
                  <input type="hidden" name="controllerAction" value="BigliettiManagement.modifyView"/>
                  <input type="submit" name="submitButton" class="button" value="Modifica"/>
                </form>
                <form name="deleteForm" method="post" action="Dispatcher">
                  <input type="hidden" name="bigliettoId" value="<%=biglietto.getIdBiglietto()%>"/>
                  <input type="hidden" name="controllerAction" value="BigliettiManagement.delete"/>
                  <input type="submit" name="submitButton" class="button" value="Elimina"/>
                </form>
              </div>
              <%}%>
              </a>
            </article>
          <%}%>
        </section>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
