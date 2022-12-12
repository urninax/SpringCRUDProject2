package edu.urninax.project1.services;

import edu.urninax.project1.models.Book;
import edu.urninax.project1.models.Person;
import edu.urninax.project1.repositories.PeopleRepository;
import edu.urninax.project1.util.BookExpirationValidator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService{

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository){
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Optional<Person> findOneByName(String name){
        return peopleRepository.findPersonByFullName(name);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> getPersonBooks(int personId){
        Person person = peopleRepository.findById(personId).get();
        Hibernate.initialize(person.getBooks());
        List<Book> personBooks = person.getBooks();
        for(Book book : personBooks){
            BookExpirationValidator.check(book);
        }
        return personBooks;
    }
}
