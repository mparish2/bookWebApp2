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
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;

import javax.inject.Inject;

/**
 *
 * @author Matthew_2
 */
@Dependent
public class AuthorDAO implements AuthorDAOStrategy, Serializable {

    @Inject
    private DBStrategy db;
    
    private String driver;
    private String url;
    private String user;
    private String pwd;

    private static final String TABLE_NAME = "author";
    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR_NAME = "author_name";
    private static final String DATE_ADDED = "date_added";

    public AuthorDAO() {
    }
    
    
     @Override
    public Author getAuthorById(int authorId)  throws DataAccessException, ClassNotFoundException, SQLException{
       db.openConnection(driver, url, user, pwd);
        
        Map<String,Object> sinRec = db.findById(TABLE_NAME,AUTHOR_ID, authorId);
        Author author = new Author();
        author.setAuthorId((Integer)sinRec.get("author_id"));
        author.setAuthorName(sinRec.get("author_name").toString());
        author.setDateAdded((Date)sinRec.get("date_added"));
        
        return author;
    }
    
    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {

        db.openConnection(driver, url, user, pwd); //rigid for now. will fix later

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
        db.openConnection(driver, url, user, pwd);
        int numAuthor = db.deleteRecordbyPrimaryKey(TABLE_NAME, AUTHOR_ID, primaryKeyValue);
        db.closeConnection();
        return numAuthor;
    }

    @Override
    public int deleteAuthorsbyIDs(List<Object> primaryKeyValues) throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, pwd);
        int numAuthors = db.deleteRecordsbyPrimaryKey(TABLE_NAME, AUTHOR_ID, primaryKeyValues);
        db.closeConnection();
        return numAuthors;
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
            db.openConnection(driver, url, user, pwd);
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
    public boolean addAuthor(Author author) throws SQLException{
        try {
            db.openConnection(driver, url, user, pwd);
            List<String> authorColumns = Arrays.asList(AUTHOR_NAME, DATE_ADDED);;
            List<Object> authorValues = Arrays.asList(author.getAuthorName(), author.getDateAdded());
            boolean numAuthor = db.insertRecord(TABLE_NAME, authorColumns, authorValues);
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
    public boolean saveAuthor(Integer authorId, String authorName) throws DataAccessException, SQLException, ClassNotFoundException {
        db.openConnection(driver, url, user, pwd);
        boolean result = false;
        
        if(authorId == null || authorId.equals(0)) {
            result = db.insertRecord("author", Arrays.asList("author_name","date_added"), 
                                      Arrays.asList(authorName,new Date()));
        } else {
            // must be an update of an existing record
            int recsUpdated = db.updatebyID("author", Arrays.asList("author_name"), 
                                       Arrays.asList(authorName),
                                       "author_id", authorId);
            if(recsUpdated > 0) {
                result = true;
            }
        }
        return result;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) throws NullPointerException{
        if(driver == null || driver.isEmpty()){
            throw new NullPointerException();
        }
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws NullPointerException {
        if(url == null || url.isEmpty()){
            throw new NullPointerException();
        }
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) throws NullPointerException {
        if(user == null || user.isEmpty()){
            throw new NullPointerException();
        }
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) throws NullPointerException {
        if(pwd == null || pwd.isEmpty()){
            throw new NullPointerException();
        }
        this.pwd = pwd;
    }
    
    
    @Override
    public DBStrategy getDb() {
        return db;
    }

    @Override
    public void setDb(DBStrategy db) throws NullPointerException{
        if(db == null){
            throw new NullPointerException();
        }
        this.db = db;
    }

     @Override
    public void initDAO(String driver, String url, String user, String pwd) {
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(pwd);
        //needs validation later (best practices)
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        AuthorDAOStrategy dao = new AuthorDAO();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);

//        List<Object> primaryKeyValues = new ArrayList();
//         primaryKeyValues.add("71");
//         primaryKeyValues.add("72");
//         int result = dao.deleteAuthorsbyIDs(primaryKeyValues);
        
//          List<Author> authors2 = dao.getAuthorList();
//        System.out.println(authors2);
//        Author testAuthor = new Author("Matthew Parish", new Date());
//        boolean test = dao.addAuthor(testAuthor);
//        System.out.println(test);
    }

   

    

   

  

}
