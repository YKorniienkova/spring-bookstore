package mate.academy.springintro.repository.specification;

import mate.academy.springintro.dto.BookSearchParametrDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametrDto searchParametrs);
}
