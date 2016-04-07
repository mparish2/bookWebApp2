package edu.wctc.mgp.bookwebapp2.service;

import edu.wctc.mgp.bookwebapp2.entity.Book;
import edu.wctc.mgp.bookwebapp2.repository.AuthorRepository;
import edu.wctc.mgp.bookwebapp2.repository.BookRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * Don't confuse the Spring @Respository annotation with the repository
 * classes (AuthorRepository, BookRespository). The annotation here is used 
 * solely to tell Spring to translate any exception messages into more
 * user friendly text. Any class annotated that way will do that.
 * 
 * @author jlombardo
 */
@Repository
@Transactional(readOnly = true)
public class BookService {
    private transient final Logger LOG = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;

    public BookService() {
    }
    
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    
    public Book findById(String id) {
        return bookRepo.findOne(new Integer(id));
    }
    
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param book 
     */
    @Transactional
    public void remove(Book book) {
        LOG.debug("Deleting book: " + book.getTitle());
        bookRepo.delete(book);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param book 
     */
    @Transactional
    public Book edit(Book book) {
        return bookRepo.saveAndFlush(book);
    }
    
}
