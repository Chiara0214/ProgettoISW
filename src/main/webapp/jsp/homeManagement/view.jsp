<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Home";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <title>Teatro</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <section class="copertina">
        <img src="images/teatro_filarmonico.jpg" alt="Teatro Filarmonico">
        <div id="barra-ricerca">
          <form name="searchForm" method="post" action="Dispatcher">
            <input type="text" placeholder="Titolo spettacolo" id="titolo" name="titolo">
            <input type="text" placeholder="Genere" id="genere" name="genere">
            <input type="date" placeholder="Da" id="data-da" name="data-da">
            <input type="date" placeholder="a" id="data-a" name="data-a">
            <input type="hidden" name="controllerAction" value="SpettacoliManagement.view"/>
            <input type="submit" value="Cerca">
          </form>
        </div>

      </section>
      <ol class="galleria">
        <li>
          <a href="Dispatcher?controllerAction=HomeManagement.view">
            <img src="images/la-bottega-del-caffe.jpg" alt="Prosa">
            <h1>Prosa</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=HomeManagement.view">
            <img src="images/don-giovanni.png" alt="Opera">
            <h1>Opera</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=HomeManagement.view">
            <img src="images/lago-dei-cigni.jpg" alt="Danza">
            <h1>Danza</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=HomeManagement.view">
            <img src="images/le-quattro-stagioni-vivaldi.jpg" alt="Concerti">
            <h1>Concerti</h1>
          </a>
        </li>
      </ol>
      <section class="descrizione">
        <h2>Informazioni</h2>
        <%if (loggedOn) {%>
        Benvenuto <%=loggedUser.getNome()%> <%=loggedUser.getCognome()%>!<br/>
        Clicca sulla voce "Rubrica" del men&ugrave; per gestire i tuoi contatti.
        <%} else {%>
        Benvenuto.
        Fai il logon per acquistare biglietti.
        <%}%>
      </section>
    </main>
    <%@include file="/include/footer.inc"%>
</html>
