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

        var newHTML = "";

        if(carrello) {
          for (var i = 0; i < carrello.biglietti.length; i++) {

            newHTML += '<article class="biglietto">' +
                    '<h1>Biglietto</h1>' +
                    '<section class="dettagliBiglietto">' +
                    '<div class="bigliettoCampo"><h2>Nome intestato:</h2>' +
                    '<p>' + carrello.biglietti[i].nome + carrello.biglietti[i].cognome + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Categoria:</h2>' +
                    '<p>' + carrello.biglietti[i].categoria + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Spettacolo:</h2>' +
                    '<p>' + "spettacolo" + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Data:</h2>' +
                    '<p>' + "data" + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Ora:</h2>' +
                    '<p>' + "ora" + '</p></div>' +
                    '<div class="bigliettoCampo"><h2>Posto:</h2><p>' + carrello.biglietti[i].posto.zona + carrello.biglietti[i].posto.palco + carrello.biglietti[i].posto.fila + carrello.biglietti[i].posto.numeroPosto + '</p></div>' +
                    '</section>' +
                    '<input type="button" id="' + i + '" class="rimuoviButton" value="Rimuovi"/>' +
                    '</article>';

          }

        } else {
          newHTML = "<h2>Carrello vuoto</h2>";
          document.getElementById("button-container").style.display = 'none';
        }

        document.getElementById("biglietti-container").innerHTML = newHTML;

        Array.from(document.querySelectorAll(".rimuoviButton")).forEach(
                (button) => {
                  button.addEventListener("click", () => {removeFromCart(button.id)});
                }
        );
      }

      function removeFromCart(id){
        console.log("click");
        let carrello = JSON.parse(localStorage.getItem("carrello"));

        let index = carrello.biglietti.findIndex(item => item.id === id)
        carrello.biglietti.splice(index, 1);

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

    <div id="button-container">
        <input type="button" id="svuotaButton" class="button" value="Svuota carrello" />
        <input type="button" id="acquistaButton" class="button" value="Acquista tutto" />
    </div>
    <form name="acquistaForm" method="post" action="Dispatcher">
      <input type="hidden" name="carrello"/>
      <input type="hidden" name="controllerAction" value="CarrelloManagement.insert"/>
    </form>

    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
