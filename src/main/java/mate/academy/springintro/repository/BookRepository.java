package mate.academy.springintro.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
