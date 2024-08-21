<nav class="sidebar">
    <ul>
        <li <%=sidebarActiveLink.equals("Aggiungi spettacolo")?"class=\"active\"":""%>><a class="add" href="Dispatcher?controllerAction=SpettacoliManagement.insView"><span>Aggiungi spettacolo</span></a></li>
        <li <%=sidebarActiveLink.equals("Aggiungi coupon")?"class=\"active\"":""%>><a class="add" href="Dispatcher?controllerAction=CouponManagement.insView"><span>Aggiungi coupon</span></a></li>
        <li style="margin: 15px 0;"><hr style="border: 1px solid black;"></li>
        <li <%=sidebarActiveLink.equals("Visualizza biglietti")?"class=\"active\"":""%>><a class="view" href="Dispatcher?controllerAction=BigliettiManagement.view&gestione=true"><span>Visualizza biglietti</span></a></li>
        <li <%=sidebarActiveLink.equals("Visualizza coupon")?"class=\"active\"":""%>><a class="view" href="Dispatcher?controllerAction=CouponManagement.view"><span>Visualizza coupon</span></a></li>
    </ul>
</nav>