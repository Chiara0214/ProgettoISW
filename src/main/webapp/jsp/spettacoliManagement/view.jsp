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
    <%@include file="/include/htmlHead.inc"%>
    <title>Spettacoli</title>

  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%for (i = 0; i < spettacoli.size(); i++) {%>
      <p><%= spettacoli.get(i).getNome()%></p>
      <%}%>

    </main>
    <%@include file="/include/footer.inc"%>
</html>
