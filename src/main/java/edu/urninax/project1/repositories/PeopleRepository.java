package edu.urninax.project1.repositories;

import edu.urninax.project1.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{
    Optional<Person> findPersonByFullName(String fullName);
}
