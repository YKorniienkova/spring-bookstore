package mate.academy.springintro.repository.specification.book;

import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public Specification<Book> getSpecification(String params) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), params);
    }
}
