package mate.academy.springintro.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameterDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.specification.SpecificationBuilder;
import mate.academy.springintro.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationBuilder specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        bookMapper.updateBookFromDto(requestDto, book);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public Page<BookDto> search(BookSearchParameterDto searchParameters, Pageable pageable) {
        Specification<Book> specification = specificationBuilder.build(searchParameters);

        return bookRepository.findAll(specification, pageable)
                .map(bookMapper::toDto);
    }
}
