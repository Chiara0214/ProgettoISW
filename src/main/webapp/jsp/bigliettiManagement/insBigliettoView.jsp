<%@page session="false"%>
<%@ page import="java.util.List" %>
<%@ page import="com.progettoisw.model.mo.*" %>

<%
  int fila_index=0;
  int posto_index=0;
  int palco_index=0;
  String zona=null;
  Posto posto = new Posto();
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  Replica replica = (Replica) request.getAttribute("replica");
  Biglietto biglietto = (Biglietto) request.getAttribute("biglietto");
  String coupons = (String) request.getAttribute("coupons");
  List<Posto> postiOccupati = (List<Posto>) request.getAttribute("postiOccupati");
  Posto currentPosto = new Posto();
  if(biglietto != null) {
    currentPosto = biglietto.getPosto();
  }
  String action=(biglietto != null) ? "modify" : "insert";
  String menuActiveLink;
  if(action.equals("modify")) {
    menuActiveLink = "I miei biglietti";
  } else {
    menuActiveLink = "Spettacoli";
  }
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/biglietti.css" type="text/css" media="screen">
    <title>Acquista biglietto</title>
    <script language="javascript">
      const status = "<%=action%>";
      let biglietti = [];
      let carrello = {
        biglietti: biglietti
      };

      let sconto = 0;

      const formatter = new Intl.NumberFormat('it-IT', {
        style: 'currency',
        currency: 'EUR'
      });

      if(localStorage.getItem("carrello")){
        carrello = JSON.parse(localStorage.getItem("carrello"));
      }

      const biglietto_id = carrello.biglietti.length;

      function updateCart(event){
        if(!document.insBigliettoForm.nome.value || !document.insBigliettoForm.cognome.value || !document.insBigliettoForm.categoria.value || !document.insBigliettoForm.zona.value) {
          alert("Inserisci tutti i campi");
          event.preventDefault();
          return false;
        }

        var letters = /^[A-Za-z]+$/;
        if(!document.insBigliettoForm.nome.value.match(letters) || !document.insBigliettoForm.cognome.value.match(letters)){
          alert("Nome e cognome devono contenere solo lettere");
          event.preventDefault();
          return false;
        }

        const biglietto = {
          "idCoupon":document.insBigliettoForm.idCoupon.value,
          "scontoCoupon":sconto,
          "idBiglietto":biglietto_id,
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

        /*Controllo se il biglietto è già nel carrello*/
        if(carrello.biglietti.length){
          const alreadyExists = carrello.biglietti.find(item => {
            return item.nome === document.insBigliettoForm.nome.value && item.cognome === document.insBigliettoForm.cognome.value && item.replica.idReplica == <%=replica.getIdReplica()%>
          }) !== undefined;
          if(alreadyExists) {
            alert("Un biglietto per la persona specificata \u00e8 gi\u00e0 presente nel carrello");
            return false;
          }
        }

        carrello.biglietti.push(biglietto);
        localStorage.setItem("carrello", JSON.stringify(carrello));

        alert("Aggiunto al carrello");
      }

      function validateCoupon(){
        const coupons = <%=coupons%>;
        console.log("coupons: " + coupons);
        const codice =  document.insBigliettoForm.couponCode.value;

        const coupon = coupons.find(item => item.codice === codice);

        if(sconto){
          alert("Hai gi\u00e0 usato un coupon per questo biglietto");
          return false;
        }

        if(!coupon){
          alert("Coupon non trovato");
          return false;
        }
        if(coupon.genere !== "<%=replica.getSpettacolo().getGenere()%>" && coupon.genere !== "Tutti"){
          alert("Coupon non valido per il genere " + "<%=replica.getSpettacolo().getGenere()%>");
          return false;
        }

        const oggi = new Date();

        const dataInizio = new Date(coupon.dataInizio)
        const dataFine = new Date(coupon.dataFine)

        if(dataFine < oggi){
          alert("Coupon scaduto");
          return false;
        }
        if(dataInizio.getTime() > oggi){
          const data = dataInizio.toLocaleDateString("it-IT", {
            year: "numeric",
            month: "long",
            day: "2-digit",
          });

          alert("Coupon valido dal " + data);

          return false;
        }

        let alreadyUsedInCarrello = false;
        if(carrello.biglietti.length){
          alreadyUsedInCarrello = carrello.biglietti.find(item => {
            return item.idCoupon == coupon.idCoupon;
          }) !== undefined;
        }

        const alreadyUsed = coupon.utenti.find(item => {return item.idUtente === <%=loggedUser.getIdUtente()%>
        }) !== undefined;
        if(alreadyUsed || alreadyUsedInCarrello){
          alert("Hai gi\u00e0 usato questo coupon");
          return false;
        }

        sconto = coupon.sconto;
        document.insBigliettoForm.idCoupon.value = coupon.idCoupon;

        alert("Sconto del " + sconto + "% applicato");

        //aggiorna prezzo
        calcolaPrezzo();

      }

      function calcolaPrezzo(){
          let prezzo = 15;

          const zona = document.insBigliettoForm.zona.value;
          const categoria = document.getElementById("categoria").value;

          if(!zona) return false;

          switch (zona) {
            case "Palco laterale":
              prezzo += 3;
              break;
            case "Palco centrale":
              prezzo += 6;
              break;
            case "Platea":
              prezzo += 10;
              break;
          }

          switch (categoria) {
            case "Ridotto under 20":
              prezzo += -8;
              break;
            case "Ridotto under 30":
              prezzo += -5;
              break;
            case "Ridotto over 65":
              prezzo += -8;
              break;
          }

          document.getElementById("prezzo").innerText = formatter.format(prezzo - prezzo * sconto / 100);
      }

      function submitBiglietto(event) {

        const selectedZona = document.insBigliettoForm.zona.value;

        if (!selectedZona) {
          alert('Seleziona un posto');
          event.preventDefault();
        }

        document.insBigliettoForm.controllerAction.value = "BigliettiManagement." + status;

        document.getElementById("categoria").disabled = false;

      }

      function changeSeat(selectedDiv, zona, fila, palco, posto) {

        /*Controllo se il posto selezionato è già nel carrello*/
        if (carrello.biglietti.length) {
          const prevZona = document.insBigliettoForm.zona.value;
          const prevFila = document.insBigliettoForm.fila.value;
          const prevPalco = document.insBigliettoForm.palco.value;
          const prevPosto = document.insBigliettoForm.numero_posto.value;

          document.insBigliettoForm.zona.value = zona;
          document.insBigliettoForm.fila.value = fila;
          document.insBigliettoForm.palco.value = palco;
          document.insBigliettoForm.numero_posto.value = posto;

          const alreadyExists = carrello.biglietti.find(item => {
            return item.posto.zona === document.insBigliettoForm.zona.value && item.posto.fila === document.insBigliettoForm.fila.value && item.posto.palco === document.insBigliettoForm.palco.value && item.posto.numeroPosto === document.insBigliettoForm.numero_posto.value && item.replica.idReplica == <%=replica.getIdReplica()%>
          }) !== undefined;
          if (alreadyExists) {
            /*Porto i valori del form a quelli precedenti*/
            document.insBigliettoForm.zona.value = prevZona;
            document.insBigliettoForm.fila.value = prevFila;
            document.insBigliettoForm.palco.value = prevPalco;
            document.insBigliettoForm.numero_posto.value = prevPosto;

            alert("Un biglietto con questo posto \u00e8 gi\u00e0 presente nel carrello");
            return false;
          }
        }

        if(selectedDiv.classList.contains('libero') && !selectedDiv.classList.contains('nonSelezionabile')) {
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
        if(document.getElementById("useCoupon")) document.getElementById("useCoupon").addEventListener("click", validateCoupon);
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

      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <div class="container" style="min-width: 1170px;">
        <section id="insBigliettoSection">
          <form name="insBigliettoForm" id="insBigliettoForm" action="Dispatcher" method="post">
            <div class="field">
              <label for="nome">Nome intestato</label>
              <input type="text" id="nome" name="nome" pattern="[A-Za-z]+" value="<%=(action.equals("modify")) ? biglietto.getNome() : ""%>" required/>
            </div>
            <div class="field">
              <label for="cognome">Cognome intestato</label>
              <input type="text" id="cognome" name="cognome" pattern="[A-Za-z]+" value="<%=(action.equals("modify")) ? biglietto.getCognome() : ""%>" required/>
            </div>
            <div class="field">
              <label for="categoria">Categoria</label>
              <select id="categoria" name="categoria" form="insBigliettoForm" <%=action.equals("insert") ? "required" : "disabled"%>>
                <option value="Intero" <%=action.equals("insert") || biglietto.getCategoria().equals("Intero")? "selected" : ""%>>Intero</option>
                <option value="Ridotto under 20" <%=action.equals("modify") && biglietto.getCategoria().equals("Ridotto under 20")? "selected" : ""%>>Ridotto under 20</option>
                <option value="Ridotto under 30" <%=action.equals("modify") && biglietto.getCategoria().equals("Ridotto under 30")? "selected" : ""%>>Ridotto under 30</option>
                <option value="Ridotto over 65" <%=action.equals("modify") && biglietto.getCategoria().equals("Ridotto over 65")? "selected" : ""%>>Ridotto over 65</option>
              </select>
            </div>
            <%if(action.equals("insert")) {%>
            <div class="field">
              <label for="couponCode">Codice coupon</label>
              <input type="text" id="couponCode" name="couponCode" value=""/>
              <input type="button" id="useCoupon" name="useCoupon" value="Applica">
            </div>
            <%}%>
            <%if(action.equals("insert")) {%><input type="hidden" name="replicaId" value="<%=replica.getIdReplica()%>"/><%}%>
            <%if(action.equals("modify")) {%><input type="hidden" name="bigliettoId" value="<%=biglietto.getIdBiglietto()%>"/><%}%>
            <input type="hidden" name="idCoupon"/>
            <input type="hidden" name="zona" value="<%=(action.equals("modify")) ? biglietto.getPosto().getZona() : ""%>"/>
            <input type="hidden" name="fila" value="<%=(action.equals("modify")) ? biglietto.getPosto().getFila() : ""%>"/>
            <input type="hidden" name="palco" value="<%=(action.equals("modify")) && !biglietto.getPosto().getZona().equals("platea") ? biglietto.getPosto().getPalco() : ""%>"/>
            <input type="hidden" name="numero_posto" value="<%=(action.equals("modify")) ? biglietto.getPosto().getNumeroPosto() : ""%>"/>
            <input type="hidden" name="controllerAction"/>
          </form>
        </section>
        <%@include file="/include/mappaPosti.inc"%>
        <div class="bottom-container">
          <%if(action.equals("insert")) {%><h1>Prezzo: <span id="prezzo">--</span></h1><%}%>
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
