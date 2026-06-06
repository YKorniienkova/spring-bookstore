package mate.academy.springintro.repository.specification;

import mate.academy.springintro.dto.book.BookSearchParameterDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameterDto searchParametrs);
}
