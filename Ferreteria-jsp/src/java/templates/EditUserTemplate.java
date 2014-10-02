/*
 * Copyright (C) 2014 Luca Giordano, Lucio Martínez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package templates;

import entity.Users;
import servlets.SessionUser;
import servlets.ShoppingCart;

/**
 *
 * @author alumno
 */
public class EditUserTemplate extends Template {

    private Users user;

    public EditUserTemplate(Users user) {
        this.user = user;
    }

    @Override
    public String printContent(Object data) {
        return "<div class=\"jumbotron presentation users\">                    <h1>Editar Usuarios</h1>"
                + "<form class=\"form-inline\" role=\"form\" action=\"usuarios/editar\" method=\"post\">"
                + "<input type=\"hidden\" name=\"user-id\" value=\"" + user.getIdUser() + "\">"
                + "<div class=\"form-group\">                          <label for=\"username\">Nombre de usuario</label>"
                + "<input type=\"text\" value=\"" + user.getUsername() + "\" name=\"username\" id=\"username\" class=\"form-control\" placeholder=\"Nuevo nombre de usuario\" required>"
                + "</div>                        <div class=\"form-group\">                          <label for=\"user-password\">Contraseña</label>"
                + "<input type=\"password\" name=\"password\" id=\"user-password\" class=\"form-control\" placeholder=\"Nueva contraseña\" required>"
                + "</div>                        <div class=\"checkbox\">                          <label>                            Es administrador?  <input type=\"checkbox\" name=\"admin\">                           </label>                        </div>                        <button type=\"submit\" class=\"btn btn-default\">Editar</button>                    </form>                </div>";
    }

    @Override
    public String printBreadcrumbs() {
        return "<ol class=\"breadcrumb\">"
                + "<li><a href=\"" + APP_ROOT + "\">Inicio</a></li>"
                + "<li><a href=\"usuarios\">Usuarios</a></li>"
                + "<li class=\"active\">Editar</li>"
                + "</ol>";
    }

    @Override
    public String printNav(ShoppingCart shoppingCart) {
        int totalProducts = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;
        String username = sessionUser.getUsername(),
               content  = "";

        content += "<ul class=\"nav navbar-nav\">"
                + "<li><a href=\"inicio\">Inicio</a></li>"
                + "<li><a href=\"historial\">Historial</a></li>"
                + "<li><a href=\"productos\">Productos</a></li>"
                + "<li class=\"active\"><a href=\"usuarios\">Usuarios</a></li>"
                + "</ul>                     <ul class=\"nav navbar-nav navbar-right\">";

        if (totalProducts > 0)
            content += "<li><a href=\"productos\">Carrito <span class=\"badge\">" + totalProducts + "</span></a></li>";

        content += "<li><a>Hola, " + username + "!</a></li>"
                + "<li><a href=\"logout\">Salir</a></li>                     </ul>";

        return content;
    }

    @Override
    public String printPage(String title, Object session, ShoppingCart shoppingCart) {
        sessionUser = (SessionUser)session;

        return Template.printHeader(title)
                + Template.printInitNav()
                + this.printNav(shoppingCart)
                + Template.printEndNav()
                + Template.printInitContainer()
                + this.printBreadcrumbs()
                + this.printContent(null)
                + Template.printEndContainer()
                + Template.printFooter();
    }

}
