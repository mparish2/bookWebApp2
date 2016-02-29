/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.model;

import edu.wctc.mgp.bookwebapp2.exception.DataAccessException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

    /**
     * Default Constructor
     */
    public AuthorService(){
        
    }
                                
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

    public int deleteAuthorsbyIDs(List<Object> primaryKeyValues) throws ClassNotFoundException, SQLException{
        return dao.deleteAuthorsbyIDs(primaryKeyValues);
    }
    
    public void saveAuthor(String authorId, String authorName) throws DataAccessException, SQLException, ClassNotFoundException{
        Integer id = null; 
        if(authorId == null || authorId.isEmpty()) {
                id = null;
            } else {
                id = Integer.parseInt(authorId);
            }   
        dao.saveAuthor(id, authorName);
    }
    public boolean addAuthor(Author author) throws SQLException {
        return dao.addAuthor(author);
    }

    public AuthorDAOStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDAOStrategy dao) throws NullPointerException {
         if(dao == null){
            throw new NullPointerException();
        }
        this.dao = dao;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);

//        Author testAuthor = new Author(6, "Mark Twain", new Date());
//        srv.addAuthor(testAuthor);

        
         
        
        List<Author> authors2 = srv.getAuthorList();
        System.out.println(authors2);
        
    }

}
