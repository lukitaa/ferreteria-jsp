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
import java.io.PrintWriter;
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
            response.sendRedirect("/Ferreteria/login");
            return;
        }

        SessionUser session = Common.getSessionUser(request);
        ShoppingCart shoppingCart = Common.getCart(request);

        //TODO: get products to buy from POST
        ShoppingCart purchaseDetails = Common.getCart(request);
        boolean error = false;
        int purchaseTotal = 0;

        List<Details> details = new ArrayList();

        if (purchaseDetails == null) {
            response.sendRedirect("/Ferreteria/productos");
            return;
        }

        try {
            details = PurchaseController.purchaseProducts(purchaseDetails, session.getIdUser());

            for (Details d : details) {
                purchaseTotal += d.getAmount() * d.getPrice();
            }

            //CLEAR THE SESSION WITH PRODUCTS
            Common.destroyCart(request);
        } catch (StorageException ex) {
            error = true;
        } catch (InvalidParameterException ex) {
            error = true;
        }

        // Check if there was an error or nothing has been bought
        if (error || details.size() <= 0) {
            response.sendRedirect("/Ferreteria/productos");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(new templates.PurchaseTemplate(details, purchaseTotal).printPage("DetallesCompra", session, shoppingCart));
        } finally {
            out.close();
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
