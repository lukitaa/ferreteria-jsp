<%-- 
    Document   : procesa-login
    Created on : 21/10/2014, 20:58:24
    Author     : alumno
--%>

<jsp:useBean id="sessionUser" class="entity.Users" scope="session"/>
<jsp:setProperty name="sessionUser" property="*" />
<%@page import="entity.Users"%>
<%@page import="controllers.StorageException"%>
<%@page import="controllers.InvalidParameterException"%>
<%@page import="controllers.LoginController"%>
<%
boolean success = false;

try {
    Users u = LoginController.login(sessionUser.getUsername(), sessionUser.getPassword());
    
    sessionUser.setAdmin(u.isAdmin());

    success = true;
} 
catch(InvalidParameterException e) { } 
catch(StorageException e) { }

if (success) {
    response.sendRedirect("home.jsp");
} else {
    response.sendRedirect("login.jsp?username=" + sessionUser.getUsername());
}
%>