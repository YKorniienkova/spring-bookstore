package mate.academy.springintro.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookSearchParameterDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);

        Set<Category> categories = categoryRepository.findAllById(requestDto.getCategoryIds())
                .stream()
                .collect(Collectors.toSet());
        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
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

        Set<Category> categories = categoryRepository.findAllById(requestDto.getCategoryIds())
                .stream()
                .collect(Collectors.toSet());
        book.setCategories(categories);

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
