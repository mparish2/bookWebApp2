/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Matthew_2
 */
@SessionScoped
public class AuthorDAO implements AuthorDAOStrategy, Serializable {

    @Inject
    private DBStrategy db;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PWD = "admin";

    private static final String TABLE_NAME = "author";
    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR_NAME = "author_name";
    private static final String DATE_ADDED = "date_added";

    public AuthorDAO() {
    }
    
    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {

        db.openConnection(DRIVER, URL, USER, PWD); //rigid for now. will fix later

        //turning a listOmaps to listOauthors
        List<Map<String, Object>> rawData = db.findAllRecords(TABLE_NAME, 0);
        List<Author> authors = new ArrayList<>();

        for (Map rec : rawData) {
            Author author = new Author();
            Integer id = Integer.valueOf(rec.get(AUTHOR_ID).toString());
            author.setAuthorId(id);
            String name = rec.get(AUTHOR_NAME) == null ? "" : rec.get(AUTHOR_NAME).toString();
            author.setAuthorName(name);
            Date date = rec.get(DATE_ADDED) == null ? null : (Date) rec.get(DATE_ADDED);
            author.setDateAdded(date);

            authors.add(author);
        }

        db.closeConnection();
        return authors;
    }

    /**
     *
     * @param primaryKeyValue
     * @return 0 or 1
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public int deleteAuthorbyID(Object primaryKeyValue) throws SQLException, ClassNotFoundException {
        db.openConnection(DRIVER, URL, USER, PWD);
        int numAuthor = db.deleteRecordbyPrimaryKey(TABLE_NAME, AUTHOR_ID, primaryKeyValue);
        db.closeConnection();
        return numAuthor;
    }

    /**
     *
     * @param author
     * @return 0 or 1
     * @throws SQLException
     */
    @Override
    public int updatebyID(Author author) throws SQLException {
        try {
            db.openConnection(DRIVER, URL, USER, PWD);
            List<String> authorColumns = Arrays.asList(AUTHOR_NAME, DATE_ADDED);;
            List<Object> authorValues = Arrays.asList(author.getAuthorName(), author.getDateAdded());
            int numAuthor = db.updatebyID(TABLE_NAME, authorColumns, authorValues, AUTHOR_ID, author.getAuthorId());
            return numAuthor;
        } catch (SQLException sqlE) {
            throw sqlE;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            db.closeConnection();
        }

    }
    
    
    @Override
    public int addAuthor(Author author) throws SQLException{
        try {
            db.openConnection(DRIVER, URL, USER, PWD);
            List<String> authorColumns = Arrays.asList(AUTHOR_NAME, DATE_ADDED);;
            List<Object> authorValues = Arrays.asList(author.getAuthorName(), author.getDateAdded());
            int numAuthor = db.insertRecord(TABLE_NAME, authorColumns, authorValues);
            return numAuthor;
        } catch (SQLException sqlE) {
            throw sqlE;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            db.closeConnection();
        }
    }

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        AuthorDAOStrategy dao = new AuthorDAO();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);

        Author testAuthor = new Author("Matthew Parish", new Date());
       int test = dao.addAuthor(testAuthor);
        System.out.println(test);
    }

}
