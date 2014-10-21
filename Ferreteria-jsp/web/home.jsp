<%-- 
    Document   : home
    Created on : Aug 26, 2014, 5:16:07 PM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>

<%@page import="servlets.SessionUser"%>
<%@page import="servlets.ShoppingCart"%>
<%@page import="servlets.Common"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<%
// Check if user is logged
if (!Common.userIsLogged(request)) {
    response.sendRedirect("login.jsp");
    return;
}
    
ShoppingCart shoppingCart = Common.getCart(request);
SessionUser sessionUser   = Common.getSessionUser(request);
int totalProducts         = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;
%>
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - Inicio</title>
        
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
                        <li class="active"><a href="home.jsp">Inicio</a></li>
                        <li><a href="historic.jsp">Historial</a></li>
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
                        <li><a href="DetailsServlet">Carrito <span class="badge"><%= totalProducts %></span></a></li>
                    <% } %>
                        <li><a>Hola, <%= sessionUser.getUsername() %>!</a></li>
                        <li><a class="btn-logout" href="logout">Salir</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- ENDS NAV -->
        
        <main role="main" class="container">
            <div class="col-md-10 col-md-offset-1">
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation home">
                    <h1 header>Bienvenido a Ferreter&iacute;a!</h1>
                    <p>Desde aqu&iacute; puedes acceder a las siguientes opciones: </p>
                    <div class="container menu">
                        <div class="row">
                            <a href="historic.jsp" class="col-md-3 btn-block btn btn-lg">historial</a>
                            <a href="products.jsp" class="col-md-3 btn-block btn btn-lg">productos</a>
                            <% 
                                if (sessionUser.isAdmin()){
                            %>
                                <a href="users.jsp" class="col-md-3 btn-block btn btn-lg">usuarios</a>
                                <a href="#on-the-near-future" class="col-md-3 btn-block btn btn-lg">pedidos</a>
                            <% } %>
                        </div>
                    </div>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>
