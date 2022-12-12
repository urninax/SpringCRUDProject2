package edu.urninax.project1.util;

import edu.urninax.project1.DAO.PersonDAO;
import edu.urninax.project1.models.Person;
import edu.urninax.project1.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class PersonValidator implements Validator{
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService){
        this.peopleService = peopleService;
    }
    @Override
    public boolean supports(Class<?> clazz){
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        Person person = (Person) target;
        Optional<Person> personOptional = peopleService.findOneByName(person.getFullName());
        if(personOptional.isPresent()){
            if(personOptional.get().getId()!=person.getId()){
                errors.rejectValue("fullName", "", "This name already exists");
            }
        }
    }
}
