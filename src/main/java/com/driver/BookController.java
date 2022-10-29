package com.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(this.id);
        this.id++;
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity getBookById(@PathVariable ("id") int id){
        for(Book book : bookList){
           int current= book.getId();
           if(id==current){
               return new ResponseEntity<>(book,HttpStatus.FOUND);
           }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable("id")int id){
        for(Book b : bookList){
            if(b.getId()==id){
                bookList.remove(b);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks(){
        bookList.clear();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam("author") String author){
        List<Book> listByAuthor = new ArrayList<>();
        for(Book b: bookList){
            if(b.getAuthor().equals(author)){
                listByAuthor.add(b);
            }
        }
        return new ResponseEntity(listByAuthor,HttpStatus.OK);
    }

    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam("genre") String genre){
        List<Book> listByGenre = new ArrayList<>();
        for(Book b: bookList){
            if(Objects.equals(b.getGenre(), genre)){
                listByGenre.add(b);
            }
        }
        return new ResponseEntity(listByGenre,HttpStatus.OK);
    }
    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()

    // get request /get-all-books
    // getAllBooks()

    // delete request /delete-all-books
    // deleteAllBooks()

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
}
