/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.model;

import edu.wctc.mgp.bookwebapp2.exception.DataAccessException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Matthew_2
 */
@SessionScoped
public class AuthorService implements Serializable{

    @Inject
    private AuthorDAOStrategy dao;

                                
    public Author getAuthorById(String authorId)throws DataAccessException, ClassNotFoundException, SQLException{
       return dao.getAuthorById(Integer.parseInt(authorId));//needs to be int lower down
    }
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        return dao.getAuthorList();
    }

    public int deleteAuthorbyID(Object primaryKeyValue) throws SQLException, ClassNotFoundException {
        return dao.deleteAuthorbyID(primaryKeyValue);
    }

    public int updateAuthorbyId(Author author) throws SQLException {
        return dao.updatebyID(author);
    }

    public int addAuthor(Author author) throws SQLException {
        return dao.addAuthor(author);
    }

    public AuthorDAOStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDAOStrategy dao) {
        this.dao = dao;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);

        Author testAuthor = new Author(6, "Mark Twain", new Date());
        srv.addAuthor(testAuthor);

        List<Author> authors2 = srv.getAuthorList();
        System.out.println(authors2);
        
    }

}
