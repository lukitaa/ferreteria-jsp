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

package servlets;

import controllers.StorageException;
import controllers.UsersController;
import entity.Details;
import entity.Purchases;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class HistoricDetailServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // User must be logged in to access this page!
        if (!Common.userIsLogged(request)) {
            response.sendRedirect("/Ferreteria/login");
            return;
        }

        SessionUser session = Common.getSessionUser(request);
        ShoppingCart shoppingCart = Common.getCart(request);

        String userId = request.getParameter("usuario");

        //TODO: recover purchase data
        Set<Purchases> purchases = null;
        Set<Details> purchaseDetails = null;

        //TODO: handle correctly hibernate exceptions
        // Take note that when the hibernate session is closed below,
        // it may have been closed before.

        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        if (userId != null && !userId.isEmpty()) {
            try {
                purchases = UsersController.getUserPurchases(Integer.parseInt(userId), sessionHibernate);
            } catch (StorageException ex) {//TODO: do something
                Logger.getLogger(HistoricDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(new templates.HistoricDetailTemplate(purchases).printPage("Historial detalle", session, shoppingCart));
        } finally {
            out.close();
        }
        sessionHibernate.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
