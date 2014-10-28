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
import entity.Details;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class DetailsServlet extends HttpServlet {

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

        // Check if user is logged
        if (!Common.userIsLogged(request)) {
            response.sendRedirect("login");
            return;
        }

        ShoppingCart shoppingCart = Common.getCart(request);

        // Check if they are products, otherwise show products
        if (shoppingCart == null || shoppingCart.getProductsId().isEmpty()) {
            response.sendRedirect("productos");
            return;
        }

        boolean errorLoadingData = false;

        try {

            List<Details> details = PurchaseController.getDetailsFromCart(shoppingCart);

            // Check if there is something to buy, otherwise exit
            if (details == null) {
                response.sendRedirect("productos");
                return;
            }

            // Store details on session to be processed on view
            //Common.generatePurchaseDetails(request, details);
            Common.addAttribute(request, "purchaseDetails", details);

        } catch (InvalidParameterException ex) {
            errorLoadingData = true;
        } catch (StorageException ex) {
            errorLoadingData = true;
        }

        request.getRequestDispatcher("/WEB-INF/details.jsp" + ((errorLoadingData) ? "?error=1" : "")).forward(request, response);
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
