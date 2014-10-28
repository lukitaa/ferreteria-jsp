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

import controllers.ProductsController;
import controllers.StorageException;
import entity.Products;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author usuario
 */
public class EditProductServlet extends HttpServlet {


    private void renderEditProductPage(HttpServletRequest request, HttpServletResponse response,
            int productId) throws IOException, ServletException {

        Products product = null;
        try {
            product = ProductsController.getProduct(productId);
        } catch (StorageException ex) {
            Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        Common.addAttribute(request, "product", product);

        // Render page
        request.getRequestDispatcher("/WEB-INF/edit-product.jsp").forward(request, response);
    }


    private void renderEditedProductPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String productName = request.getParameter("product-name");
        int productId      = Integer.parseInt(request.getParameter("product-id")),
            productPrice   = Integer.parseInt(request.getParameter("product-precio")),
            productStock   = Integer.parseInt(request.getParameter("product-stock"));

        boolean success = false;

        // Obtener los datos antiguos para pasar a la controladora junto a los nuevos y editar.
        Products oldProduct = null;
        try {
            oldProduct = ProductsController.getProduct(productId);

            if(oldProduct != null) {
                ProductsController.updateProduct(oldProduct, productName, productPrice, productStock);
                success = true;
            }
        }
        catch (StorageException ex) { }

        // Render page
        request.getRequestDispatcher("/WEB-INF/edited-product.jsp?success="+success).forward(request, response);
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


        // An admin must be logged in to access this page!
        if (!Common.adminIsLogged(request)) {
            response.sendRedirect("../../inicio");
            return;
        }


        String productIdReceived = request.getParameter("id");

        if (productIdReceived != null) {

            // Product has been selected for edit
            int productId = Integer.parseInt(productIdReceived);
            renderEditProductPage(request, response, productId);

        } else {
            // Fuck him if this doesn't want to be loaded
            renderEditedProductPage(request, response);
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
