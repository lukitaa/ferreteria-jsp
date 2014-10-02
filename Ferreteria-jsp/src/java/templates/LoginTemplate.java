/*
 * Copyright (C) 2014 Lucio Martinez <luciomartinez at openmailbox dot org>
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import servlets.ShoppingCart;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class LoginTemplate extends Template {

    /**
     *
     * @param data String that represents the username that used to login but failed
     *              If it is null, the user have not tried to login before
     * @return
     */
    @Override
    public String printContent(Object data) {
        boolean loginError = (data != null);
        String username = "", content = "";

        content = "<div class=\"jumbotron presentation login\">                     <h1>Bienvenido a Ferreter&iacute;a!</h1>                     <p>Inicia sesi&oacute;n para entrar al sistema de compras.</p>";

        if (loginError) {
            username = (String) data;
            content += "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">                         <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Cerrar</span></button>                         <strong>Error</strong> La combinaci&oacute;n usuario contraseña es incorrecta.                     </div>";
        }

        content += "<form role=\"form\" class=\"form form-horizontal\" method=\"post\">                         <div class=\"form-group\">                             <label for=\"username-login\" class=\"col-sm-3 control-label\">Nombre de Usuario</label>                             <div class=\"col-sm-7\">"
                + "<input type=\"text\" class=\"form-control\" name=\"username\" id=\"username-login\" placeholder=\"pepe\" value=\"" + username + "\" required>"
                + "</div>                         </div>                         <div class=\"form-group\">                             <label for=\"password-login\" class=\"col-sm-3 control-label\">Contraseña</label>                             <div class=\"col-sm-7\">                                 <input type=\"password\" class=\"form-control\" name=\"password\" id=\"password-login\" placeholder=\"C0n7r@s3ñ@\" required>                             </div>                         </div>                         <div class=\"form-group\">                             <div class=\"col-sm-offset-3 col-sm-7\">                                 <button type=\"submit\" class=\"btn btn-default\">Iniciar Sesi&oacute;n</button>                             </div>                         </div>                     </form>                 </div>";

        return content;
    }

    @Override
    public String printBreadcrumbs() {
        return "<ol class=\"breadcrumb\">"
                + "<li><a href=\"" + APP_ROOT + "\">Inicio</a></li>"
                + "<li class=\"active\">Login</li>"
                + "</ol>";
    }

    /**
     *
     * @param data String that represents the actual date
     * @return
     */
    @Override
    public String printNav(ShoppingCart useless) {
        String date = new SimpleDateFormat("dd 'of' MMM, yyyy").format(Calendar.getInstance().getTime());
        return "<ul class=\"nav navbar-nav\">                         <li><a href=\"inicio\">Inicio</a></li>                         <li class=\"active\"><a href=\"login\">Login</a></li>                     </ul>                     <ul class=\"nav navbar-nav navbar-right\">"
                + "<li><a>" + date + "</a></li>"
                + "</ul>";
    }

    /**
     *
     * @param title
     * @param data The username that was failed to login with
     * @return
     */
    @Override
    public String printPage(String title, Object data, ShoppingCart useless) {
        // Print the full page
        return Template.printHeader(title)
                + Template.printInitNav()
                + this.printNav(useless)
                + Template.printEndNav()
                + Template.printInitContainer()
                + this.printBreadcrumbs()
                + this.printContent(data)
                + Template.printEndContainer()
                + Template.printFooter();
    }

}
