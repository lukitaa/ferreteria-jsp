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

package servlets;

import entity.Users;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class Common {

    private static String SESSION_ATTR_NAME = "session_user",
                          SESSION_ATTR_NAME_PURCHASE = "purchase_details",
                          SESSION_ATTR_NAME_CART = "shopping_cart";

    public static boolean adminIsLogged(HttpServletRequest request) {
        // Get the session or generate it if doesn't exist already
        HttpSession session = request.getSession();
        // Get user from session
        SessionUser s = (SessionUser) session.getAttribute(SESSION_ATTR_NAME);
        // Check for user already logged in and it has to be an admin
        return (s != null && s.isAdmin());
    }

    public static boolean userIsLogged(HttpServletRequest request) {
        // Get the session or generate it if doesn't exist already
        HttpSession session = request.getSession();
        // Check for user already logged in
        return (session.getAttribute(SESSION_ATTR_NAME) != null);
    }

    public static HttpSession generateSession(HttpServletRequest request, Users u) {
        HttpSession session = null;

        // Generate new session
        session = request.getSession();

        session.setAttribute(SESSION_ATTR_NAME, new SessionUser(u.getIdUser(), u.getUsername(), u.isAdmin()));

        return session;
    }

    public static SessionUser getSessionUser(HttpServletRequest request) {
        return (SessionUser) request.getSession().getAttribute(SESSION_ATTR_NAME);
    }


    //SHOPPING CART STUFF

    public static HttpSession generateCart(HttpServletRequest request, ShoppingCart c) {
        HttpSession session = null;

        // Generate new session
        session = request.getSession();

        session.setAttribute(SESSION_ATTR_NAME_CART, c);

        return session;
    }

    public static ShoppingCart getCart(HttpServletRequest request) {
        return (ShoppingCart) request.getSession().getAttribute(SESSION_ATTR_NAME_CART);
    }

    public static void destroyCart(HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_ATTR_NAME_CART, null);
    }
    
}
