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
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%if (loggedOn) {%>
      Benvenuto <%=loggedUser.getNome()%> <%=loggedUser.getCognome()%>!<br/>
      Clicca sulla voce "Rubrica" del men&ugrave; per gestire i tuoi contatti.
      <%} else {%>
      Benvenuto.
      Fai il logon per gestire la tua rubrica.
      <%}%>
    </main>
    <%@include file="/include/footer.inc"%>
</html>
