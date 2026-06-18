package mate.academy.springintro.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookToCartRequestDto {
    @NotNull
    private Long bookId;
    @Positive
    private int quantity;
}
