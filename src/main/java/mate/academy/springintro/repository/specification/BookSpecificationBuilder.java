package mate.academy.springintro.repository.specification;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.book.BookSearchParameterDto;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.repository.specification.book.AuthorSpecificationProvider;
import mate.academy.springintro.repository.specification.book.TitleSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final AuthorSpecificationProvider authorSpecificationProvider;
    private final TitleSpecificationProvider titleSpecificationProvider;

    @Override
    public Specification<Book> build(BookSearchParameterDto searchParametrs) {
        Specification<Book> specification = Specification.where((Specification<Book>) null);
        if (searchParametrs.title() != null
                && searchParametrs.title().length() > 0) {
            specification = specification.and(titleSpecificationProvider
                    .getSpecification(searchParametrs.title()));
        }
        if (searchParametrs.author() != null
                && searchParametrs.author().length() > 0) {
            specification = specification.and(authorSpecificationProvider
                    .getSpecification(searchParametrs.author()));
        }
        return specification;
    }
}
