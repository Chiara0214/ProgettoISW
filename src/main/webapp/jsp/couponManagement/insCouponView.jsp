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
    <link rel="stylesheet" href="css/coupon.css" type="text/css" media="screen">
    <title>Gestione</title>
    <script language="javascript">

      function goBack() {
        document.backForm.submit();
      }

      function checkDates(event) {
        const data_inizio = document.insCouponForm.dataInizio.value;
        const data_fine = document.insCouponForm.dataFine.value;
        if(data_inizio > data_fine) {
          alert('La data di inizio deve essere meno recente della data di fine');
          event.preventDefault();
        }
      }

      function mainOnLoadHandler() {
        document.insCouponForm.backButton.addEventListener("click", goBack);
        document.insCouponForm.submitButton.addEventListener("click", (event) => {checkDates(event)});
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <div class="container" style="margin-left: 250px;">
        <section id="insCouponSection">
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
              <input type="button" name="submitButton" class="button" value="Aggiungi"/>
              <input type="button" name="backButton" class="button" value="Annulla"/>
            </div>
            <input type="hidden" name="controllerAction" value="CouponManagement.insert"/>
          </form>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="CouponManagement.insView"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
