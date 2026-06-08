package mate.academy.springintro.service;

import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameterDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    Page<BookDto> search(BookSearchParameterDto searchParameters, Pageable pageable);
}
