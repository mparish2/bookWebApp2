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

/**
 *
 * @author Matthew_2
 */
public interface AuthorDAOStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;

    public int deleteAuthorbyID(Object primaryKeyValue) throws SQLException, ClassNotFoundException;

    public int updatebyID(Author author) throws SQLException;

    public boolean addAuthor(Author author) throws SQLException;

    public Author getAuthorById(int authorId) throws DataAccessException, ClassNotFoundException, SQLException;

    public DBStrategy getDb();

    public void setDb(DBStrategy db);

    public void initDAO(String driver, String url, String user, String pwd);

    public void setPwd(String PWD);

    public String getPwd();

    public void setUser(String USER);

    public String getUser();

    public void setUrl(String URL);

    public String getUrl();

    public void setDriver(String DRIVER);

    public String getDriver();
    
    public boolean saveAuthor(Integer authorId, String authorName) throws DataAccessException, SQLException, ClassNotFoundException;
}
