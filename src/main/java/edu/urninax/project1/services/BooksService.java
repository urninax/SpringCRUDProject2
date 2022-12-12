package edu.urninax.project1.services;

import edu.urninax.project1.models.Book;
import edu.urninax.project1.models.Person;
import edu.urninax.project1.repositories.BooksRepository;
import edu.urninax.project1.util.BookExpirationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class BooksService{
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, boolean sortByYear){
        List<Book> books = null;
        if(sortByYear){
            if(page != -1 && booksPerPage != -1){
                books = booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("productionYear"))).getContent();
            }else{
                books = booksRepository.findAll(Sort.by(Sort.Direction.ASC, "productionYear"));
            }
        }else{
            if(page != -1 && booksPerPage != -1){
                books = booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }else{
                books = booksRepository.findAll();
            }
        }
        return books;
    }

    public List<Book> search(String start){
        return booksRepository.searchBooksByNameStartsWith(start);
    }

    public Book findOne(int id){
        Book foundBook = booksRepository.findById(id).get();
        BookExpirationValidator.check(foundBook);
        return foundBook;
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Person getOwner(int bookId){
        Optional<Book> foundBook = booksRepository.findById(bookId);
        return foundBook.map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void assign(int bookId, Person person){
        Book book = booksRepository.findById(bookId).get();
        person.addBook(book);
        System.out.println(Calendar.getInstance().getTime());
        System.out.println(new Date());
        book.setTakenAt(new Date());
    }

    @Transactional
    public void release(int bookId){
        Book book = booksRepository.findById(bookId).get();
        book.setOwner(null);
        book.setExpired(false);
        book.setTakenAt(null);
    }

}
