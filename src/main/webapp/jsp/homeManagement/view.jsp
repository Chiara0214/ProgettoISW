<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Home";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <link rel="stylesheet" href="css/home.css" type="text/css" media="screen">
    <title>Teatro</title>
    <script language="javascript">

      function search(event) {
        const data_da = document.searchForm.data_da.value;
        const data_a = document.searchForm.data_a.value;
        if(data_a < data_da) {
          alert('La data "Fino a" deve essere meno recente della data "Dal giorno"');
          event.preventDefault();
        }
      }

      function mainOnLoadHandler() {
        document.searchForm.searchButton.addEventListener("click", (event) => {search(event)});
      }

    </script>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <section class="copertina">
        <img src="images/teatro_filarmonico.jpg" alt="Teatro Filarmonico">
        <div id="barra-ricerca">
          <form name="searchForm" id="searchForm" method="get" action="Dispatcher">
            <input type="text" placeholder="Titolo spettacolo" id="titolo" name="titolo">
            <select name="genere" id="genere" form="searchForm">
              <option disabled selected value style="display:none;"> Genere </option>
              <option value="prosa">Prosa</option>
              <option value="opera">Opera</option>
              <option value="danza">Danza</option>
              <option value="concerti">Concerti</option>
              <option value="altro">Altro</option>
            </select>
            <input type="text" placeholder="Dal giorno" id="data-da" name="data_da"
                   onfocus="(this.type='date')"
                   onblur="(this.type='text')">
            <input type="text" placeholder="Fino a" id="data-a" name="data_a"
                   onfocus="(this.type='date')"
                   onblur="(this.type='text')">
            <input type="hidden" name="controllerAction" value="SpettacoliManagement.view"/>
            <input type="submit" name="searchButton" value="Cerca">
          </form>
        </div>

      </section>
      <ol class="galleria">
        <li>
          <a href="Dispatcher?controllerAction=SpettacoliManagement.view&genere=Prosa">
            <img src="images/la-bottega-del-caffe.jpg" alt="Prosa">
            <h1>Prosa</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=SpettacoliManagement.view&genere=Opera">
            <img src="images/don-giovanni.png" alt="Opera">
            <h1>Opera</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=SpettacoliManagement.view&genere=Danza">
            <img src="images/lago-dei-cigni.jpg" alt="Danza">
            <h1>Danza</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=SpettacoliManagement.view&genere=Concerti">
            <img src="images/le-quattro-stagioni-vivaldi.jpg" alt="Concerti">
            <h1>Concerti</h1>
          </a>
        </li>
        <li>
          <a href="Dispatcher?controllerAction=SpettacoliManagement.view&genere=Altro">
            <img src="images/categoria_extra.jpg" alt="Altro">
            <h1>Altro</h1>
          </a>
        </li>
      </ol>
      <section class="descrizione">
        <h2>Informazioni</h2>
        <p>Sito di un teatro per la ricerca di spettacoli e acquisto di biglietti.</p>
      </section>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
