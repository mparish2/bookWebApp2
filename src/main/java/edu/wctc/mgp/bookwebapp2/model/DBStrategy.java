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
public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException;

    public abstract void closeConnection() throws SQLException;

    public abstract List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException;
    
    public abstract int deleteRecordbyPrimaryKey(String tableName, String primarykeyName, Object primaryKeyValue) throws SQLException;
    
     public int updatebyID(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException;
    
     public boolean insertRecord(String tableName, List <String> columnNames, List <Object>columnValues)throws SQLException;

     public Map<String, Object> findById(String tableName, String primaryColName,
            Object primaryKeyValue) throws DataAccessException, ClassNotFoundException, SQLException;
     
     public int deleteRecordsbyPrimaryKey(String tableName, String primarykeyName, List<Object> primaryKeyValues) throws SQLException;
     
     
      /**
     * Open a connection using a connection pool configured on server.
     *
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws DataAccessException - if ds cannot be established
     */
    void openConnection(DataSource ds) throws DataAccessException;
}
