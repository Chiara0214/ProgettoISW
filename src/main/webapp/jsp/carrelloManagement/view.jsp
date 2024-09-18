<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Carrello";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/carrello.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title>Carrello</title>
    <script language="javascript">
      const formatter = new Intl.NumberFormat('it-IT', {
        style: 'currency',
        currency: 'EUR'
      });

      function updateView(){
        let carrello = JSON.parse(localStorage.getItem("carrello"));

        let totale = 0;

        let newHTML = "";

        if(carrello && carrello.biglietti.length) {
          for (let i = 0; i < carrello.biglietti.length; i++) {
            const prezzo = calcolaPrezzo(carrello.biglietti[i].posto.zona, carrello.biglietti[i].categoria, carrello.biglietti[i].scontoCoupon);

            totale += prezzo;

            const data = carrello.biglietti[i].replica.inizio;
            const [dataGiorno, dataOra] = data.split(' ');

            const [anno, mese, giorno] = dataGiorno.split('-');
            const formattedDate = giorno + "/" + mese + "/" + anno;
            const [ora, minuti] = dataOra.split(':');
            const formattedTime = ora + ":" + minuti;

            /* HTML del biglietto da aggiungere a biglietti-container */
            newHTML += '<article class="biglietto">' +
                    '<h1>Biglietto</h1>' +
                    '<section class="dettagliBiglietto">' +
                    '<div class="bigliettoCampo"><h2>Nome intestato:</h2>' +
                    '<p>' + carrello.biglietti[i].nome + " " + carrello.biglietti[i].cognome + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Categoria:</h2>' +
                    '<p>' + carrello.biglietti[i].categoria + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Spettacolo:</h2>' +
                    '<p>' + carrello.biglietti[i].replica.spettacolo.nome + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Data:</h2>' +
                    '<p>' + formattedDate + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Ora:</h2>' +
                    '<p>' + formattedTime + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Posto:</h2><p>' + carrello.biglietti[i].posto.zona + ' ' + carrello.biglietti[i].posto.palco;

            if(carrello.biglietti[i].posto.zona.startsWith("palco")) {
              newHTML += " - palco " + carrello.biglietti[i].posto.palco;
            }

            newHTML +=" - Fila " + carrello.biglietti[i].posto.fila + " - Posto " + carrello.biglietti[i].posto.numeroPosto + '</p></div>';

            if(carrello.biglietti[i].scontoCoupon !== 0) {
              newHTML += '<div class="bigliettoCampo"><h2>Sconto applicato:</h2><p>' + carrello.biglietti[i].scontoCoupon + '%' + '</p></div>';
            }

            /* Prezzo del singolo biglietto */
            newHTML += '</section>' +
                    '<span id="prezzoBiglietto">' + formatter.format(prezzo) + '</span>' +
                    '<input type="button" id="' + i + '" class="rimuoviButton" value="Rimuovi"/>' +
                    '</article>';

          }

        } else {
          newHTML = "<h2>Carrello vuoto</h2>";
          document.getElementById("bottom-container").style.display = 'none';
        }

        document.getElementById("biglietti-container").innerHTML = newHTML;
        document.getElementById("prezzo").innerText = formatter.format(totale);

        /* Pulsante per rimuovere il biglietto dal carrello */
        Array.from(document.querySelectorAll(".rimuoviButton")).forEach(
                (button) => {
                  button.addEventListener("click", () => {removeFromCart(button.id)});
                }
        );
      }

      function calcolaPrezzo(zona, categoria, sconto){
        let prezzo = 15;

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

        return prezzo - prezzo * sconto / 100;

      }

      function removeFromCart(id){
        let carrello = JSON.parse(localStorage.getItem("carrello"));

        const index = carrello.biglietti.findIndex((item) => {
          return item.idBiglietto == id;
        });
        /* Rimuovo l'elemento con l'indice specificato */
        if(index !== -1) carrello.biglietti.splice(index, 1); //findIndex ritorna -1 se non trova nessun elemento con l'id specificato

        localStorage.setItem("carrello", JSON.stringify(carrello));

        updateView();
      }

      function emptyCart() {
        localStorage.clear();
        document.getElementById("biglietti-container").innerHTML = "";

        updateView();
      }

      function acquista(){
        document.acquistaForm.carrello.value = localStorage.getItem("carrello");
        emptyCart();

        document.acquistaForm.submit();
      }

      function mainOnLoadHandler() {

        updateView();

        document.getElementById("svuotaButton").addEventListener("click", emptyCart);
        document.getElementById("acquistaButton").addEventListener("click", acquista);

      }

    </script>
  </head>
  <body>
  <%@include file="/include/header.inc" %>
  <main id="main">
    <div id="biglietti-container">
      <%-- Qua vengono inseriti i biglietti del carrello --%>
    </div>
    <div id="bottom-container">
      <h1>Totale: <span id="prezzo">--</span></h1>
      <div id="button-container">
        <input type="button" id="svuotaButton" class="button" value="Svuota carrello" />
        <form name="acquistaForm" method="post" action="Dispatcher">
          <input type="hidden" name="carrello"/>
          <input type="hidden" name="controllerAction" value="CarrelloManagement.insert"/>
          <input type="submit" id="acquistaButton" class="button" value="Acquista tutto" />
        </form>
      </div>
    </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
