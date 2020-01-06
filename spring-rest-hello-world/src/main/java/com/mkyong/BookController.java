package com.mkyong;

import com.mkyong.error.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository repository;

    // Find
    @GetMapping("/books")
    ResponseEntity<?> findAll() {
        List<Book> books = repository.findAll();
        if (books.isEmpty())
            return new ResponseEntity<>("No data to dispaly", HttpStatus.NO_CONTENT);
        else

            return new ResponseEntity<>(books, HttpStatus.OK);
    }


    // Save
    @PostMapping("/books")
    ResponseEntity<?> saveNewBook(@RequestBody Book newBook) {
        if (newBook.equals(null))
            return new ResponseEntity<>("the book data is empty", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(repository.save(newBook), HttpStatus.CREATED);
    }

    // Find
    @GetMapping("/books/{id}")
    ResponseEntity<?> findOne(@PathVariable Long id) {
        Optional<Book> response = repository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);// new BookNotFoundException(id));
        } else {
            return new ResponseEntity<>( new String("There is no data found  with Id: ") + id, HttpStatus.BAD_REQUEST);// new BookNotFoundException(id));

        }
    }

    // Save or update
    @PutMapping("/books/{id}")
    ResponseEntity<Book> updateBook(@RequestBody Book newBook, @PathVariable Long id) {

        Optional<Book> book = repository.findById(id);

        if (book.isPresent() & newBook != null) {
            Book book1 = (Book) book.get();
            book1.setAuthor(newBook.getAuthor());
            book1.setName(newBook.getName());
            book1.setPrice(newBook.getPrice());

            return new ResponseEntity<>(repository.save(book1), HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(newBook, HttpStatus.BAD_REQUEST);
    }

    // update author only
    @PatchMapping("/books/{id}")
    ResponseEntity<?> patch(@RequestBody Map<String, String> updateAuthor, @PathVariable Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()) {
            Book book1 = book.get();
            book1.setAuthor(updateAuthor.get("author"));

            return new ResponseEntity<>(repository.save(book1), HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new BookNotFoundException(id).getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**/
    @DeleteMapping("/books/{id}")
    ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()) {
            repository.deleteById(id);
            String msg= "Book with ID :" + id  + "is deleted succefully!!!!";
            return new ResponseEntity<>(msg , HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new BookNotFoundException(id).getMessage(), HttpStatus.BAD_REQUEST);
    }

}
