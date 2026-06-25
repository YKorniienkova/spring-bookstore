package mate.academy.springintro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Set;

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
        Category category =  new Category();
        category.setName("Programming");
        category.setDescription("Programming books");
        Category savedCategory = categoryRepository.save(category);


        Book expected = TestUtil.createBook();
        expected.setCategories(Set.of(savedCategory));

        Book savedBook = bookRepository.save(expected);

        Page<Book> result = bookRepository.findAllByCategoriesId(
                savedCategory.getId(),
                PageRequest.of(0,10)
        );

        Book actual = result.getContent().get(0);

        assertEquals(expected, actual);
    }
}
