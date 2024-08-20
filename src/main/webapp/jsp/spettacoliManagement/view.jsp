<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="com.progettoisw.model.mo.Spettacolo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>

<%
  int i = 0;
  int j = 0;
  Date inizio;
  DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  List<Spettacolo> spettacoli = (List<Spettacolo>) request.getAttribute("spettacoli");
  String menuActiveLink = "Spettacoli";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/spettacoli.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title>Spettacoli</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main style="background-color: #8a3b3b;">
      <div class="spettacoli-container">
        <%for (i = 0; i < spettacoli.size(); i++) {%>
        <article class="spettacolo" id="spettacolo">
          <a href="Dispatcher?controllerAction=SpettacoliManagement.viewSpettacolo&selectedSpettacolo=<%=spettacoli.get(i).getIdSpettacolo()%>">
            <img src="images/la-bottega-del-caffe.jpg" alt="La bottega del caffè">
            <section class="spettacolo-details">
              <h1><%= spettacoli.get(i).getNome()%></h1>
              <h2><%= spettacoli.get(i).getGenere()%></h2>
              <p><%= spettacoli.get(i).getCompagnia()%></p>
              <section class="date-spettacolo">
                <%for (j = 0; j < spettacoli.get(i).getRepliche().size(); j++) {%>
                <%
                  inizio = spettacoli.get(i).getRepliche(j).getInizio();
                %>
                <p><%=inizio != null ? df.format(inizio) : "Nessuna data disponibile"%></p>
                <%}%>
              </section>
            </section>
          </a>
        </article>
        <%}%>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
