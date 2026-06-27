package mate.academy.springintro.util;

import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.dto.category.CategoryDto;
import mate.academy.springintro.dto.category.CreateCategoryRequestDto;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;

import java.math.BigDecimal;
import java.util.Set;

public final class TestUtil {
    private TestUtil() {

    }

    public static CreateBookRequestDto createBookRequestDto() {
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

    public static Book createBook() {
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

    public static BookDto createBookDto() {
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

    public static CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Programming");
        requestDto.setDescription("Programming books");
        return requestDto;
    }

    public static CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Programming");
        categoryDto.setDescription("Programming books");
        return categoryDto;
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");
        category.setDescription("Programming books");
        return category;
    }
}
