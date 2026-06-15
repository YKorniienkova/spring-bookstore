package mate.academy.springintro.dto.cartItem;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.ShoppingCart;

@Getter
@Setter
public class UpdateCartItemRequestDto {
    @Min(1)
    private int quantity;
}
