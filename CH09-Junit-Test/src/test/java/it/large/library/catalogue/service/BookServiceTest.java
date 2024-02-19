package it.large.library.catalogue.service;

import it.large.library.catalogue.entity.AuthorEntity;
import it.large.library.catalogue.entity.BookEntity;
import it.large.library.catalogue.entity.CategoryEntity;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.model.CategoryModel;
import it.large.library.catalogue.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Test
    void getBook() {
        // Creazione di un oggetto di esempio
        UUID bookId = UUID.randomUUID();
        BookEntity bookEntity = new BookEntity();
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorId(UUID.randomUUID());
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(UUID.randomUUID());
        List<CategoryEntity> categoryEntities = new ArrayList<>();

        categoryEntities.add(categoryEntity);
        bookEntity.setBookId(bookId);
        bookEntity.setAuthor(authorEntity);
        bookEntity.setCategories(categoryEntities);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        // Chiamata al metodo del servizio
        BookModel result = bookService.getBook(bookId);

        // Verifica che il risultato non sia nullo
        assertNotNull(result);
        // Verifica che il convertitore sia chiamato correttamente
        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }


    @Test
    void add() {
        // Creazione di un oggetto di esempio
        BookModel bookModel = new BookModel();
        AuthorModel authorModel = new AuthorModel();
        authorModel.setAuthorId(UUID.randomUUID());
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId(UUID.randomUUID());
        List<CategoryModel> categoryModelList = new ArrayList<>();

        categoryModelList.add(categoryModel);
        bookModel.setAuthor(authorModel);
        bookModel.setCategories(categoryModelList);

        // Mock del AuthorService
        when(authorService.get(any(UUID.class))).thenReturn(authorModel);

        // Chiamata al metodo del servizio
        BookModel result = bookService.add(bookModel);

        // Verifica che il risultato non sia nullo
        assertNotNull(result);
        // Verifica che il convertitore sia chiamato correttamente
        verify(authorService, times(1)).get(any(UUID.class));
        verify(bookRepository, times(1)).save(any(BookEntity.class));
        verifyNoMoreInteractions(authorService, bookRepository);
    }

    @Test
    void remove() {
        // Creazione di un oggetto di esempio
        UUID bookId = UUID.randomUUID();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        // Chiamata al metodo del servizio
        bookService.remove(bookId);

        // Verifica che il convertitore sia chiamato correttamente
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(bookEntity);
        verifyNoMoreInteractions(bookRepository);
    }
}