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

import controllers.InvalidParameterException;
import controllers.PurchaseController;
import controllers.StorageException;
import entity.Purchases;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class GenerateOrderServlet extends HttpServlet {

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

        // An admin must be logged in to access this page!
        if (!Common.adminIsLogged(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get product ID from parameter
        String purchaseIdReceived = request.getParameter("pedido");
        boolean success = false;

        if (purchaseIdReceived != null && !purchaseIdReceived.isEmpty()) {
            try {
                int purchaseId = Integer.parseInt(purchaseIdReceived);

                // Recover the purchase
                Purchases p = PurchaseController.getPurchase(purchaseId);
                // Update the value to ordered
                p.setDone(true);
                // Store the changes
                PurchaseController.udpatePurchase(p);

                success = true;

            } catch (StorageException ex) {//TODO: do something
                Logger.getLogger(GenerateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidParameterException ex) {
                Logger.getLogger(GenerateOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch(NumberFormatException e) {
                // I don't care about assholes
            }
        }

        request.getRequestDispatcher("/WEB-INF/generate-order.jsp"+((!success)?"?error=1":"")).forward(request, response);
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
