<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Replica" %>
<%@ page import="com.progettoisw.model.mo.Biglietto" %>
<%@ page import="com.progettoisw.model.mo.Posto" %>
<%@ page import="java.util.List" %>

<%
  int i=0;
  int j=0;
  int k=0;
  boolean loggedOn = true;
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Spettacoli";
  Replica replica = (Replica) request.getAttribute("replica");
  Biglietto biglietto = (Biglietto) request.getAttribute("biglietto");
  List<Posto> postiOccupati = (List<Posto>) request.getAttribute("postiOccupati");
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

      function submitBiglietto() {
        var f;
        f = document.insBigliettoForm;
        f.controllerAction.value = "BigliettiManagement."+status;
      }

      function goBack() {
        document.backForm.submit();
      }

      function mainOnLoadHandler() {
        document.insBigliettoForm.addEventListener("submit", submitBiglietto);
        document.insBigliettoForm.backButton.addEventListener("click", goBack);
      }

      function changeSeat(zona, fila, palco, posto){
        console.log("selezionato zona " + zona + ", fila " + fila + ", palco " + palco + ", posto " + posto);
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
            <input type="submit" name="submitButton" class="button" value="<%=(action.equals("modify")) ? "Conferma" : "Acquista"%>"/>
            <input type="button" name="backButton" class="button" value="Annulla"/>
            <%if(action.equals("insert")) {%><input type="hidden" name="replicaId" value="<%=replica.getIdReplica()%>"/><%}%>
            <%if(action.equals("modify")) {%><input type="hidden" name="bigliettoId" value="<%=biglietto.getIdBiglietto()%>"/><%}%>
            <input type="hidden" name="zona" value="palco laterale"/>
            <input type="hidden" name="fila" value="1"/>
            <input type="hidden" name="palco" value="12"/>
            <input type="hidden" name="numero_posto" value="3"/>
            <input type="hidden" name="controllerAction"/>
          </form>
        </section>

        <section id="postiSection">
          <section id="palchi-centrali" style="width:700px;">
            <% for(k=1; k<=24; k++){%>
            <div class="palco-centrale" style="display:inline-block; margin: 5px;">
              <% for(i=2; i>0; i--){%>
              <div class="fila">
                <% for(j=1; j<=4; j++){%>
                <div onclick="changeSeat('palco centrale',<%=i%>,<%=k%>,<%=j%>)" class="<%=isOccupied(postiOccupati, "platea", i, k, j) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;"></div>
                <%}%>
              </div>
              <%}%>
            </div>
            <%}%>
          </section>
          <section id="platea" style="margin:10px 75px;">
            <% for(i=15; i>0; i--){%>
            <div class="fila">
            <% for(j=1; j<=20; j++){%>
              <div onclick="changeSeat('platea',<%=i%>,0,<%=j%>)" class="<%=isOccupied(postiOccupati, "platea", i, 0, j) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;"></div>
            <%}%>
            </div>
            <%}%>
          </section>
        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>

<%!
  private boolean isOccupied(List<Posto> postiOccupati, String zona, int fila, int palco, int posto) {
    return postiOccupati.contains(new Posto(zona, fila, palco, posto));
  }
%>