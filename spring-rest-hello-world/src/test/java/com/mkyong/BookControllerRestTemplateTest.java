package com.mkyong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkyong.error.BookNotFoundException;
import org.hibernate.mapping.Any;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
//@ActiveProfiles("test")
public class BookControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookRepository mockRepository;

    Book book;

    @Before
    public void init() {
        book = new Book(1L, "A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41"));
//        when(mockRepository.findById(1l)).thenReturn(Optional.of(book));
        when(mockRepository.findById(1l)).thenReturn(Optional.of(book));


    }

    @Test
    public void find_bookId_OK() throws JSONException {

        String expected = "{id:1,name:\"A Guide to the Bodhisattva Way of Life\",author:\"Santideva\",price:15.41}";

        ResponseEntity<Book> response = restTemplate.getForEntity("/books/1", Book.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

//        JSONAssert.assertEquals(expected, response.getBody(), false);
        assertEquals("A Guide to the Bodhisattva Way of Life", response.getBody().getName().toString());

        verify(mockRepository, times(1)).findById(1L);

    }

    @Test
    public void find_bookId_notOK() throws JSONException {

        when(mockRepository.findById(100l)).thenReturn(Optional.empty());//new String("there is no data found with id"));
        ResponseEntity<String> response = restTemplate.getForEntity("/books/100", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is no data found  with Id: 100", response.getBody());
        verify(mockRepository, times(1)).findById(100L);

    }

    @Test
    public void find_allBook_OK() throws Exception {

        List<Book> books = Arrays.asList(
                new Book(1L, "Book A", "Ah Pig", new BigDecimal("1.99")),
                new Book(2L, "Book B", "Ah Dog", new BigDecimal("2.99")));
        when(mockRepository.findAll()).thenReturn(books);
        String expected = om.writeValueAsString(books);
//        ResponseEntity<String> response = restTemplate.getForEntity("/books", String.class);

        ResponseEntity<Book[]> response = restTemplate.getForEntity("/books", Book[].class);
        List<Book> actual = Arrays.asList(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(actual.size(), books.size());

        verify(mockRepository, times(1)).findAll();
    }

    @Test
    public void find_bookIdNotFound_404() throws Exception {

        String expected = "{status:404,error:\"Not Found\",message:\"Book id not found : 5\",path:\"/books/5\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/books/5", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void save_book_OK() throws Exception {

        Book newBook = new Book(1L, "Spring Boot Guide", "mkyong", new BigDecimal("2.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);

        String expected = om.writeValueAsString(newBook);

        ResponseEntity<Book> response = restTemplate.postForEntity("/books", newBook, Book.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newBook.toString(), response.getBody().toString());

//        verify(mockRepository, times(1)).save(any(Book.class));

    }


    public Book save_book_OK_Internal() throws Exception {

        Book newBook = new Book(1L, "Spring Boot Guide", "mkyong", new BigDecimal("2.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);

        String expected = om.writeValueAsString(newBook);

        ResponseEntity<Book> response = restTemplate.postForEntity("/books", newBook, Book.class);
        return response.getBody();

    }

    @Test
    public void save_book_not_OK() throws Exception {

        Book newBook = null;//new Book(1L, "Spring Boot Guide", "mkyong", new BigDecimal("2.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);


        ResponseEntity<Book> response = restTemplate.postForEntity("/books", newBook, Book.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

//        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void update_book_OK() throws Exception {

//        Book updateBook = new Book(1L, "ABC", "mkyong", new BigDecimal("19.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(book);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        book.setAuthor("Amanuel Tekelemariam");
        book.setName("OOP");
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(book), headers);

        ResponseEntity<Book> response = restTemplate.exchange("/books/1",HttpMethod.PUT, entity,Book.class);

        System.out.println(response.getBody().toString());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(book.toString(), response.getBody().toString());

//        verify(mockRepository, times(1)).findById(1L);
//        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void patch_bookAuthor_OK() throws Exception {

        Book updateBook = save_book_OK_Internal();
        System.out.println("\n\n\n" + updateBook.toString());

        when(mockRepository.save(any(Book.class))).thenReturn(updateBook);
        String patchInJson = "{\"author\":\"ultraman\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

        ResponseEntity<Book> response = restTemplate.exchange("/books/1", HttpMethod.PATCH, entity, Book.class);

        ResponseEntity<Book> book22 = restTemplate.getForEntity("/books/1", Book.class);

        System.out.println("\n\n\n" + book22.getBody());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("ultraman", book22.getBody().getAuthor().toString());

    }

    @Test
    public void patch_bookPrice_405() throws JSONException {

        String expected = "{status:405,error:\"Method Not Allowed\",message:\"Field [price] update is not allow.\"}";

        String patchInJson = "{\"price\":\"99.99\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/books/1", HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(0)).save(any(Book.class));
    }

    @Test
    public void delete_book_OK() {

//        doNothing().when(mockRepository).deleteById(1L);
        ResponseEntity<String> response = restTemplate.exchange("/books/2", HttpMethod.DELETE, null, String.class);

        ResponseEntity<Book> bookResponseEntity= restTemplate.getForEntity("/books/2", Book.class);
        System.out.println("\n\n\n" +  bookResponseEntity.getBody() );

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        String msg = "Book with ID :" + 2L + "is deleted succefully!!!!";
        assertEquals(msg, response.getBody().toString());

//        verify(mockRepository, times(1)).deleteById(1L);
    }


    @Test
    public void delete_book_not_OK() {
//        Book book22= null;
//        when(mockRepository.deleteById(1L)).;
        ResponseEntity<String> response = restTemplate.exchange("/books/99", HttpMethod.DELETE, null, String.class);




        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String msg = new BookNotFoundException(99L).getMessage();
        assertEquals(msg, response.getBody().toString());

//        verify(mockRepository, times(1)).deleteById(1L);
    }

//    @Test
//    public void delete_book_OK() {
////        Book book22= null;
////        when(mockRepository.deleteById(1L)).;
//        ResponseEntity<String> response = restTemplate.exchange("/books/99", HttpMethod.DELETE, null, String.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        String msg = new BookNotFoundException(99L).getMessage();
//        assertEquals(msg, response.getBody().toString());
//
////        verify(mockRepository, times(1)).deleteById(1L);
//    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
