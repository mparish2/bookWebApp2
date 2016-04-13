package edu.wctc.mgp.bookwebapp2.repository;

import edu.wctc.mgp.bookwebapp2.entity.Author;
import java.io.Serializable;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mparish2
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
    @Query("SELECT a FROM Author a JOIN FETCH a.bookSet WHERE a.authorId = (:id)")
    public Author findByIdAndFetchBooksEagerly(@Param("id") Integer id);
//    
    @Query("SELECT a.authorName FROM Author a")
    public Object[] findAllWithNameOnly();
    
    @Query("SELECT a FROM Author a WHERE a.dateAdded => (:start) and a.dateAdded <= (:end)")
    public Author findByDates(@Param("start") Date start, @Param("end") Date end);
}
