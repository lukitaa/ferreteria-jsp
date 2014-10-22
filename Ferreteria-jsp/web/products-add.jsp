<%-- 
    Document   : users-add
    Created on : Aug 26, 2014, 5:16:07 PM
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>

<jsp:useBean id="sessionUser" class="entity.Users" scope="session"/>
<%@page import="entity.Products"%>
<%@page import="controllers.ProductsController"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="controllers.StorageException"%>
<%@page import="controllers.UsersController"%>
<%@page import="java.util.List"%>
<%@page import="entity.Users"%>
<%@page import="servlets.ShoppingCart"%>

<%@page import="servlets.Common"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// Check if user is logged
    
    if (sessionUser == null || !sessionUser.isAdmin()) {
    response.sendRedirect("home.jsp");
    return;
}
    
    ShoppingCart shoppingCart = Common.getCart(request);
    int totalProducts = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;

    List<Products> listaProd = null;
    listaProd = ProductsController.getProducts();
%>   
<!DOCTYPE html>
<html lang="es" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Ferreter&iacute;a - ABM Productos</title>
        
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
                        <li><a href="users.jsp">Usuarios</a></li>
                        <li><a href="ordenes">Ordenes</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <%  if (totalProducts > 0) { %>
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
                    <li><a href="products.jsp">Productos</a></li>
                    <li class="active">Agregar</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron">
                    <h2>Agregar producto</h2>
                    <form class="form-inline" role="form" action="AddProductsServlet" method="post">
                        <div class="form-group">
                            <label>Nombre del producto:</label>
                            <input type="text" name="producto" id="producto" class="form-control" placeholder="Ingrese el producto" required>
                        </div>
                        <div class="form-group">
                            <label>Stock</label>
                            <input type="text" name="producto-stock" id="producto-stock" class="form-control" placeholder="Ingrese el stock" required>
                        </div>
                        <div class="form-group">
                            <label>Precio</label>
                            <input type="text" name="producto-precio" id="producto-precio" class="form-control" placeholder="Ingrese el precio" required>
                        </div>
                        <button type="submit" class="btn btn-default">Agregar producto</button>
                    </form>
                    <br>
                    <h2>Tabla de productos actuales</h2>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Precio</th>
                                <th>Stock</th>
                                <th>Modificar</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Products r : listaProd) { %>
                                <form action="DeleteProductServlet" method="post">
                                    <input type="hidden" name="product-id" value="<%= r.getIdProduct()%>">
                                    <tr>
                                        <td><%= r.getProduct()%></td>
                                        <td><%= r.getPrice() %></td>
                                        <td><%= r.getStock() %></td>
                                        <td><a href="edit-product.jsp?product-id=<%= r.getIdProduct()%>" class="btn btn-xs btn-info">Editar</a></td>
                                        <td><input type="submit" class="btn btn-xs btn-danger" value="Eliminar"></td>
                                    </tr>
                                </form>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>
