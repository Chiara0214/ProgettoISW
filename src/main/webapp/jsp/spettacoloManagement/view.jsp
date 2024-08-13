<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.util.List" %>

<%
  int i = 0;
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Spettacoli";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/spettacolo.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title>Spettacoli</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>

    </main>
    <%@include file="/include/footer.inc"%>
</html>
