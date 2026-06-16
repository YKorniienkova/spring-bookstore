package mate.academy.springintro.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookToCartRequestDto {
    private Long bookId;
    private int quantity;
}
