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
import javax.enterprise.context.Dependent;

import javax.enterprise.inject.Alternative;

/**
 *
 * @author Matthew_2
 */
@Alternative
@Dependent
public class MockAuthorDAO implements AuthorDAOStrategy,Serializable{
    
    private DBStrategy db;
    
    private List<Author> authorList = new ArrayList<>();
   
    Author a1 = new Author(0,"Bob",new Date(0163,4,1));  
    Author a2 = new Author(1,"Bill",new Date(0161,4,2));
    Author a3 = new Author(2,"Jane",new Date(0162,4,5));
    
    /**
     * method getAllAuthor
     * @return the author list
     */
    @Override
    public List<Author> getAuthorList(){
        
        authorList.add(a1);
        authorList.add(a2);
        authorList.add(a3);
        
        return authorList;
    }

    @Override
    public int deleteAuthorbyID(Object primaryKeyValue) throws SQLException, ClassNotFoundException {
        return 1;
    }

    @Override
    public int updatebyID(Author a) throws SQLException {
        return 1;
    }

    @Override
    public boolean addAuthor(Author author) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBStrategy getDb() {
       return db;
    }

    @Override
    public void setDb(DBStrategy db) {
        this.db = db;
    }

    @Override
    public Author getAuthorById(int authorId) throws DataAccessException, ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initDAO(String driver, String url, String user, String pwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPwd(String PWD) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPwd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUser(String USER) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUrl(String URL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUrl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDriver(String DRIVER) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDriver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveAuthor(Integer authorId, String authorName) throws DataAccessException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
