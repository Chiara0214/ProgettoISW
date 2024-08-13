<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.util.List" %>

<%
  int i = 0;
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
    <main>
      <section class="spettacoli-container">
        <%for (i = 0; i < spettacoli.size(); i++) {%>
        <article class="spettacolo" id="spettacolo">
          <a href="Dispatcher?controllerAction=SpettacoloManagement.view&spettacoloid=<%= spettacoli.get(i).getIdSpettacolo()%>">
            <img src="images/la-bottega-del-caffe.jpg" alt="La bottega del caffè">
            <section class="spettacolo-details">
              <h1><%= spettacoli.get(i).getNome()%></h1>
              <h2><%= spettacoli.get(i).getGenere()%></h2>
              <p><%= spettacoli.get(i).getCompagnia()%></p>
            </section>
          </a>
        </article>
        <%}%>
      </section>
    </main>
    <%@include file="/include/footer.inc"%>
</html>
