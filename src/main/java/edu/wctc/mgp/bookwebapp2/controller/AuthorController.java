/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.controller;

import edu.wctc.mgp.bookwebapp2.exception.DataAccessException;
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
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Matthew_2
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    //add more constants later
    private static final String AUTHOR_RESP_VIEW = "/AuthorResponse.jsp";
    private static final String AUTHOR_ADD_VIEW = "/Add.jsp";
    private static final String AUTHOR_EDIT_VIEW = "/Edit.jsp";
    private static final String VIEWAUTHORS_ACTION = "viewAuthors";
    private static final String ADD_EDIT_DEL_PARM = "AEDAuthor";
    private static final String EDIT_ACTION = "Edit";
    private static final String DELETE_ACTION = "Delete";
    private static final String ADD_ACTION = "Add";
    private static final String SAVE_ACTION = "Save";
    private static final String CANCEL_ACTION = "Cancel";
    private static final String PARAM_ERROR_MSG = "No or Wrong parameter identified";
    private static final String LOGIN = "Login";
    private static final String LOGOUT = "Logout";
    
    private String driverClass;
    private String url;
    private String userName;
    private String pwd;
    private String dbJndiName;

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
        HttpSession session = request.getSession();
        try {
            String taskType = request.getParameter("taskType");
            String[] authorIds = request.getParameterValues("authorId");

            //uses init parameters to config database connection
            configDBConnection();

            switch (taskType) {
                case LOGIN:
                    String message = request.getParameter("loginTxtBox");
                    session.setAttribute("message", message);
                    destination = "/index.jsp";
                    break;
                case LOGOUT:
                    session.invalidate();
                    destination = "/index.jsp";
                    break;
                case VIEWAUTHORS_ACTION:
                    //this to retrieve from the private method belonging to the controller
                    this.retrieveList(request, as);
                    destination = AUTHOR_RESP_VIEW;
                    break;
                case ADD_EDIT_DEL_PARM:
                    String subTaskType = request.getParameter("submit");
                    switch (subTaskType) {
                        case DELETE_ACTION:
                            String authorID = request.getParameter("authorId");
                            String deleteType = request.getParameter("my-checkbox");
                            //or create a new multidelete method w/ a buildDeleteStatement (w/ a "in")
                            List<Object> ids = new ArrayList();
                            if (Boolean.valueOf(deleteType) == false) { //multi

                                //ids.addAll(Arrays.asList(authorIds));
                                for (Object id : authorIds) {
                                    ids.add(id);
                                }
                                as.deleteAuthorsbyIDs(ids);
                                this.retrieveList(request, as);
                                destination = AUTHOR_RESP_VIEW;

                            } else if (Boolean.valueOf(deleteType) == true) {//single

                                as.deleteAuthorbyID(authorID);
                                this.retrieveList(request, as);
                                destination = AUTHOR_RESP_VIEW;
                            }
                            break;
                        case EDIT_ACTION:
                            String authorId = authorIds[0]; //first of checked
                            Author author = as.getAuthorById(authorId);
                            request.setAttribute("author", author);
                            destination = AUTHOR_EDIT_VIEW;
                            break;
                        case ADD_ACTION:
                            destination = AUTHOR_ADD_VIEW;
                            break;
                        default:
                            break;
                    }
                    break;
                case CANCEL_ACTION:
                    this.retrieveList(request, as);
                    destination = AUTHOR_RESP_VIEW;
                    break;
                case SAVE_ACTION:
                    String authorName = request.getParameter("authorName");
                    String authorId = request.getParameter("authorId");
                    as.saveAuthor(authorId, authorName);
                    this.retrieveList(request, as);
                    destination = AUTHOR_RESP_VIEW;
                    break;
                default:
                    request.setAttribute("errorMsg", PARAM_ERROR_MSG);
                    destination = AUTHOR_RESP_VIEW;
                    break;
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

    private void configDBConnection() throws DataAccessException, NamingException {
        if(dbJndiName == null) {
            as.getDao().initDAO(driverClass, url, userName, pwd);
        } else{
             Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            as.getDao().initDao(ds);
        }
    }

    @Override
    public void init() throws ServletException {
//        driverClass = getServletContext().getInitParameter("db.driver.class");
//        url = getServletContext().getInitParameter("db.url");
//        userName = getServletContext().getInitParameter("db.username");
//        pwd = getServletContext().getInitParameter("db.password");
        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
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
