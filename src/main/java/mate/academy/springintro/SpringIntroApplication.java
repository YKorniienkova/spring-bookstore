package mate.academy.springintro;

import java.math.BigDecimal;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringIntroApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntroApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("Taras");
                book.setTitle("Book");
                book.setIsbn("978-3-16-148410-0");
                book.setPrice(BigDecimal.valueOf(200));
                book.setDescription("It's a book");
                book.setCoverImage("image");
                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }
}
