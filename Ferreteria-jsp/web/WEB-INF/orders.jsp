<%-- 
    Document   : orders
    Created on : Oct 21, 2014, 2:42:33 AM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>

<%@page import="java.util.List"%>
<%@page import="util.HibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="controllers.PurchaseController"%>
<%@page import="entity.Purchases"%>
<%@page import="servlets.ShoppingCart"%>
<%@page import="servlets.Common"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="sessionUser" class="servlets.SessionUser" scope="session"/>
<%
ShoppingCart shoppingCart = Common.getCart(request);
int totalProducts = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;

// TODO: get pending orders
Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
List<Purchases> orders = PurchaseController.getPendingOrders(sessionHibernate);
%>  
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - Ordenes</title>
        
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
                        <li><a href="historic.jsp">Historial</a></li>
                        <li><a href="products.jsp">Productos</a></li>
                        <% if (sessionUser.isAdmin()) { %>
                        <li><a href="users.jsp">Usuarios</a></li>
                        <li class="active"><a href="ordenes">Ordenes</a></li>
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
                <!-- BEGINS BREADCRUMBS -->
                <ol class="breadcrumb">
                    <li><a href="home.jsp">Inicio</a></li>
                    <li class="active">Ordenes</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation products">
                    <h1 class="header">Ordenes pendientes</h1>
                    <p>A continuación podrá generar ordenes de piqueo para ordenes pendientes.</p>
                    <% if (orders != null && orders.size() > 0) { %>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID pedido</th>
                                <th>Usuario</th>
                                <th>Generar orden</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Purchases p : orders) { %>
                            <tr>    
                                <td><%= p.getIdPurchase() %></td>
                                <td><%= p.getUsers().getUsername() %></td>
                                <td><a href="generar-orden?pedido=<%= p.getIdPurchase() %>" title="Generar orden de piqueo para el pedido" class="btn btn-xs btn-info">Generar</a></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% } else { %>
                    <p class="lead">No se encontraron ordenes pendientes.</p>
                    <% } %>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>
<%
if (sessionHibernate != null) {
    sessionHibernate.close();
}
%>