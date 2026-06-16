package mate.academy.springintro.service;

import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartitem.AddBookToCartRequestDto;
import mate.academy.springintro.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.springintro.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(User user);

    ShoppingCartDto addBookToCart(User user, AddBookToCartRequestDto requestDto);

    ShoppingCartDto updateCartItem(User user, Long cartItemId, UpdateCartItemRequestDto requestDto);

    void deleteCartItem(User user, Long cartItemId);
}
