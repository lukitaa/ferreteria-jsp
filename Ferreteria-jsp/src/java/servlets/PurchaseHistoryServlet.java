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

import controllers.PurchaseController;
import controllers.StorageException;
import controllers.UsersController;
import entity.Details;
import entity.Purchases;
import entity.Users;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
public class PurchaseHistoryServlet extends HttpServlet {

    private void renderHistoryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Users> users = new ArrayList();
        try {
            users = UsersController.getUsers();
        } catch (StorageException ex) {
            //TODO: do something
        }

        Common.addAttribute(request, "users", users);

        request.getRequestDispatcher("/WEB-INF/historic.jsp").forward(request, response);
    }

    private void renderHistoryDetail(HttpServletRequest request, HttpServletResponse response,
            int userId)
            throws ServletException, IOException {

        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        try {
            Set<Purchases> purchases = UsersController.getUserPurchases(userId, sessionHibernate);

            Common.addAttribute(request, "purchases", purchases);

            request.getRequestDispatcher("/WEB-INF/historic-detail.jsp").forward(request, response);
        } catch (StorageException ex) {
            //TODO: do something
        }

        if (sessionHibernate != null) {
            sessionHibernate.close();
        }
    }

    private void renderPurchaseDetail(HttpServletRequest request, HttpServletResponse response,
            int purchaseId)
            throws ServletException, IOException {

        // Recover details from last purchase
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        try {
            Purchases p = PurchaseController.getPurchase(sessionHibernate, purchaseId);
            Set<Details> details = null;

            if (p != null) {
                details = p.getDetailses();
            }

            Common.addAttribute(request, "details", details);

            request.getRequestDispatcher("/WEB-INF/view-purchase.jsp").forward(request, response);
        } catch (StorageException ex) {
            //TODO: do something
        }

        if (sessionHibernate != null) {
            sessionHibernate.close();
        }
    }

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

        // Check if admin user is logged
        if (!Common.adminIsLogged(request)) {
            response.sendRedirect("inicio");
            return;
        }

        //TODO: move this into specific servlets avoiding if statement
        String userIdReceived     = request.getParameter("usuario"),
               purchaseIdReceived = request.getParameter("compra");

        if (userIdReceived != null) {

            int userId;
            // Default to current user when ID is wrong or missing
            // Why? because we don't have an ERROR message *yet*
            if (!userIdReceived.isEmpty()) {
                userId = Integer.parseInt(userIdReceived);
            } else {
                userId = Common.getSessionUser(request).getIdUser();
            }

            renderHistoryDetail(request, response, userId);

        } else if (purchaseIdReceived != null) {

            int purchaseId = Integer.parseInt(purchaseIdReceived);
            renderPurchaseDetail(request, response, purchaseId);

        } else {
            renderHistoryList(request, response);
        }
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
