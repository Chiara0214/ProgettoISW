<%@page session="false"%>
<%@page import="com.progettoisw.model.mo.Utente"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.progettoisw.model.mo.Biglietto" %>
<%@ page import="com.progettoisw.model.mo.Coupon" %>

<%
  int i = 0;
  DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  List<Coupon> coupons = (List<Coupon>) request.getAttribute("coupons");
  String menuActiveLink = "Gestione";
  String sidebarActiveLink = "Visualzza coupon";
%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/biglietti.css" type="text/css" media="screen">
    <%@include file="/include/htmlHead.inc"%>
    <title>Biglietti</title>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%@include file="/include/sidebar.inc"%>
      <div class="container" style="margin-left: 250px;">
        <section id="listaBiglietti">
          <%for (i = 0; i < coupons.size(); i++) {%>
            <article class="biglietto">
              <h1>Coupon n. <%=coupons.get(i).getIdCoupon()%></h1>
              <section class="dettagliBiglietto">
                <div class="bigliettoCampo"><h2>Sconto:</h2><p><%=coupons.get(i).getSconto()%>%</p></div>
                <div class="bigliettoCampo"><h2>Genere:</h2><p><%=coupons.get(i).getGenere()%></p></div>
                <div class="bigliettoCampo"><h2>Data inizio:</h2><p><%=df.format(coupons.get(i).getDataInizio())%></p></div>
                <div class="bigliettoCampo"><h2>Data fine:</h2><p><%=df.format(coupons.get(i).getDataFine())%></p></div>
              </section>
            </article>
          <%}%>
        </section>
      </div>
    </main>
    <%@include file="/include/footer.inc"%>
  </body>
</html>
