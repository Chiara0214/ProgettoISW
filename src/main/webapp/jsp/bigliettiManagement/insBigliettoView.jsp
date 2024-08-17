<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Replica" %>
<%@ page import="com.progettoisw.model.mo.Biglietto" %>
<%@ page import="com.progettoisw.model.mo.Posto" %>
<%@ page import="java.util.List" %>

<%
  int fila_index=0;
  int posto_index=0;
  int palco_index=0;
  String zona=null;
  Posto posto = new Posto();
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Spettacoli";
  Replica replica = (Replica) request.getAttribute("replica");
  Biglietto biglietto = (Biglietto) request.getAttribute("biglietto");
  List<Posto> postiOccupati = (List<Posto>) request.getAttribute("postiOccupati");
  Posto currentPosto = new Posto();
  if(biglietto != null) {
    currentPosto = biglietto.getPosto();
  }
  String action=(biglietto != null) ? "modify" : "insert";
%>

<!DOCTYPE html>1
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/biglietti.css" type="text/css" media="screen">
    <title>Acquista biglietto</title>
    <script language="javascript">
      var status="<%=action%>";

      function submitBiglietto() {
        var f;
        f = document.insBigliettoForm;
        f.controllerAction.value = "BigliettiManagement."+status;
      }

      function goBack() {
        document.backForm.submit();
      }

      function changeSeat(selectedDiv, zona, fila, palco, posto) {

        if(selectedDiv.classList.contains('libero')){
          document.insBigliettoForm.zona.value = zona;
          document.insBigliettoForm.fila.value = fila;
          document.insBigliettoForm.palco.value = palco;
          document.insBigliettoForm.numero_posto.value = posto;

          Array.from(document.querySelectorAll('.libero')).forEach(
                  (div) => div.classList.remove('postoSelezionato')
          );

          selectedDiv.classList.add("postoSelezionato");

          console.log("selezionato zona " + zona + ", fila " + fila + ", palco " + palco + ", posto " + posto);
        }
      }

      function mainOnLoadHandler() {
        document.insBigliettoForm.addEventListener("submit", submitBiglietto);
        document.insBigliettoForm.backButton.addEventListener("click", goBack);

        Array.from(document.querySelectorAll(".occupato .infoPosto")).forEach(
                (divOccupato) => {
                  divOccupato.innerHTML += "<span style='display: inline-block; color:red;'>Occupato</span>";
                }
        );
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
              <input type="text" id="nome" name="nome" value="<%=(action.equals("modify")) ? biglietto.getNome() : ""%>" required/>
            </div>
            <div class="field">
              <label for="cognome">Cognome intestato</label>
              <input type="text" id="cognome" name="cognome" value="<%=(action.equals("modify")) ? biglietto.getCognome() : ""%>" required/>
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
            <%if(action.equals("insert")) {%><input type="hidden" name="replicaId" value="<%=replica.getIdReplica()%>"/><%}%>
            <%if(action.equals("modify")) {%><input type="hidden" name="bigliettoId" value="<%=biglietto.getIdBiglietto()%>"/><%}%>
            <input type="hidden" name="zona" value="<%=(action.equals("modify")) ? biglietto.getPosto().getZona() : ""%>"/>
            <input type="hidden" name="fila" value="<%=(action.equals("modify")) ? biglietto.getPosto().getFila() : ""%>"/>
            <input type="hidden" name="palco" value="<%=(action.equals("modify")) ? biglietto.getPosto().getPalco() : ""%>"/>
            <input type="hidden" name="numero_posto" value="<%=(action.equals("modify")) ? biglietto.getPosto().getNumeroPosto() : ""%>"/>
            <input type="hidden" name="controllerAction"/>
          </form>
        </section>
        <%@include file="/include/mappaPosti.inc"%>
        <div class="button-container">
        <input type="submit" name="submitButton" form="insBigliettoForm" class="button" value="<%=(action.equals("modify")) ? "Conferma" : "Acquista"%>"/>
        <input type="button" name="backButton" form="insBigliettoForm" class="button" value="Annulla"/>
        </div>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>