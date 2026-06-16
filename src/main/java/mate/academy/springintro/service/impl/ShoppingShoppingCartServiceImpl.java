package mate.academy.springintro.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartitem.AddBookToCartRequestDto;
import mate.academy.springintro.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.CartItemMapper;
import mate.academy.springintro.mapper.ShoppingCartMapper;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.CartItemRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto getShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        return shoppingCartMapper.toDto(shoppingCart);

    }

    @Override
    public ShoppingCartDto addBookToCart(User user, AddBookToCartRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateCartItem(User user, Long cartItemId,
                                          UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = getCartItemForUser(user, cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return getShoppingCart(user);
    }

    @Override
    public void deleteCartItem(User user, Long cartItemId) {
        CartItem cartItem = getCartItemForUser(user, cartItemId);
        cartItemRepository.delete(cartItem);
    }

    private CartItem getCartItemForUser(User user, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id: " + cartItemId));
        if (!cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
            throw new EntityNotFoundException(
                    "Can't find cart item by id: " + cartItemId);
        }
        return cartItem;
    }
}
