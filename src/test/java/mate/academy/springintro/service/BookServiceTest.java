package mate.academy.springintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.service.impl.BookServiceImpl;
import mate.academy.springintro.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

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
        Book book = TestUtil.createBook();
        book.setId(bookId);

        BookDto expected = TestUtil.createBookDto();

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));
        when(bookMapper.toDto(book))
                .thenReturn(expected);
        BookDto actual = bookService.findById(bookId);

        assertEquals(expected, actual);
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
        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto();
        Book book = TestUtil.createBook();
        Book savedBook = TestUtil.createBook();
        savedBook.setId(1L);
        BookDto expected = TestUtil.createBookDto();

        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expected);

        BookDto actual = bookService.save(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update existing book")
    void update_ValidBookId_ReturnsBookDto() {
        Long bookId = 1L;
        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto();
        Book book = TestUtil.createBook();
        book.setId(bookId);
        BookDto expected = TestUtil.createBookDto();

        Category category = TestUtil.createCategory();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.update(bookId, requestDto);

        verify(bookMapper).updateBookFromDto(requestDto, book);
        assertEquals(expected, actual);
    }
}
