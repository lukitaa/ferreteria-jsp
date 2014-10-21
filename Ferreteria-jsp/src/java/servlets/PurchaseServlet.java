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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alumno
 */
public class PurchaseServlet extends HttpServlet {


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
            response.sendRedirect("/Ferreteria-jsp/login");
            return;
        }

        SessionUser session = Common.getSessionUser(request);

        //TODO: get products to buy from POST
        List<Details> purchaseDetails = Common.getPurchaseDetails(request);
        boolean error = false;

        //List<Details> details = new ArrayList();

        // Check if they are products, otherwise exit
        if (purchaseDetails == null) {
            response.sendRedirect("/Ferreteria-jsp/products.jsp");
            return;
        }

        try {
            purchaseDetails = PurchaseController.purchaseProducts(purchaseDetails, session.getIdUser());

            //CLEAR THE SESSION WITH PRODUCTS
            //Common.destroyCart(request);
            // NOTE: this has been moved into the JSP page
        } catch (StorageException ex) {
            error = true;
        } catch (InvalidParameterException ex) {
            error = true;
        }

        // Check if there was an error or nothing has been bought
        if (error || purchaseDetails.size() <= 0) {
            //TODO: display an error message
            response.sendRedirect("/Ferreteria-jsp/products.jsp");
            return;
        }

        // Add details into session to pass it to the view
        //Common.generatePurchaseDetails(request, details);

        response.sendRedirect("../purchase.jsp");
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
