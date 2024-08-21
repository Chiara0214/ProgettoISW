<script>
  function headerOnLoadHandler() {
    var emailTextField = document.querySelector("#email");
    var emailTextFieldMsg = "L'indirizzo email \xE8 obbligatorio.";
    var passwordTextField = document.querySelector("#password");
    var passwordTextFieldMsg = "La password \xE8 obbligatoria.";

    if (emailTextField != undefined && passwordTextField != undefined ) {
      emailTextField.setCustomValidity(emailTextFieldMsg);
      emailTextField.addEventListener("change", function () {
        this.setCustomValidity(this.validity.valueMissing ? emailTextFieldMsg : "");
      });
      passwordTextField.setCustomValidity(passwordTextFieldMsg);
      passwordTextField.addEventListener("change", function () {
       this.setCustomValidity(this.validity.valueMissing ? passwordTextFieldMsg : "");
      });
    }
  }
</script>

<header class="topnav">
    <nav>
        <ul>
            <li <%=menuActiveLink.equals("Home")?"class=\"active\"":""%>><a href="Dispatcher?controllerAction=HomeManagement.view">Home</a></li>
            <li <%=menuActiveLink.equals("Spettacoli")?"class=\"active\"":""%>><a href="Dispatcher?controllerAction=SpettacoliManagement.view">Spettacoli</a></li>
            <%if (loggedOn) {%><li <%=menuActiveLink.equals("I miei biglietti")?"class=\"active\"":""%>><a href="Dispatcher?controllerAction=BigliettiManagement.view">I miei biglietti</a></li><%}%>
            <%if (loggedOn && loggedUser.getPrivilegi()) {%><li <%=menuActiveLink.equals("Gestione")?"class=\"active\"":""%>><a href="Dispatcher?controllerAction=SpettacoliManagement.insView">Gestione</a></li><%}%>
            <%if (loggedOn) {%>
            <li <%=menuActiveLink.equals("Carrello")?"class=\"active\"":""%>><a href="Dispatcher?controllerAction=CarrelloManagement.view">Carrello</a></li>
            <%}%>
            <%if (!loggedOn) {%>
            <li <%=menuActiveLink.equals("Registrati")?"class=\"active\"":""%>><a href="Dispatcher?controllerAction=HomeManagement.registrazioneView">Registrati</a></li>
            <%}%>
        </ul>
    </nav>
  <form name="logoutForm" action="Dispatcher" method="post">
    <input type="hidden" name="controllerAction" value="HomeManagement.logout"/>
  </form>

    <section id="login">
        <%if (loggedOn) {%><span><a href="javascript:logoutForm.submit()">Logout</a></span><%}%>
        <%if (!loggedOn) {%>
          <form name="logonForm" action="Dispatcher" method="post">
            <label for="email">Email</label>
            <input type="text" id="email"  name="email" maxlength="50" required>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" maxlength="45" required>
            <input type="hidden" name="controllerAction" value="HomeManagement.logon"/>
            <input type="submit" value="Ok">
          </form>
        <%}%>
    </section>

</header>