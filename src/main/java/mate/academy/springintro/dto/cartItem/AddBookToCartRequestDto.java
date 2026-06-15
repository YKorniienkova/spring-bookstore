package mate.academy.springintro.dto.cartItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookToCartRequestDto {
    private Long bookId;
    private int quantity;
}
