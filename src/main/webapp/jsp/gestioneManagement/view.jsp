<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>

<%
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Gestione";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <title>Gestione</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>

    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
