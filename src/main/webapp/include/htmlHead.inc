<meta charset="utf-8"/>
<link rel="icon" href="images/favicon.png" type="image/png">
<link rel="stylesheet" href="css/styles.css" type="text/css" media="screen">
<script>
  var applicationMessage;
  <%if (applicationMessage != null) {%>
      applicationMessage="<%=applicationMessage%>";
      <%}%>
  function onLoadHandler() {
      headerOnLoadHandler();
      try { mainOnLoadHandler(); } catch (e) {}
      if (applicationMessage!=undefined) alert(applicationMessage);
  }
  window.addEventListener("load", onLoadHandler);
</script>