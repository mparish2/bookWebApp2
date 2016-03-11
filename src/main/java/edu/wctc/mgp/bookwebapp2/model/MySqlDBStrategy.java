/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.model;

import edu.wctc.mgp.bookwebapp2.exception.DataAccessException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;


/**
 *
 * @author Matthew_2
 */
@Dependent
public class MySqlDBStrategy implements DBStrategy,Serializable {

    private Connection conn;

    public MySqlDBStrategy() {
    }

    //what would be better this or bring it in from the construct. think about/research
    
      /**
     * Open a connection using a connection pool configured on server.
     *
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws DataAccessException - if ds cannot be established
     */
    @Override
    public final void openConnection(DataSource ds) throws DataAccessException {
        try {
            conn = ds.getConnection();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(),ex.getCause());
        }
    }
    
    /**
     *
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }
    /**
     * 
     * @param tableName
     * @param primaryKeyFieldName
     * @param primaryKeyValue
     * @return
     * @throws DataAccessException 
     */
     @Override
    public final Map<String, Object> findById(String tableName, String primaryKeyFieldName,
            Object primaryKeyValue) throws DataAccessException {

        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = null;
        final Map<String, Object> record = new HashMap();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, primaryKeyValue);
            ResultSet rs = stmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            // Retrieve the raw data from the ResultSet and copy the values into a Map
            // with the keys being the column names of the table.
            if (rs.next()) {
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
            }
            
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        } // end finally

        return record;
    }

    /**
     * Make sure you open and close connection when using this method. Future
     * optimization may include change the return type an array.
     *
     * @param maxRecords - limits records fount to first maxRecords or if
     * maxRecords is zero (0) then no limit.
     * @param tableName
     * @return
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {

        String sql;
        if (maxRecords < 1) {
            sql = "select * from " + tableName;
        } else {
            sql = "select * from " + tableName + " limits " + maxRecords;
        }

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        /*provides information about the table. */
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> records = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int colNo = 1; colNo <= columnCount; colNo++) {  //(orig had < "" + 1) <= or == ask Mr.Lombardo
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnName(colNo);
                record.put(colName, colData);
            }
            records.add(record);
        }
        return records;
    }

    /**
     * Make sure you open and close connection when using this method.
     *
     * @param tableName
     * @param primarykeyName
     * @param primaryKeyValue
     * @return records deleted
     * @throws SQLException
     */
    @Override
    public int deleteRecordbyPrimaryKey(String tableName, String primarykeyName, Object primaryKeyValue) throws SQLException {
        int recordsDeleted = 0;
        PreparedStatement pstmt = null;

        final String sql = "Delete FROM " + tableName + " WHERE " + primarykeyName + " = ?";

        pstmt = conn.prepareStatement(sql);

        if (primarykeyName != null) {
            if (primaryKeyValue instanceof String) { // just have .setObject ?
                pstmt.setString(1, (String) primaryKeyValue);
            } else {
                pstmt.setObject(1, primaryKeyValue);
            }

        }
        recordsDeleted = pstmt.executeUpdate();

        return recordsDeleted;
    }

    /**
     *
     * @param tableName
     * @param colNames
     * @param colValues
     * @param pkColName
     * @param value
     * @return
     * @throws SQLException
     */
    @Override
    public int updatebyID(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1; // prepared statements start at 1 (w/ the "?"(s) )
            Object obj = null;

            // set params for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // and finally set param for wehere value
            pstmt.setObject(index, value);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } // end try
        } // end finally

        return recsUpdated;
    }

    //boolean type? better for UI?
    @Override
    public boolean insertRecord(String tableName, List <String> columnNames, List <Object>columnValues) throws SQLException{ 
        int recordsInserted = 0;
        PreparedStatement preSmt = null;
        
        /*
        INSERT INTO table_name (column1,column2,column3,...)
        VALUES (value1,value2,value3,...);
        */
        
        //make a build insert statement?? (because of single responsibilty)
        try{
        preSmt = buildInsertStatement(conn, tableName, columnNames);
        
        final Iterator i = columnValues.iterator();
            int index = 1; // prepared statements start at 1 (w/ the "?"(s) )
            

            // set params for column values
            while (i.hasNext()) {
                final Object obj = i.next();
                preSmt.setObject(index++, obj);
            }

            recordsInserted = preSmt.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            try {
                preSmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } // end try
        } // end finally
        if (recordsInserted == 1) {
            return true;
        } else {
            return false;
        }
        
         
    }
    
    
    
    
    /**
     * Builds a java.sql.PreparedStatement for an sql update using only one
     * where clause test
     *
     * @param conn - a JDBC <code>Connection</code> object
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be updated.
     * @param whereField - a <code>String</code> representing the field name for
     * the search criteria.
     * @return java.sql.PreparedStatement
     * @throws SQLException
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
            List colDescriptors, String whereField)
            throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    /**
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @return
     * @throws SQLException 
     */
    private PreparedStatement buildInsertStatement(Connection conn,String tableName,List columnNames) throws SQLException{ //no values needed because they are provided from the list
         //multithread safe (stringbuffer)
        //beginning of string
         StringBuffer sql = new StringBuffer("Insert Into " + tableName + " (");
         final Iterator i=columnNames.iterator(); //get the column names in the list
		while( i.hasNext() ) {
                        sql.append(i.next() + ", ");
                }
                //looked at examples with sql and String buffer. Still confused on somethings but this works
                // createing a new stringbuffer constructs a string buffer initialized to the contents of the specified string.
                //A substring returns a new String that contains a subsequence of characters currently contained in this sequence.
         sql = new StringBuffer((sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ") ) + ") Values (" ); //get the values in the list
         for (int m = 0; m < columnNames.size();m++){
             sql.append("?, "); 
         }
         final String finalSQL = ((sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ") ) + ")" );
         return conn.prepareStatement(finalSQL);
    }
    public int deleteRecordsbyPrimaryKey(String tableName, String primarykeyName, List<Object> primaryKeyValues) throws SQLException{
        PreparedStatement pstmt = null;
        int recordsDeleted = 0;
        
        try{
        pstmt = buildDeleteStatement(conn,tableName,primarykeyName, primaryKeyValues);
         final Iterator i = primaryKeyValues.iterator();
            int index = 1; // prepared statements start at 1 (w/ the "?"(s) )
            Object obj = null;
           while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
           } 
       
            recordsDeleted = pstmt.executeUpdate();
        }catch(Exception e){
            throw e;
        }try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            }
        
         return recordsDeleted;
    }
    private PreparedStatement buildDeleteStatement(Connection conn,String tableName,Object primaryKeyName,List<Object> primaryKeyValues) throws SQLException{
        StringBuffer sql = new StringBuffer("Delete From " + tableName + " Where " + primaryKeyName + " IN (");
        for (int m = 0; m < primaryKeyValues.size();m++){
             sql.append("?, ");
        }
        final String finalSQL = ((sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ") ) + ")" );
        return conn.prepareStatement(finalSQL);
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//

        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
        System.out.println(rawData);
        db.closeConnection();

         db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
         List<Object> primaryKeyValues = new ArrayList();
         primaryKeyValues.add("69");
         primaryKeyValues.add("70");
         int result = db.deleteRecordsbyPrimaryKey("author","author_id", primaryKeyValues);
          db.closeConnection();
         
       // db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

//        List<String> colNames = Arrays.asList("author_name", "date_added");
//        List<Object> colValues = Arrays.asList("Nick Piette", "2012-03-12");
//        int result = db.updatebyID("author", colNames, colValues, "author_id", 1);
//        db.closeConnection();
//        int del = db.deleteRecordbyPrimaryKey("author", "author_id", 3);
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        List<Map<String, Object>> rawData2 = db.findAllRecords("author", 0);
//        db.closeConnection();
//
//        System.out.println(rawData2);
        
        System.out.println("---------");
        
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        List<String> colNames2 = Arrays.asList("author_name", "date_added");
//        List<Object> colValues2 = Arrays.asList("Lex Luther", "2014-03-15");
//        db.insertRecord("author", colNames2, colValues2);
//        db.closeConnection();
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String, Object>> rawData3 = db.findAllRecords("author", 0);
        System.out.println(rawData3);
        db.closeConnection();
    }
}
