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

import entity.Products;
import java.util.List;
import servlets.SessionUser;
import servlets.ShoppingCart;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class ProductsTemplate extends Template {

    private List<Products> products;

    public ProductsTemplate(List<Products> products) {
        this.products = products;
    }

    private String printProducstInRows() {
        String rows = "";

        for (Products p : products) {
            rows += "<form class=\"products\" action=\"carrito\" method=\"post\">"
                + "<input type=\"hidden\" name=\"product-id\" value=\"" + p.getIdProduct() +  "\">"
                + "<tr>"
                + "<td>" + p.getProduct() + "</td>" // PRODUCT NAME
                + "<td class=\"price\">" + p.getPrice() + "</td>" // PRODUCT PRICE
                + "<td class=\"stock\">" + p.getStock() + "</td>" // PRODUCT STOCK
                + "<td><input type=\"number\" name=\"product-stock\" min=\"0\" max=\"" + p.getStock() + "\" value=\"0\"></td>"
                + "<td><button type=\"submit\" class=\"btn btn-xs btn-primary\">Agregar</button></td>"
                + "</tr>"
                + "</form>";
        }

        return rows;
    }

    @Override
    public String printContent(Object data) {
        String content = "";

        content += "<div class=\"jumbotron presentation products\">                    <h1 class=\"header\">Comprar productos</h1>                    <table class=\"table table-bordered\">                        <thead>                            <tr>                                <th>Producto</th>                                <th>Precio</th>                                <th>Stock</th>                                <th>Unidades</th>                                <th>Agregar</th>                            </tr>                        </thead>"
                + "<tbody>"
                + printProducstInRows()
                + "</tbody>"
                + "</table><a href=\"productos/compra\" class=\"btn btn-xs btn-primary\">Comprar</a></div>";

        return content;
    }

    @Override
    public String printBreadcrumbs() {
        return "<ol class=\"breadcrumb\">"
                + "<li><a href=\"" + APP_ROOT + "\">Inicio</a></li>"
                + "<li class=\"active\">Productos</li>"
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
                + "<li class=\"active\"><a href=\"productos\">Productos</a></li>";

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
