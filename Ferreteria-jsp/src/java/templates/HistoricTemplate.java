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
import java.util.List;
import servlets.SessionUser;
import servlets.ShoppingCart;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class HistoricTemplate extends Template {

    private List<Users> usuarios;

    public HistoricTemplate(List<Users> usuarios_) {
        this.usuarios = usuarios_;
    }

    private String printAllUsersInRows() {
        String rows = "";

        for (Users u : usuarios) {
            rows += "<tr>"
                + "<td>" + u.getUsername() + "</td>"
                + "<td><a href=\"historial/cliente?usuario=" + u.getIdUser() + "\" class=\"btn btn-xs btn-info\">Ver</a></td>"
                + "</tr>";
        }
        return rows;
    }

    @Override
    public String printContent(Object data) {
        return "<div class=\"jumbotron presentation products\">                    <h1 class=\"header\">Historial de compras</h1>                    <table class=\"table table-bordered\">                        <thead>                            <tr>                                <th>Nombre de usuario</th>                                <th>Historial</th>                            </tr>                        </thead>"
                + "<tbody>"
                + printAllUsersInRows()
                + "</tbody>                    </table>                </div>";
    }

    @Override
    public String printBreadcrumbs() {
        return "<ol class=\"breadcrumb\">"
                + "<li><a href=\"" + APP_ROOT + "\">Inicio</a></li>"
                + "<li class=\"active\">Historial</li>"
                + "</ol>";
    }

    @Override
    public String printNav(ShoppingCart shoppingCart) {
        int totalProducts = (shoppingCart != null) ? shoppingCart.getTotalProducts() : 0;
        String username = sessionUser.getUsername(),
               content  = "";

        content += "<ul class=\"nav navbar-nav\">"
                + "<li><a href=\"inicio\">Inicio</a></li>"
                + "<li class=\"active\"><a href=\"historial\">Historial</a></li>"
                + "<li><a href=\"productos\">Productos</a></li>";

        if (sessionUser.isAdmin())
            content += "<li><a href=\"usuarios\">Usuarios</a></li>";

        content += "</ul>                     <ul class=\"nav navbar-nav navbar-right\">";

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
