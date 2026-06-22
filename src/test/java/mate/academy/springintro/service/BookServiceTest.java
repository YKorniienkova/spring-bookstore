package mate.academy.springintro.service;

import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Find book by valid id")
    public void findById_WithValidBookId_ReturnsBookDto() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));
        when(bookMapper.toDto(book))
                .thenReturn(bookDto);
        BookDto actual = bookService.findById(bookId);

        assertEquals(bookId, actual.getId());
    }

    @Test
    @DisplayName("Throw exception for invalid book id")
    void findById_InvalidBookId_ThrowsException() {
        Long bookId = 100L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(bookId)
        );

        assertEquals("Can't find book by id: " + bookId, exception.getMessage());
    }

    @Test
    @DisplayName("""
            Delete book
            when book id is valid
            """)
    void deleteById_ValidBookId_DeletesBook() {
        Long bookId = 1L;

        bookService.deleteById(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @Test
    @DisplayName("Save book with valid Dto")
    void save_ValidRequestDto_ReturnsBookDto() {
        CreateBookRequestDto requestDto = createBookRequestDto();
        Book book = createBook();
        Book savedBook = createBook();
        savedBook.setId(1L);
        BookDto expected = createBookDto();

        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expected);

        BookDto actual = bookService.save(requestDto);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getCategoryIds(), actual.getCategoryIds());
    }

    @Test
    @DisplayName("Update existing book")
    void update_ValidBookId_ReturnsBookDto() {
        Long bookId = 1L;
        CreateBookRequestDto requestDto = createBookRequestDto();
        Book book = createBook();
        book.setId(bookId);
        BookDto expected = createBookDto();

        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.update(bookId, requestDto);

        verify(bookMapper).updateBookFromDto(requestDto, book);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }


    private CreateBookRequestDto createBookRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Java");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("9781234567897");
        requestDto.setPrice(BigDecimal.valueOf(50));
        requestDto.setDescription("Java book");
        requestDto.setCoverImage("img.jpg");
        requestDto.setCategoryIds(Set.of(1L));
        return requestDto;
    }

    private Book createBook() {
        Book book = new Book();
        book.setTitle("Java");
        book.setAuthor("Author");
        book.setIsbn("9781234567897");
        book.setPrice(BigDecimal.valueOf(50));
        book.setDescription("Java book");
        book.setCoverImage("img.jpg");
        book.setCategories(Set.of());
        return book;
    }

    private BookDto createBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Java");
        bookDto.setAuthor("Author");
        bookDto.setIsbn("9781234567897");
        bookDto.setPrice(BigDecimal.valueOf(50));
        bookDto.setDescription("Java book");
        bookDto.setCoverImage("img.jpg");
        bookDto.setCategoryIds(Set.of(1L));
        return bookDto;
    }
}
