package edu.urninax.project1.controllers;

import edu.urninax.project1.DAO.PersonDAO;
import edu.urninax.project1.models.Book;
import edu.urninax.project1.models.Person;
import edu.urninax.project1.services.PeopleService;
import edu.urninax.project1.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController{
    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final PersonDAO personDAO;

    public PeopleController(PersonValidator personValidator, PeopleService peopleService, PersonDAO personDAO){
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getPersonBooks(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("personToEdit", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("personToEdit") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people/"+id;
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people/";
    }
}
