/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.controller;


import edu.wctc.mgp.bookwebapp2.entity.Author;
import edu.wctc.mgp.bookwebapp2.entity.Book;
import edu.wctc.mgp.bookwebapp2.service.AuthorService;
import edu.wctc.mgp.bookwebapp2.service.BookService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

   private BookService bs;
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

                        case ADD_ACTION:
                            this.retrieveAuthorList(request, as);
                            pageDestination = BOOK_ADD_VIEW;
                            break;
                        case EDIT_ACTION:

                            String bookId = bookIds[0];
                            book = bs.findById(bookId);
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
                                    book = bs.findById(id);
                                    bs.remove(book);

                                }

                            } else if (Boolean.valueOf(deleteType) == true) {
                                book = bs.findById(bookID);
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
                            author = as.findById(authorId);
                            book.setAuthorId(author);
                        }
                        // edit 
                    } else {

                        book = bs.findById(bookId);
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if (authorId != null) {
                            author = as.findById(authorId);
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
                    request.setAttribute("errorMsg", PARAM_ERROR_MSG);
                    pageDestination = BOOK_RESP_VIEW;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg",e.getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(pageDestination);
        dispatcher.forward(request, response);

    }

    // Avoid D-R-Y
    private void retrieveBookList(HttpServletRequest request, BookService bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }

    private void retrieveAuthorList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
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
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        as = (AuthorService) ctx.getBean("authorService");
        bs = (BookService) ctx.getBean("bookService");

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
