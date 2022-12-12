package edu.urninax.project1.controllers;

import edu.urninax.project1.DAO.BookDAO;
import edu.urninax.project1.DAO.PersonDAO;
import edu.urninax.project1.models.Book;
import edu.urninax.project1.models.Person;
import edu.urninax.project1.services.BooksService;
import edu.urninax.project1.services.PeopleService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController{
    private final BooksService booksService;
    private final PeopleService peopleService;

    public BooksController(PeopleService peopleService, BooksService booksService){
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", defaultValue = "-1") Integer page,
                        @RequestParam(name = "books_per_page", defaultValue = "-1") Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", defaultValue = "false") boolean sortByYear, Model model){
        model.addAttribute("books", booksService.findAll(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/new")
    public String addBookGet(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("owner", booksService.getOwner(id));
        model.addAttribute("people" , peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @PostMapping("/search")
    public String find(@RequestParam("keyword") String keyword, Model model){
        model.addAttribute("books", booksService.search(keyword));
        return "books/search";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.assign(id, person);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/"+id;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("bookToEdit", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("bookToEdit") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books/";
    }
}
