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

    //add more constants later
    private static final String AUTHOR_RESP_VIEW = "/AuthorResponse.jsp";
    //private static final String DELETE_RESP_VIEW = "DeleteAuthorResponse.jsp";
    private static final String AUTHOR_ADD_VIEW = "/Add.jsp";
    private static final String AUTHOR_EDIT_VIEW = "/Edit.jsp";
    
    private String driverClass;
    private String url;
    private String userName;
    private String pwd;
    
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
        String destination = AUTHOR_RESP_VIEW;
        try {
            String taskType = request.getParameter("taskType");
            String[] authorIds = request.getParameterValues("authorId");
            
            //uses init parameters to config database connection
            configDBConnection();
            
            if (taskType.equals("viewAuthors")) {
                //this to retrieve from the private method belonging to the controller
                this.retrieveList(request, as);
                destination = AUTHOR_RESP_VIEW;

            } else if (taskType.equals("AEDAuthor")) {
                String subTaskType = request.getParameter("submit");
                if (subTaskType.equals("Delete")) {
                   
                    //or create a new multidelete method w/ a buildDeleteStatement (w/ a "in")
                    for (String id : authorIds) {
                            as.deleteAuthorbyID(id);
                        }
                    
                    this.retrieveList(request, as);
                    destination = AUTHOR_RESP_VIEW;
                    
                }else if(subTaskType.equals("Edit")){
                    String authorId = authorIds[0]; //first of checked
                    Author author = as.getAuthorById(authorId);
                    request.setAttribute("author", author);
                    destination = AUTHOR_EDIT_VIEW;
                } else if(subTaskType.equals("Add")){
                    destination = AUTHOR_ADD_VIEW;
                }
            }
            else if(taskType.equals("Cancel")){
                this.retrieveList(request, as);
                destination = AUTHOR_RESP_VIEW;
            }
            else if(taskType.equals("Save")){
                 String authorName = request.getParameter("authorName");
                 String authorId = request.getParameter("authorId");
                 as.saveAuthor(authorId, authorName);
                 this.retrieveList(request, as);
                 destination = AUTHOR_RESP_VIEW;
            }
           
        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getMessage());
        }

        RequestDispatcher view = request.getServletContext().getRequestDispatcher(destination);
        view.forward(request, response);
    }

    //not repeating code
    //needed to set the retreieved authorlist, needed to get all of the authors
    private void retrieveList(HttpServletRequest request, AuthorService as) throws Exception {
        List<Author> authors = as.getAuthorList();
        request.setAttribute("authors", authors);
    }
    
    private void configDBConnection(){
        as.getDao().initDAO(driverClass, url, userName, pwd);
    }
    
    @Override
    public void init() throws ServletException{
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        pwd = getServletContext().getInitParameter("db.password");
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
