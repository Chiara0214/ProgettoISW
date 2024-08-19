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

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/biglietti.css" type="text/css" media="screen">
    <title>Acquista biglietto</title>
    <script language="javascript">
      var status="<%=action%>";


      function updateCart(event){
        if(!document.insBigliettoForm.nome.value || !document.insBigliettoForm.cognome.value || !document.insBigliettoForm.categoria.value || !document.insBigliettoForm.zona.value) {
          alert("Inserisci tutti i campi");
          event.preventDefault();
          return false;
        }

        const biglietto = {
          "idBiglietto":document.insBigliettoForm.id.value,
          "nome":document.insBigliettoForm.nome.value,
          "cognome":document.insBigliettoForm.cognome.value,
          "categoria":document.insBigliettoForm.categoria.value,
          "posto":
                  {"zona":document.insBigliettoForm.zona.value,
                    "fila":document.insBigliettoForm.fila.value,
                    "palco":document.insBigliettoForm.palco.value,
                    "numeroPosto":document.insBigliettoForm.numero_posto.value
                  },
          "replica":
                  {"idReplica":"<%=replica.getIdReplica()%>",
                    "spettacolo": {
                      "nome":"<%=replica.getSpettacolo().getNome()%>"
                    },
                    "inizio":"<%=replica.getInizio()%>"
                  },
          "utente":
                  {"idUtente":"<%=loggedUser.getIdUtente()%>"
                  }
        };

        let biglietti = [];
        let carrello = {
          biglietti: biglietti
        };
        if(localStorage.getItem("carrello")){
          carrello = JSON.parse(localStorage.getItem("carrello"));
        }
        carrello.biglietti.push(biglietto);
        localStorage.setItem("carrello", JSON.stringify(carrello));

        alert("Aggiunto al carrello");
      }

      function calcolaPrezzo(event, zona, categoria){
          prezzo = 15;
          zona = document.insBigliettoForm.zona.value;
          categoria = document.getElementById("categoria").value;

          if(!zona) return false;

          switch (zona) {
            case "palco laterale":
              prezzo += 3;
              break;
            case "palco centrale":
              prezzo += 6;
              break;
            case "platea":
              prezzo += 10;
              break;
          }

          switch (categoria) {
            case "ridotto under 20":
              prezzo += -8;
              break;
            case "ridotto under 30":
              prezzo += -5;
              break;
            case "ridotto over 65":
              prezzo += -8;
              break;
          }

          document.getElementById("prezzo").innerText = prezzo + " euro";
      }

      function submitBiglietto(event) {
        var selectedZona = document.insBigliettoForm.zona.value;

        if (!selectedZona) {
          alert('Seleziona un posto');
          event.preventDefault();
        }

        document.insBigliettoForm.controllerAction.value = "BigliettiManagement." + status;

      }

      function changeSeat(selectedDiv, zona, fila, palco, posto) {

        let carrello;

        if(selectedDiv.classList.contains('libero') && !selectedDiv.classList.contains('nonSelezionabile')){
          if(localStorage.getItem("carrello")){
            carrello = JSON.parse(localStorage.getItem("carrello"));
          }
          document.insBigliettoForm.id.value = carrello? carrello.biglietti.length : 0;
          document.insBigliettoForm.zona.value = zona;
          document.insBigliettoForm.fila.value = fila;
          document.insBigliettoForm.palco.value = palco;
          document.insBigliettoForm.numero_posto.value = posto;

          Array.from(document.querySelectorAll('.libero')).forEach(
                  (div) => div.classList.remove('postoSelezionato')
          );

          selectedDiv.classList.add("postoSelezionato");

          calcolaPrezzo();

          }
      }

      function mainOnLoadHandler() {

        document.insBigliettoForm.categoria.addEventListener("change", calcolaPrezzo);
        document.insBigliettoForm.submitButton.addEventListener("click", (event) => {submitBiglietto(event)});
        if(document.carrelloForm.addToCarrelloButton) document.carrelloForm.addToCarrelloButton.addEventListener("click", (event) => {updateCart(event)});

        Array.from(document.querySelectorAll(".occupato .infoPosto")).forEach(
                (divOccupato) => {
                  divOccupato.innerHTML += "<span style='display: inline-block; color:red;'>Occupato</span>";
                }
        );

        Array.from(document.querySelectorAll(".nonSelezionabile .infoPosto")).forEach(
                (divNonSelezionabile) => {
                  divNonSelezionabile.innerHTML += "<span style='display: inline-block; color:black;'>Non selezionabile</span>";
                }
        );

        if(<%=action.equals("modify")%>) calcolaPrezzo();

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
              <select id="categoria" name="categoria" form="insBigliettoForm" <%=action.equals("insert") ? "required" : "readonly"%>>
                <option value="intero" <%=action.equals("insert") || biglietto.getCategoria().equals("intero")? "selected" : ""%>>Intero</option>
                <option value="ridotto under 20" <%=action.equals("modify") && biglietto.getCategoria().equals("ridotto under 20")? "selected" : ""%>>Ridotto under 20</option>
                <option value="ridotto under 30" <%=action.equals("modify") && biglietto.getCategoria().equals("ridotto under 30")? "selected" : ""%>>Ridotto under 30</option>
                <option value="ridotto over 65" <%=action.equals("modify") && biglietto.getCategoria().equals("ridotto over 65")? "selected" : ""%>>Ridotto over 65</option>
              </select>
            </div>
            <%if(action.equals("insert")) {%><input type="hidden" name="replicaId" value="<%=replica.getIdReplica()%>"/><%}%>
            <%if(action.equals("modify")) {%><input type="hidden" name="bigliettoId" value="<%=biglietto.getIdBiglietto()%>"/><%}%>
            <input type="hidden" name="id" value=""/>
            <input type="hidden" name="zona" value="<%=(action.equals("modify")) ? biglietto.getPosto().getZona() : ""%>"/>
            <input type="hidden" name="fila" value="<%=(action.equals("modify")) ? biglietto.getPosto().getFila() : ""%>"/>
            <input type="hidden" name="palco" value="<%=(action.equals("modify")) && !biglietto.getPosto().getZona().equals("platea") ? biglietto.getPosto().getPalco() : ""%>"/>
            <input type="hidden" name="numero_posto" value="<%=(action.equals("modify")) ? biglietto.getPosto().getNumeroPosto() : ""%>"/>
            <input type="hidden" name="controllerAction"/>
          </form>
        </section>
        <%@include file="/include/mappaPosti.inc"%>
        <div class="bottom-container">
          <h1>Prezzo: <span id="prezzo">--</span></h1>
          <div class="button-container">
            <input type="submit" name="submitButton" form="insBigliettoForm" class="button" value="<%=(action.equals("modify")) ? "Conferma" : "Acquista"%>"/>
            <%if(action.equals("insert")) {%>
            <form name="carrelloForm" method="post" action="Dispatcher">
              <input type="hidden" name="selectedSpettacolo" value="<%=replica.getSpettacolo().getIdSpettacolo()%>"/>
              <input type="hidden" name="controllerAction" value="SpettacoliManagement.viewSpettacolo"/>
              <input type="submit" name="addToCarrelloButton" class="button" value="Aggiungi al carrello"/>
            </form>
            <%}%>
            <form name="backForm" method="post" action="Dispatcher">
              <input type="hidden" name="selectedSpettacolo" value="<%=replica.getSpettacolo().getIdSpettacolo()%>"/>
              <input type="hidden" name="controllerAction" value="<%=action.equals("modify") ? "BigliettiManagement.view" : "SpettacoliManagement.viewSpettacolo"%>"/>
              <input type="submit" name="goBackButton" class="button" value="Annulla"/>
            </form>
            </div>
        </div>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>