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
    <script language="javascript">

      function goBack() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.insBigliettoForm.backButton.addEventListener("click", goBack);
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <div class="container">
        <section id="insBigliettoSection">
          <form name="insBigliettoForm" id="insBigliettoForm" action="Dispatcher" method="post">
            <div class="field">
              <label for="nome">Nome intestato</label>
              <input type="text" id="nome" name="nome" required/>
            </div>
            <div class="field">
              <label for="cognome">Cognome intestato</label>
              <input type="text" id="cognome" name="cognome" required/>
            </div>
            <div class="field">
              <label for="categoria">Categoria</label>
              <select id="categoria" name="categoria" form="insBigliettoForm" required>
                <option value="intero" selected>Intero</option>
                <option value="ridotto under 20">Ridotto under 20</option>
                <option value="ridotto under 30">Ridotto under 30</option>
                <option value="ridotto over 65">Ridotto over 65</option>
              </select>
            </div>
            <input type="submit" name="submitButton" class="button" value="Acquista"/>
            <input type="button" name="backButton" class="button" value="Annulla"/>
            <input type="hidden" name="replicaId" value="<%=replica.getIdReplica()%>"/>
            <input type="hidden" name="zona" value="palco laterale"/>
            <input type="hidden" name="fila" value="1"/>
            <input type="hidden" name="palco" value="12"/>
            <input type="hidden" name="numero_posto" value="3"/>
            <input type="hidden" name="controllerAction" value="BigliettiManagement.insert"/>
          </form>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
