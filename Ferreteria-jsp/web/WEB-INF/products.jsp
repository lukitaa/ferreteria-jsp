<%-- 
    Document   : products
    Created on : Aug 26, 2014, 5:16:07 PM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>


<%@page import="controllers.PurchaseController"%>
<%@page import="entity.Products"%>
<%@page import="java.util.List"%>
<%@page import="servlets.ShoppingCart"%>
<%@page import="servlets.Common"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="sessionUser" class="servlets.SessionUser" scope="session"/>
<jsp:useBean id="products" type="java.util.List<Products>" scope="session"/>
<%
ShoppingCart shoppingCart = Common.getCart(request);
int totalProducts = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;
%>   
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - Productos</title>
        
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
                        <li class="active"><a href="products.jsp">Productos</a></li>
                        <% if (sessionUser.isAdmin()) { %>
                        <li><a href="users.jsp">Usuarios</a></li>
                        <li><a href="ordenes">Ordenes</a></li>
                        <% } %>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <% if (totalProducts > 0) { %>
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
                    <li class="active">Productos</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation products">
                    <h1 class="header">Productos</h1>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Precio</th>
                                <th>Stock</th>
                                <th>Unidades</th>
                                <th>Agregar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Products p : products) { %>
                            <form class="products" action="AddToCartServlet" method="post">
                                <input type="hidden" name="product-id" value="<%= p.getIdProduct()%>">
                                <tr>
                                    <td><%= p.getProduct() %></td>
                                    <td class="price"><%= p.getPrice() %></td>
                                    <td class="stock"><%= p.getStock() %></td>
                                    <td><input type="number" name="product-stock" min="0" max="<%= p.getStock()%>" value="0"></td>
                                    <td><button type="submit" class="btn btn-xs btn-default" title="Agregar producto al carrito">Agregar</button></td>
                                </tr>
                            </form>
                            <% } %>      
                        </tbody>
                    </table>
                    <a href="carrito" class="btn btn-primary">Ver Pedido</a>
                    <% if (sessionUser.isAdmin()) { %>
                        <a href="productos/editar" class="btn btn-primary btn-right">ABM de Productos</a>
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
