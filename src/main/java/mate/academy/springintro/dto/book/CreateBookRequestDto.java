package mate.academy.springintro.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateBookRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    private String description;
    private String coverImage;
    @NotEmpty
    private Set<Long> categoryIds;
}
