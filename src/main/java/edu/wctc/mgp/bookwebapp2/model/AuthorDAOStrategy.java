/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.model;

import edu.wctc.mgp.bookwebapp2.exception.DataAccessException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Matthew_2
 */
public interface AuthorDAOStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException, DataAccessException;

    public int deleteAuthorbyID(Object primaryKeyValue) throws SQLException,DataAccessException, ClassNotFoundException;

    public int deleteAuthorsbyIDs(List<Object> primaryKeyValues) throws ClassNotFoundException,DataAccessException, SQLException;

    public int updatebyID(Author author) throws SQLException;

    public boolean addAuthor(Author author) throws SQLException;

    public Author getAuthorById(int authorId) throws DataAccessException, ClassNotFoundException, SQLException;

    public DBStrategy getDb();

    public void setDb(DBStrategy db) throws NullPointerException;
    
    public abstract void initDao(DataSource ds) throws DataAccessException;

    public abstract void initDAO(String driver, String url, String user, String pwd) throws DataAccessException;


    public void setPwd(String pwd) throws NullPointerException;

    public String getPwd();

    public void setUser(String user) throws NullPointerException;

    public String getUser();

    public void setUrl(String url) throws NullPointerException;

    public String getUrl();

    public void setDriver(String driver) throws NullPointerException;

    public String getDriver();

    public boolean saveAuthor(Integer authorId, String authorName) throws DataAccessException, SQLException, ClassNotFoundException;
}
