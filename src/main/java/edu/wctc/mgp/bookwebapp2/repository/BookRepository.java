package edu.wctc.mgp.bookwebapp2.repository;

import edu.wctc.mgp.bookwebapp2.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author mparish2
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
    @Query("SELECT b.title FROM Book b")
    public Object[] findAllWithTitleOnly();
    
}
