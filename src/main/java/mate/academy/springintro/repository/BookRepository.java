package mate.academy.springintro.repository;

import mate.academy.springintro.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    @Query("FROM Book b LEFT JOIN FETCH b.categories WHERE b.id = :id")
    Page<Book> findAllByCategoryId(Long categoryId, Pageable pageable);
}
