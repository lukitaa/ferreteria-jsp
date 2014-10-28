<%-- 
    Document   : edited-user
    Created on : 16/09/2014, 19:41:33
    Author     : Lucio Martinez <luciomartinez at openmailbox dot org>
--%>


<%@page import="servlets.ShoppingCart"%>
<%@page import="servlets.Common"%>
<jsp:useBean id="sessionUser" class="servlets.SessionUser" scope="session"/>
<jsp:useBean id="shoppingCart" class="servlets.ShoppingCart" scope="session"/>
<%
int totalProducts = shoppingCart.getTotalProducts();
%>   
<!DOCTYPE html>
<html lang="es" dir="ltr">     
    <head>         
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Ferreter&iacute;a - Editar usuario</title>           

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
        <nav class="navbar navbar-default" role=\"navigagtion\">
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
                        <li><a href="products.jsp">Productos</a></li>"
                        <li class="active"><a href="users.jsp">Usuarios</a></li>
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
        <main role="main" class="container">
            <div class="col-md-10 col-md-offset-1">
                <ol class="breadcrumb">
                    <li><a href="home.jsp">Inicio</a></li>
                    <li><a href="users.jsp">Usuarios</a></li>
                    <li class="active">Editar usuario</li>
                </ol>
                <div class="jumbotron">
                    <h1>Editar Usuario</h1>
                    <% if (request.getParameter("success").equals("true")) {     %>
                        <p class="lead">Usuario editado exitosamente.</p>
                    <% } else {                                                 %>
                        <p class="lead">Usuario no editado debido a un error interno.</p>
                    <% }                                                        %>
                    <h2><a href="users.jsp">Volver a pagina usuarios.</a></h2>
                </div>
            </div>
        </main>
    
    <script src="static/vendors/jquery/js/jquery.min.js"></script>
    <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src="static/js/scripts.js"></script>
    
    </body>
</html>
    