<div id="mappa-container">
    <section id="mappaPosti">
        <h1>Seleziona un posto:</h1>
        <div id="zona-superiore-container">
            <%-- -------- Zona galleria centrale -------- --%>
            <section id="galleria-centro">
                <% zona="galleria";
                for(fila_index=2; fila_index>0; fila_index--){%>
                <div class="fila">
                    <% for(posto_index=1; posto_index<=27; posto_index++){
                    posto.setPosto(zona, fila_index, null, posto_index);
                    %>
                    <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
                    <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
                </div>
                <%}%>
        </div>
        <%}%>
    </section>
    <%-- -------- Zona palco centrale -------- --%>
    <section id="palchi-centrali">
        <% for(palco_index=1; palco_index<=21; palco_index++){%>
        <div class="palco-centrale">
            <% zona="palco centrale";
            for(fila_index=2; fila_index>0; fila_index--){%>
            <div class="fila">
                <% for(posto_index=1; posto_index<=4; posto_index++){
                posto.setPosto(zona, fila_index, palco_index, posto_index);
                %>
                <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,<%=palco_index%>,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
                <span class="infoPosto"><%=zona%> - palco n. <%=palco_index%> - fila <%=fila_index%> - posto <%=posto_index%></span>
            </div>
            <%}%>
        </div>
        <%}%>
</div>
<%}%>
</section>
</div>
<div id="zona-centrale-container">
    <%-- -------- Zona galleria sx -------- --%>
    <section id="galleria-sx">
        <% zona="galleria";
        for(fila_index=2; fila_index>0; fila_index--){%>
        <div class="fila">
            <% for(posto_index=1; posto_index<=18; posto_index++){
            posto.setPosto(zona, fila_index, null, posto_index);
            %>
            <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
            <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
        </div>
        <%}%>
</div>
<%}%>
</section>
<%-- -------- Zona palco laterale di sinistra -------- --%>
<section id="palchi-laterali-sx">
    <% for(palco_index=1; palco_index<=15; palco_index++){%>
    <div class="palco-laterale">
        <% zona="palco laterale";
        for(fila_index=2; fila_index>0; fila_index--){%>
        <div class="fila" style="display: flex;flex-direction: column;gap:5px;">
            <% for(posto_index=1; posto_index<=3; posto_index++){
            posto.setPosto(zona, fila_index, palco_index, posto_index);
            %>
            <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,<%=palco_index%>,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
            <span class="infoPosto"><%=zona%> - palco n. <%=palco_index%> - fila <%=fila_index%> - posto <%=posto_index%></span>
        </div>
        <%}%>
    </div>
    <%}%>
    </div>
    <%}%>
</section>
<%-- -------- Zona platea -------- --%>
<section id="platea">
    <% zona="platea";
    for(fila_index=15; fila_index>0; fila_index--){%>
    <div class="fila">
        <% for(posto_index=1; posto_index<=20; posto_index++){
        posto.setPosto(zona, fila_index, null, posto_index);
        %>
        <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
        <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
    </div>
    <%}%>
    </div>
    <%}%>
</section>
<%-- -------- Zona palco laterale di destra -------- --%>
<section id="palchi-laterali-dx">
    <% for(palco_index=16; palco_index<=30; palco_index++){%>
    <div class="palco-laterale">
        <% zona="palco laterale";
        for(fila_index=1; fila_index<=2; fila_index++){%>
        <div class="fila" style="display: flex;flex-direction: column;gap:5px;">
            <% for(posto_index=1; posto_index<=3; posto_index++){
            posto.setPosto(zona, fila_index, palco_index, posto_index);
            %>
            <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,<%=palco_index%>,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
            <span class="infoPosto"><%=zona%> - palco n. <%=palco_index%> - fila <%=fila_index%> - posto <%=posto_index%></span>
        </div>
        <%}%>
    </div>
    <%}%>
    </div>
    <%}%>
</section>
<%-- -------- Zona galleria dx -------- --%>
<section id="galleria-dx">
    <% zona="galleria";
    for(fila_index=1; fila_index<=2; fila_index++){%>
    <div class="fila">
        <% for(posto_index=1; posto_index<=18; posto_index++){
        posto.setPosto(zona, fila_index, null, posto_index);
        %>
        <div onclick="changeSeat(this, '<%=zona%>',<%=fila_index%>,null,<%=posto_index%>)" class="posto <%=postiOccupati.contains(posto) && !posto.equals(currentPosto) ? "occupato" : "libero"%> <%=action.equals("modify") && posto.equals(currentPosto)  ? "postoSelezionato" : ""%>">
        <span class="infoPosto"><%=zona%> - fila <%=fila_index%> - posto <%=posto_index%></span>
    </div>
    <%}%>
    </div>
    <%}%>
</section>
</div>
<%-- -------- Palco -------- --%>
<section id="palco">
    <h1>Palco</h1>
</section>
</section>
</div>