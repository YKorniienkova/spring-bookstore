package mate.academy.springintro.mapper;

import java.util.stream.Collectors;
import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(
                book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
