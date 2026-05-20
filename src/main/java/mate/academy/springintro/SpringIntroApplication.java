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
                Book book1 = new Book();
                book1.setAuthor("Taras");
                book1.setTitle("Book");
                book1.setIsbn("978-3-16-148410-0");
                book1.setPrice(BigDecimal.valueOf(200));
                book1.setDescription("It's a book");
                book1.setCoverImage("image");
                bookService.save(book1);
                System.out.println(bookService.findAll());
            }
        };
    }
}
