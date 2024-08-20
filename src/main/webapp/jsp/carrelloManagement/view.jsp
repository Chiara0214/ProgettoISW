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

      function updateView(){
        let carrello = JSON.parse(localStorage.getItem("carrello"));

        let totale = 0;

        let newHTML = "";

        if(carrello && carrello.biglietti.length) {
          for (let i = 0; i < carrello.biglietti.length; i++) {
            totale += calcolaPrezzo(carrello.biglietti[i].posto.zona, carrello.biglietti[i].categoria);

            const data = carrello.biglietti[i].replica.inizio;
            const [dataGiorno, dataOra] = data.split(' ');

            const [anno, mese, giorno] = dataGiorno.split('-');
            const formattedDate = giorno + "/" + mese + "/" + anno;
            const [ora, minuti] = dataOra.split(':');
            const formattedTime = ora + ":" + minuti;

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
                    '<div class="bigliettoCampo"><h2>Posto:</h2><p>' + carrello.biglietti[i].posto.zona;
            if(carrello.biglietti[i].posto.zona.startsWith("palco")) {
              newHTML += " - palco " + carrello.biglietti[i].posto.palco;
            }
            newHTML +=" - fila " + carrello.biglietti[i].posto.fila + " - posto " + carrello.biglietti[i].posto.numeroPosto + '</p></div>' +
                    '</section>' +
                    '<input type="button" id="' + i + '" class="rimuoviButton" value="Rimuovi"/>' +
                    '</article>';

          }

        } else {
          newHTML = "<h2>Carrello vuoto</h2>";
          document.getElementById("bottom-container").style.display = 'none';
        }

        document.getElementById("biglietti-container").innerHTML = newHTML;
        document.getElementById("prezzo").innerText = totale + " euro";

        Array.from(document.querySelectorAll(".rimuoviButton")).forEach(
                (button) => {
                  button.addEventListener("click", () => {removeFromCart(button.id)});
                }
        );
      }

      function calcolaPrezzo(zona, categoria){
        let prezzo = 15;

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

        return prezzo;

      }

      function removeFromCart(id){
        let carrello = JSON.parse(localStorage.getItem("carrello"));

        const index = carrello.biglietti.findIndex((item) => {
          console.log("id: " + id + " idbig: " + item.idBiglietto);
          return item.idBiglietto == id;
        });
        if(index !== -1) carrello.biglietti.splice(index, 1);

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
    <div id="biglietti-container"></div>
    <div id="bottom-container">
      <h1>Totale: <span id="prezzo">--</span></h1>
      <div id="button-container">
        <input type="button" id="svuotaButton" class="button" value="Svuota carrello" />
        <input type="button" id="acquistaButton" class="button" value="Acquista tutto" />
      </div>
    <form name="acquistaForm" method="post" action="Dispatcher">
      <input type="hidden" name="carrello"/>
      <input type="hidden" name="controllerAction" value="CarrelloManagement.insert"/>
    </form>
    </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
