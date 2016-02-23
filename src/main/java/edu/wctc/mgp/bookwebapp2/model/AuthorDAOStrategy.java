/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Matthew_2
 */
public interface AuthorDAOStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;

    public int deleteAuthorbyID(Object primaryKeyValue) throws SQLException, ClassNotFoundException;

    public int updatebyID(Author author) throws SQLException;

    public int addAuthor(Author author) throws SQLException;
    
    public DBStrategy getDb();
    
    public void setDb(DBStrategy db);
}
