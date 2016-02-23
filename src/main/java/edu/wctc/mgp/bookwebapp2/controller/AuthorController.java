/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.wctc.mgp.bookwebapp2.model.Author;
import edu.wctc.mgp.bookwebapp2.model.AuthorService;
import edu.wctc.mgp.bookwebapp2.model.MockAuthorDAO;
import javax.inject.Inject;

/**
 *
 * @author Matthew_2
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String AUTHOR_RESP_VIEW = "AuthorResponse.jsp";
    private static final String DELETE_RESP_VIEW = "DeleteAuthorResponse.jsp";
    
    @Inject
    private AuthorService as;
    
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            String taskType = request.getParameter("taskType");
            

            if (taskType.equals("viewAuthor")) {
                List<Author> authors = null;
                authors = as.getAuthorList();
                request.setAttribute("authors", authors);
                RequestDispatcher view = request.getRequestDispatcher(AUTHOR_RESP_VIEW);
                view.forward(request, response);
            }
           else if (taskType.equals("deleteAuthor")) {
               String authorId = request.getParameter("authorId");
               request.setAttribute("authorIdResp",as.deleteAuthorbyID(authorId));
                RequestDispatcher view2 = request.getRequestDispatcher(DELETE_RESP_VIEW);
                view2.forward(request, response);
           }

        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getMessage());
        }

//        RequestDispatcher view = request.getRequestDispatcher(AUTHOR_RESP_VIEW);
//                view.forward(request,response);
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
