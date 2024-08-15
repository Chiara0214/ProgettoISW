<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>

<%
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Gestione";
  String sidebarActiveLink = "Aggiungi coupon";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/gestioneSpettacoli.css" type="text/css" media="screen">
    <title>Gestione</title>
    <script language="javascript">

      function goBack() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.insSpettacoloForm.backButton.addEventListener("click", goBack);
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <div class="container" style="margin-left: 250px;">
        <section id="insSpettacoloSection">
          <form name="insCouponForm" id="insCouponForm" action="Dispatcher" method="post">
            <div class="field">
              <label for="sconto">Sconto</label>
              <input type="number" min="5" max="100" step="5" id="sconto" name="sconto" required/>
            </div>
            <div class="field">
              <label for="genere">Genere</label>
              <select id="genere" name="genere" form="insCouponForm" required>
                <option value="tutti" selected>Tutti</option>
                <option value="prosa">Prosa</option>
                <option value="opera">Opera</option>
                <option value="danza">Danza</option>
                <option value="concerti">Concerti</option>
                <option value="altro">Altro</option>
              </select>
            </div>
            <div class="field">
              <label for="dataInizio">Data inizio</label>
              <input type="date" id="dataInizio" name="dataInizio" required/>
            </div>
            <div class="field">
              <label for="dataFine">Data fine</label>
              <input type="date" id="dataFine" name="dataFine" required/>
            </div>
            <div class="field">
              <label>&#160;</label>
              <input type="submit" name="submitButton" class="button" value="Aggiungi"/>
              <input type="button" name="backButton" class="button" value="Annulla"/>
            </div>
            <input type="hidden" name="controllerAction" value="GestioneManagement.insertCoupon"/>
          </form>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="GestioneManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
