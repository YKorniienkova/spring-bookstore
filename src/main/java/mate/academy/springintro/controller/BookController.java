package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameterDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save a new Book", description = "Save a new Book")
    public BookDto save(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public Page<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get a book by id", description = "Get a book by id")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a book by id", description = "Delete a book by id")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a book by id", description = "Update a book by id")
    public BookDto updateById(@PathVariable Long id,
                              @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Search books by parameters",
            description = "Search books by title, author")
    public Page<BookDto> searchBooks(BookSearchParameterDto searchParameters, Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
