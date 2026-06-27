package mate.academy.springintro.dto.category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
