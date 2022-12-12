package edu.urninax.project1.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @NotEmpty
    @Size(min=2, max = 60, message = "Book name should be between 2 and 60 characters")
    @Column(name = "name")
    private String name;


    @Size(min = 2, max = 40, message = "Author name should be between 2 and 40")
    @NotEmpty
    @Column(name = "author")
    private String author;

    @Max(value = 2022, message = "Production year must be before 2022 year")
    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "added_at")
    @CreationTimestamp
    private Calendar addedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.nnn")
    @Column(name = "taken_at")
    private Date takenAt;

    @Transient
    private boolean isExpired;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book(){}
    public Book(String name, String author, int productionYear){
        this.name = name;
        this.author = author;
        this.productionYear = productionYear;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public int getProductionYear(){
        return productionYear;
    }

    public void setProductionYear(int productionYear){
        this.productionYear = productionYear;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Person getOwner(){
        return owner;
    }

    public void setOwner(Person owner){
        this.owner = owner;
    }

    public Calendar getAddedAt(){
        return addedAt;
    }

    public String getFormattedAddedAt(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(addedAt.getTime());
    }

    public void setAddedAt(Calendar addedAt){
        this.addedAt = addedAt;
    }

    public Date getTakenAt(){
        return takenAt;
    }

    public void setTakenAt(Date takenAt){
        this.takenAt = takenAt;
    }

    public boolean isExpired(){
        return isExpired;
    }

    public void setExpired(boolean expired){
        this.isExpired = expired;
    }
}
