package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.dto.BookDto;
import mate.academy.springintro.dto.BookSearchParametrDto;
import mate.academy.springintro.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParametrDto searchParametrs);
}
