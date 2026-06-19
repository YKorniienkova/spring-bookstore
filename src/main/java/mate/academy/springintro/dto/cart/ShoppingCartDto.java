package mate.academy.springintro.dto.cart;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.dto.cartitem.CartItemDto;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
