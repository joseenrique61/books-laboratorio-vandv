package hu.springbootrestfuljpa.books.controllerTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import hu.springbootrestfuljpa.books.controller.BookController;
import hu.springbootrestfuljpa.books.model.Book;
import hu.springbootrestfuljpa.books.model.Review;
import hu.springbootrestfuljpa.books.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
  private static final int ID = 2;
  private static final int RELEASE = 22;
  private static final String AUTOHOR = "Homero";
  private static final String TITLE = "Iliada";
  private static final List<Review> REVIEWS_LIST = new ArrayList<>();
  private static final Book BOOK = new Book();
  private static final Optional<Book> OPTIONAL_BOOK = Optional.of(BOOK);
  private static final Optional<Book> OPTIONAL_BOOK_EMPTY = Optional.empty();
  static {
    BOOK.setAuthor(AUTOHOR);
    BOOK.setId(ID);
    BOOK.setRelease(RELEASE);
    BOOK.setReviews(REVIEWS_LIST);
    BOOK.setTitle(TITLE);
  }
  @Mock
  private BookRepository bookRepository;
  @InjectMocks
  private BookController bookController;

  @Test
  public void retrieveAllBoosTest() {
    final Book book = new Book();
    Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
    final List<Book> response = bookController.retrieveAllBooks();
    assertNotNull(response);
    assertFalse(response.isEmpty());
    assertEquals(1, response.size());
  }

  @Test
  public void retrieveBookTest() {
    Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
    ResponseEntity<Book> response = bookController.retrieveBook(ID);
    assertNotNull(response);
    assertEquals(AUTOHOR, response.getBody().getAuthor());
    assertEquals(TITLE, response.getBody().getTitle());
    assertEquals(REVIEWS_LIST, response.getBody().getReviews());
  }

  @Test
  public void retrieveBookNoFoundTest() {
    Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
    ResponseEntity<Book> httpResponse = bookController.retrieveBook(ID);
    assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void createBookTest() {
    Mockito.when(bookRepository.existsById(BOOK.getId())).thenReturn(false);
    ResponseEntity<Object> httpResponse = bookController.createBook(BOOK);
    assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void createBookExistsByIdTest() {
    Mockito.when(bookRepository.existsById(BOOK.getId())).thenReturn(true);
    ResponseEntity<Object> httpResponse = bookController.createBook(BOOK);
    assertEquals(HttpStatus.CONFLICT, httpResponse.getStatusCode());
  }

  @Test
  public void deleteBookdTest() {
    Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
    bookController.deleteBook(ID);
  }

  @Test
  public void deleteBookNotFoundTest() {
    Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
    ResponseEntity<Object> httpResponse = bookController.deleteBook(ID);
    assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }
}
