<%-- 
    Document   : historic-detail
    Created on : Sep 23, 2014, 3:34:13 PM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>

<%@page import="entity.Purchases"%>
<%@page import="util.HibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="java.util.Set"%>
<%@page import="controllers.PurchaseController"%>
<%@page import="entity.Details"%>
<%@page import="controllers.StorageException"%>
<%@page import="controllers.UsersController"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Users"%>
<%@page import="servlets.ShoppingCart"%>
<%@page import="servlets.Common"%>
<%@page import="servlets.SessionUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%  //Check to see if the user it's trying to enter the page via URL changing.
    // If user is logged, do not login *again*!
    SessionUser sessionUser = Common.getSessionUser(request);
    if (!Common.userIsLogged(request)) {
        response.sendRedirect("home.jsp");
        return;
    }
    
    ShoppingCart shoppingCart = Common.getCart(request);
    int totalProducts = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;
    
    List<Users> users = new ArrayList();
    Users u = null;
    try {
        users = UsersController.getUsers();
    } catch (StorageException ex) {
        //TODO: do something
    }
    int userId = Integer.valueOf(request.getParameter("usuario"));
    for(Users usuario : users){
        if(usuario.getIdUser() == userId )
            u = usuario;
    }
    Set<Purchases> purchases = u.getPurchaseses();
    Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
    purchases = UsersController.getUserPurchases(userId, sessionHibernate);
    
    int total = 0;
%>
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - TITLE</title>
        
        <base href="${pageContext.request.contextPath}/" >
        
        <link href="static/css/styles.css" rel="stylesheet">
        <link href="static/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="static/vendors/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        
        <!-- BEGINS NAV -->
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Activar navegaci&oacute;n</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="home.jsp">Ferreter&iacute;a</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="home.jsp">Inicio</a></li>
                        <li class="active"><a href="historic.jsp">Historial</a></li>
                        <li><a href="products.jsp">Productos</a></li>
                        <% 
                            if (sessionUser.isAdmin()){
                        %>
                        <li><a href="users.jsp">Usuarios</a></li>
                        <% } %>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                    <% 
                        if (totalProducts > 0){
                    %>
                        <li><a href="products.jsp">Carrito <span class="badge"><%= totalProducts %></span></a></li>
                    <% } %>
                        <li><a>Hola, <%= sessionUser.getUsername() %>!</a></li>
                        <li><a href="logout">Salir</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- ENDS NAV -->
        
        <main role="main" class="container">
            <div class="col-md-10 col-md-offset-1">
                <!-- BEGINS BREADCRUMBS -->
                <ol class="breadcrumb">
                    <li><a href="home.jsp">Inicio</a></li>
                    <li class="active">Historial</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation products">
                    <h1 class="header">Compras realizadas</h1>
                    <% for (Purchases p : purchases) { %>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Producto</th>
                                    <th>Precio Historico</th>
                                    <th>Unidades</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% 
                            Set<Details> purchaseDetails = p.getDetailses();
                            for (Details d : purchaseDetails) { 
                            %>
                                <tr> 
                                    <td><%= d.getProducts().getProduct() %></td>
                                    <td><%= d.getPrice() %></td>
                                    <td><%= d.getAmount() %></td>
                                </tr>
                                <% 
                                    total += d.getPrice() * d.getAmount();
                                %>
                            <% } %>
                            </tbody>
                        </table>
                        <p class="lead">Total: <%= total %></p>
                    <% total = 0; } %>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>