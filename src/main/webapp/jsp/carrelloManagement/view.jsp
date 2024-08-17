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

      function deleteItem(id){
        let carrello = JSON.parse(localStorage.getItem("carrello"));
        let biglietti = carrello.filter(biglietto => biglietto.id !== id);
        localStorage.setItem("carrello", JSON.stringify(biglietti));
      }

      function emptyCart() {
        localStorage.clear();
        document.getElementById("biglietti-container").innerHTML = "";
      }

      function mainOnLoadHandler() {

        let carrello = JSON.parse(localStorage.getItem("carrello"));

        var newHTML = "";

        for (var i = 0; i < carrello.length; i++) {
          console.log(carrello[i]);
          newHTML += '<article class="biglietto">' +
            '<h1>Biglietto</h1>' +
            '<section class="dettagliBiglietto">' +
              '<div class="bigliettoCampo"><h2>Nome intestato:</h2>' +
                '<p>' + carrello[i].nome + carrello[i].cognome + '</p></div>' +
              '<div class="bigliettoCampo"><h2>Categoria:</h2>' +
                '<p>' + carrello[i].categoria + '</p></div>' +
              '<div class="bigliettoCampo"><h2>Spettacolo:</h2>' +
                '<p>' + "spettacolo" + '</p></div>' +
              '<div class="bigliettoCampo"><h2>Data:</h2>' +
                '<p>' + "data" + '</p></div>' +
              '<div class="bigliettoCampo"><h2>Ora:</h2>' +
                '<p>' + "ora" + '</p></div>' +
              '<div class="bigliettoCampo"><h2>Posto:</h2><p>' + carrello[i].zona + carrello[i].palco + carrello[i].fila + carrello[i].numero_posto + '</p></div>' +
            '</section>' +
          '</article>';
        }

        document.getElementById("biglietti-container").innerHTML = newHTML;

        document.getElementById("svuotaButton").addEventListener("click", emptyCart);

      }

    </script>
  </head>
  <body>
  <%@include file="/include/header.inc" %>
  <main id="main">
    <div id="biglietti-container"></div>

    <input type="button" id="svuotaButton" class="button" value="Svuota carrello" />

    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
