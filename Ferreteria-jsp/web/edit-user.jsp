<%-- 
    Document   : addUser
    Created on : 16/09/2014, 19:41:33
    Author     : alumno
--%>

<%@page import="entity.Users"%>
<%@page import="controllers.UsersController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%  
    int userID = Integer.valueOf(request.getParameter("user"));
    Users user = UsersController.getUser(userID);
%> 

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
                    <a class="navbar-brand" href="inicio">Ferreter&iacute;a</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="home.jsp">Inicio</a></li>
                        <li><a href="products.jsp">Productos</a></li>
                        <li class="active"><a href="users.jsp">Usuarios</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="products.jsp">Carrito <span class="badge">0</span></a></li>
                        <li><a>Hola, YO!</a></li>
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
                    <li class="active">Usuarios</li>
                </ol>
                <!-- ENDS BREADCRUMBS -->
                <!-- BEGINS CONTENT -->
                <div class="jumbotron presentation users">
                    <h1>Editar Usuarios</h1>
                    <form class="form-inline" role="form" action="usuarios/editar" method="post">
                        <input type="hidden" name="user-id" value="<%= user.getIdUser() %>" >
                        <div class="form-group">
                            <label for="username">Nombre de usuario</label>
                            <input type="text" name="username" id="username" class="form-control" placeholder="Nuevo nombre de usuario" value="<%= user.getUsername() %>" required>
                        </div>
                        <div class="form-group">
                            <label for="user-password">Contraseña</label>
                            <input type="password" name="password" id="user-password" class="form-control" placeholder="Nueva contraseña" value="<%= user.getPassword() %>" required>
                        </div>
                        <div class="checkbox">
                            <label>
                                Es administrador?  <input type="checkbox" name="admin" checked="<%= user.isAdmin()%>" > 
                            </label>
                        </div>
                        <button type="submit" class="btn btn-default">Editar</button>
                    </form>
                </div>
                <!-- ENDS CONTENT -->
            </div>
        </main>
        
        <script src="static/vendors/jquery/js/jquery.min.js"></script>
        <script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/js/scripts.js"></script>
    </body>
</html>
