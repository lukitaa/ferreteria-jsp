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

import servlets.SessionUser;
import servlets.ShoppingCart;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public abstract class Template {

    protected SessionUser sessionUser;
    protected static String APP_ROOT = "/Ferreteria" + "/";

    public abstract String printContent(Object data);
    public abstract String printBreadcrumbs();
    public abstract String printNav(ShoppingCart shoppingCart);
    public abstract String printPage(String title, Object session, ShoppingCart shoppingCart);

    public static String printHeader(String title) {
        return "<!DOCTYPE html> <html lang=\"es\" dir=\"ltr\">     <head>         <meta charset=\"utf-8\">         <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">                  <title>Ferreter&iacute;a - " + title + "</title>                  <base href=\"" + APP_ROOT + "\" >                  <link href=\"static/css/styles.css\" rel=\"stylesheet\">         <link href=\"static/vendors/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">         <link href=\"static/vendors/bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">                  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->         <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->         <!--[if lt IE 9]>             <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>             <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>         <![endif]-->     </head>     <body>";
    }

    public static String printFooter() {
        return "<script src=\"static/vendors/jquery/js/jquery.min.js\"></script> <script src=\"static/vendors/bootstrap/js/bootstrap.min.js\"></script><script src=\"static/js/scripts.js\"></script> </body> </html>";
    }

    public static String printInitNav() {
        return "<nav class=\"navbar navbar-default\" role=\"navigation\">             <div class=\"container-fluid\">                 <div class=\"navbar-header\">                     <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">                         <span class=\"sr-only\">Activar navegaci&oacute;n</span>                         <span class=\"icon-bar\"></span>                         <span class=\"icon-bar\"></span>                         <span class=\"icon-bar\"></span>                     </button>                     <a class=\"navbar-brand\" href=\"inicio\">Ferreter&iacute;a</a>                 </div>                 <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">";
    }

    public static String printEndNav() {
        return "</div>             </div>         </nav>";
    }

    public static String printInitContainer() {
        return "<main role=\"main\" class=\"container\">             <div class=\"col-md-10 col-md-offset-1\">";
    }

    public static String printEndContainer() {
        return "</div></main>";
    }


}
