/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.controller;

import edu.wctc.mgp.bookwebapp2.ejb.AbstractFacade;
import edu.wctc.mgp.bookwebapp2.model.Author;
import edu.wctc.mgp.bookwebapp2.model.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Matthew_2
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    private static final String BOOK_RESP_VIEW = "/BookResponse.jsp";
    private static final String BOOK_ADD_VIEW = "/BookAdd.jsp";
    private static final String BOOK_EDIT_VIEW = "/BookEdit.jsp";
    private static final String VIEWBOOKS_ACTION = "viewBooks";
    private static final String ADD_EDIT_DEL_PARM = "AEDBooks";
    private static final String EDIT_ACTION = "Edit";
    private static final String DELETE_ACTION = "Delete";
    private static final String ADD_ACTION = "Add";
    private static final String SAVE_ACTION = "Save";
    private static final String CANCEL_ACTION = "Cancel";
    private static final String PARAM_ERROR_MSG = "No or Wrong parameter identified";
    //  private static final String LOGIN = "Login";
    // private static final String LOGOUT = "Logout"; */

    @Inject
    private AbstractFacade<Book> bs;
    @Inject
    private AbstractFacade<Author> as;

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

        String pageDestination = BOOK_RESP_VIEW;
        String taskType = request.getParameter("taskType");
        Book book = null;

        try {

            switch (taskType) {
                case VIEWBOOKS_ACTION:
                    this.retrieveBookList(request, bs);
                    this.retrieveAuthorList(request, as);
                    pageDestination = BOOK_RESP_VIEW;
                    break;

                case ADD_EDIT_DEL_PARM:
                    String subTaskType = request.getParameter("submit");

                    String[] bookIds = request.getParameterValues("bookId");
                    switch (subTaskType) {
                        // must be add or edit, go to addEdit page

                        case (ADD_ACTION):

                            this.retrieveAuthorList(request, as);
                            pageDestination = BOOK_ADD_VIEW;
                            break;
                        case EDIT_ACTION:

                            String bookId = bookIds[0];
                            book = bs.find(new Integer(bookId));
                            request.setAttribute("book", book);
                            this.retrieveAuthorList(request, as);

                            pageDestination = BOOK_EDIT_VIEW;
                            break;

                        case DELETE_ACTION:
                            // DELETE
                            // get array based on records checked
                            //  String[] bookIds = request.getParameterValues("bookId");
                            String deleteType = request.getParameter("my-checkbox");
                            String bookID = request.getParameter("bookId");

                            if (Boolean.valueOf(deleteType) == false) {

                                for (String id : bookIds) {
                                    book = bs.find(new Integer(id));
                                    bs.remove(book);

                                }

                            } else if (Boolean.valueOf(deleteType) == true) {
                                book = bs.find(new Integer(bookID));
                                bs.remove(book);

                            }
                            this.retrieveBookList(request, bs);
                            this.retrieveAuthorList(request, as);
                            pageDestination = BOOK_RESP_VIEW;

                            break;
                    }

                    break;

                case SAVE_ACTION:
                    String title = request.getParameter("title");
                    String isbn = request.getParameter("isbn");
                    String authorId = request.getParameter("authorId");
                    String bookId = request.getParameter("bookId");

                    //  add
                    if (bookId == null) {
                        book = new Book(0);
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if (authorId != null) {
                            author = as.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                        // edit 
                    } else {

                        book = bs.find(new Integer(bookId));
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if (authorId != null) {
                            author = as.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                    }
                    //JPA edit to save / make the actual changes
                    bs.edit(book);
                    this.retrieveBookList(request, bs);
                    this.retrieveAuthorList(request, as);
                    pageDestination = BOOK_RESP_VIEW;
                    break;

                case CANCEL_ACTION:
                    this.retrieveBookList(request, bs);
                    this.retrieveAuthorList(request, as);
                    pageDestination = BOOK_RESP_VIEW;
                    break;

                default:
                    request.setAttribute("errMsg", PARAM_ERROR_MSG);
                    pageDestination = BOOK_RESP_VIEW;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(pageDestination);
        dispatcher.forward(request, response);

    }

    // Avoid D-R-Y
    private void retrieveBookList(HttpServletRequest request, AbstractFacade<Book> bookService) throws Exception {
        List<Book> books = bs.findAll();
        request.setAttribute("books", books);
    }

    private void retrieveAuthorList(HttpServletRequest request, AbstractFacade<Author> authService) throws Exception {
        List<Author> authors = as.findAll();
        request.setAttribute("authors", authors);
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
    }
}
