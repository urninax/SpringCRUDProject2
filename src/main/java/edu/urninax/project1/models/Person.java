package edu.urninax.project1.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "person")
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;

    @NotEmpty(message="Name should not be empty")
    @Size(min = 2, max = 40, message = "Name length should be between 2 and 40 characters")
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    @NotNull(message = "Date of birth must not be empty")
    private Calendar dateOfBirth;

    @Column(name = "created_at")
    @CreationTimestamp
    private Calendar createdAt;

    public Person(){
    }

    public Person(String fullName){
        this.fullName = fullName;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public List<Book> getBooks(){
        return books;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }

    public void addBook(Book book){
        if(books == null){
            books = new ArrayList<>(Collections.singleton(book));
        }else{
            books.add(book);
        }
        book.setOwner(this);
    }

    public Calendar getDateOfBirth(){
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public Calendar getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt){
        this.createdAt = createdAt;
    }

    public String getFormattedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(dateOfBirth.getTime());
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(fullName, person.fullName) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(createdAt, person.createdAt);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, fullName, dateOfBirth, createdAt);
    }
}
