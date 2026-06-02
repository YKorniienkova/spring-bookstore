package mate.academy.springintro.repository.specification.book;

import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR = "author";

    @Override
    public Specification<Book> getSpecification(String params) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(AUTHOR), params);
    }
}
