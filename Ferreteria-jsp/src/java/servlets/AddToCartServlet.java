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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alumno
 */
@WebServlet(name = "AddToCart", urlPatterns = {"/carrito"})
public class AddToCartServlet extends HttpServlet {


    public void updateCart(ShoppingCart shoppingCart, Integer productId, Integer productAmount) {
        boolean exists = false;

        //Go trought the products' list searching if
        // the product was already in the cart to
        // increment the amount of unities to buy
        for(int i = 0, length = shoppingCart.getProductsId().size(), aux; i < length; i++) {
            //If it's in, add the new amount and the older one
            if(shoppingCart.getProductsId().get(i).equals(productId)) {
                aux = shoppingCart.getProductsAmount().get(i) + productAmount;
                shoppingCart.getProductsAmount().set(i, aux);
                exists = true;
                break;
            }
        }

        if(!exists) {
            shoppingCart.getProductsId().add(productId);
            shoppingCart.getProductsAmount().add(productAmount);
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

        // User must be logged in to access this page!
        if (!Common.userIsLogged(request)) {
            response.sendRedirect("/Ferreteria-jsp/login");
            return;
        }


        ShoppingCart shoppingCart = Common.getCart(request);

        // Check if this is the first item to add into the cart
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            Common.generateCart(request, shoppingCart);
        }

        // Get the amount of unities for the product from the request parameters
        String receivedProductId     = request.getParameter("product-id"),
               receivedProductAmount = request.getParameter("product-stock");

        if (receivedProductId != null && receivedProductAmount != null
                && !receivedProductId.isEmpty() && !receivedProductAmount.isEmpty()) {
            int productId     = Integer.valueOf(receivedProductId),
                productAmount = Integer.valueOf(receivedProductAmount);

            if (productAmount > 0)
                updateCart(shoppingCart, productId, productAmount);
        }

        response.sendRedirect("products.jsp");
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
