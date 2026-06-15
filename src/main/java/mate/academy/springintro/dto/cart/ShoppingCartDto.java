package mate.academy.springintro.dto.cart;

import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.User;

import java.util.Set;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
