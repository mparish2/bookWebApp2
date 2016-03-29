/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mgp.bookwebapp2.ejb;

import edu.wctc.mgp.bookwebapp2.model.Author;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Matthew_2
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "edu.wctc.mgp_bookwebapp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }

    public void deleteAuthorbyID(String Id) {
        /* String jpql = "delete from Author a where a.authorId = ?1";
        TypedQuery q = getEntityManager().createQuery(jpql,Author.class);
        q.executeUpdate(); */ // doesn't like it this way Caused by: 
//                               java.lang.IllegalStateException: Query argument 1 not found in the list of parameters provided during query execution.

        Author author = this.find(new Integer(Id));
        this.remove(author);
    }

//     public void deleteAuthorsbyIDs(List<Object> primaryKeyValues) throws SQLException{
//        
//        this.removeMulti(primaryKeyValues);
//     
//    }
    public void saveAuthor(String id, String name) {
        Author author = new Author();
        if (id == null) {
            // must be a new record
            author.setAuthorName(name);
            author.setDateAdded(new Date());
        } else {
            // modify record
            author.setAuthorId(new Integer(id));
            author.setAuthorName(name);
        }
        this.getEntityManager().merge(author);
    }

//     private String buildDeleteStatement(String tableName,Object primaryKeyName,List<Object> primaryKeyValues){
//         StringBuffer sql = new StringBuffer("Delete From " + tableName + " Where " + primaryKeyName + " IN (");
//         for (int m = 0; m < primaryKeyValues.size();m++){
//              sql.append("?"+ m+ ", ");
//         }
//         final String finalSQL = ((sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ") ) + ")" );
//         return finalSQL;
//     }
}
