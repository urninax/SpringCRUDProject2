package edu.urninax.project1.repositories;

import edu.urninax.project1.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>{
    List<Book> searchBooksByNameStartsWith(String start);
}
