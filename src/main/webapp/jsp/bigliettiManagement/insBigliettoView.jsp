<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Replica" %>

<%
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Spettacoli";
  Replica replica = (Replica) request.getAttribute("replica");
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/biglietti.css" type="text/css" media="screen">
    <title>Acquista biglietto</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <div class="container">
        <section id="insBigliettoSection">
          <p><%=replica.getIdReplica()%></p>
        </section>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
