package mate.academy.springintro.repository;

import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    @DisplayName("Find books by category id")
    void findAllByCategoriesId_WithValidCategoriesId_ReturnsBooks() {
        Category category = new Category();
        category.setName("Programming");
        category.setDescription("Programming books");
        categoryRepository.save(category);

        Book book = new Book();
        book.setTitle("Java");
        book.setAuthor("Author");
        book.setIsbn("3243243242342");
        book.setPrice(BigDecimal.valueOf(50));
        book.setDescription("Java book");
        book.setCoverImage("img.jpg");
        book.setCategories(Set.of(category));
        bookRepository.save(book);

        Page<Book> result = bookRepository.findAllByCategoriesId(
                category.getId(),
                PageRequest.of(0,10)
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("Java book", result.getContent().get(0).getDescription());
    }
}
