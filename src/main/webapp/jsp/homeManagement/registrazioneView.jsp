<%@ page import="com.progettoisw.model.mo.Utente" %>
<%@page session="false"%>

<%
  boolean loggedOn = false;
  Utente loggedUser = null;
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Registrati";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/home.css" type="text/css" media="screen">
    <title>Registrazione</title>
    <script language="javascript">

      function goback() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.registrazioneForm.backButton.addEventListener("click", goback);
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main style="background-color: #ffe7cb;">
        <section id="registrazioneSection">
          <form name="registrazioneForm" id="registrazioneForm" action="Dispatcher" method="post">
            <div class="field">
              <label for="nome">Nome</label>
              <input type="text" id="nome" name="nome" pattern="[A-Za-z]+" required/>
            </div>
            <div class="field">
              <label for="cognome">Cognome</label>
              <input type="text" id="cognome" name="cognome" pattern="[A-Za-z]+" required/>
            </div>
            <div class="field">
              <label for="email">Indirizzo e-mail</label>
              <input type="email" id="email" name="email" required/>
            </div>
            <div class="field">
              <label for="telefono">Numero di telefono</label>
              <input type="tel" id="telefono" name="telefono" pattern="[0-9]+" maxlength="10" required/>
            </div>
            <div class="field">
              <label for="password">Password</label>
              <input type="password" id="password" name="password" required/>
            </div>
            <div class="field">
              <label>&#160;</label>
              <input type="hidden" name="controllerAction" value="HomeManagement.registrazione"/>
              <input type="submit" name="submitButton" class="button" value="Conferma"/>
              <input type="button" name="backButton" class="button" value="Annulla"/>
            </div>
          </form>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
