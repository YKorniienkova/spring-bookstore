package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
