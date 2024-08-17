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

        <section id="postiSection" style="width:1150px;display: flex; flex-direction: column; align-items: center;">

          <%-- -------- Zona galleria centrale -------- --%>
            <section id="galleria-centro" style="margin:15px;display: inline-block;">
              <% zona="galleria";
                for(fila_index=2; fila_index>0; fila_index--){%>
              <div class="fila">
                <% for(posto_index=1; posto_index<=27; posto_index++){
                  posto.setPosto(zona, fila_index, null, posto_index);
                %>
                <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;">
                  <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                </div>
                <%}%>
              </div>
              <%}%>
            </section>

          <%-- -------- Zona palco centrale -------- --%>
          <section id="palchi-centrali" style="width:615px;">
            <% for(palco_index=1; palco_index<=21; palco_index++){%>
            <div class="palco-centrale" style="display:inline-block; margin: 5px;">
              <% zona="palco centrale";
                for(fila_index=2; fila_index>0; fila_index--){%>
              <div class="fila">
                <% for(posto_index=1; posto_index<=4; posto_index++){
                posto.setPosto(zona, fila_index, palco_index, posto_index);
                %>
                <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,<%=palco_index%>,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; width: 15px; height: 15px; border-radius: 50%;">
                  <span class="infoPosto"><%=zona%> - palco n. <%=palco_index%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                </div>
                <%}%>
              </div>
              <%}%>
            </div>
            <%}%>
          </section>

            <div class="zona-centrale" style="display:flex; flex-direction: row; justify-content: center; align-items: center;">

              <%-- -------- Zona galleria sx -------- --%>
              <section id="galleria-sx" style="height:400px;margin:0 25px; display: flex; flex-wrap: wrap; gap:3px;">
                <% zona="galleria";
                  for(fila_index=2; fila_index>0; fila_index--){%>
                <div class="fila" style="display: flex; flex-direction: column; gap:3px;">
                  <% for(posto_index=1; posto_index<=18; posto_index++){
                    posto.setPosto(zona, fila_index, null, posto_index);
                  %>
                  <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;">
                    <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                  </div>
                  <%}%>
                </div>
                <%}%>
              </section>

            <%-- -------- Zona palco laterale di sinistra -------- --%>

            <section id="palchi-laterali-sx" style="height: 420px; width: 170px; display: flex; flex-direction:column; flex-wrap: wrap;">
              <% for(palco_index=1; palco_index<=15; palco_index++){%>
              <div class="palco-laterale" style="display:flex;flex-direction: row; margin: 8px;">
                <% zona="palco laterale";
                  for(fila_index=2; fila_index>0; fila_index--){%>
                <div class="fila" style="display: flex;flex-direction: column;gap:5px;">
                  <% for(posto_index=1; posto_index<=3; posto_index++){
                    posto.setPosto(zona, fila_index, palco_index, posto_index);
                  %>
                  <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,<%=palco_index%>,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;">
                    <span class="infoPosto"><%=zona%> - palco n. <%=palco_index%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                  </div>
                  <%}%>
                </div>
                <%}%>
              </div>
              <%}%>
            </section>

            <%-- -------- Zona platea -------- --%>
            <section id="platea" style="margin:0 80px;">
              <% zona="platea";
                for(fila_index=15; fila_index>0; fila_index--){%>
              <div class="fila">
                <% for(posto_index=1; posto_index<=20; posto_index++){
                  posto.setPosto(zona, fila_index, null, posto_index);
                %>
                <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;">
                  <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                </div>
                <%}%>
              </div>
              <%}%>
            </section>

              <%-- -------- Zona palco laterale di destra -------- --%>

              <section id="palchi-laterali-dx" style="height: 420px; width: 170px; display: flex; flex-direction:column; flex-wrap: wrap;">
                <% for(palco_index=13; palco_index<=27; palco_index++){%>
                <div class="palco-laterale" style="display:flex;flex-direction: row; margin: 8px;">
                  <% zona="palco laterale";
                    for(fila_index=2; fila_index>0; fila_index--){%>
                  <div class="fila" style="display: flex;flex-direction: column;gap:5px;">
                    <% for(posto_index=1; posto_index<=3; posto_index++){
                      posto.setPosto(zona, fila_index, palco_index, posto_index);
                    %>
                    <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,<%=palco_index%>,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;">
                      <span class="infoPosto"><%=zona%> - palco n. <%=palco_index%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                    </div>
                    <%}%>
                  </div>
                  <%}%>
                </div>
                <%}%>
              </section>

                <%-- -------- Zona galleria dx -------- --%>
                <section id="galleria-dx" style="height:400px;margin:0 25px; display: flex; flex-wrap: wrap; gap:3px;">
                  <% zona="galleria";
                    for(fila_index=2; fila_index>0; fila_index--){%>
                  <div class="fila" style="display: flex; flex-direction: column; gap:3px;">
                    <% for(posto_index=1; posto_index<=18; posto_index++){
                      posto.setPosto(zona, fila_index, null, posto_index);
                    %>
                    <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) ? "occupato" : "libero"%>" style="display: inline-block; margin: 2px; width: 15px; height: 15px; border-radius: 50%;">
                      <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                    </div>
                    <%}%>
                  </div>
                  <%}%>
                </section>

            </div>
          <p>Palco</p>

        </section>
        <form name="backForm" method="post" action="Dispatcher">
          <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
        </form>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>