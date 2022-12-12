package edu.urninax.project1.util;

import edu.urninax.project1.models.Book;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BookExpirationValidator{
    public static void check(Book book){
        long nowTimeInMs = new Date().getTime();
        if(book.getTakenAt()==null){
            book.setExpired(false);
            return;
        }
        long takenAtTimeInMs = book.getTakenAt().getTime();
        System.out.println(TimeUnit.DAYS.convert(nowTimeInMs-takenAtTimeInMs, TimeUnit.MILLISECONDS));
        book.setExpired(TimeUnit.DAYS.convert(nowTimeInMs-takenAtTimeInMs, TimeUnit.MILLISECONDS)>=10);
    }
}
