package mate.academy.springintro.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartItem.AddBookToCartRequestDto;
import mate.academy.springintro.dto.cartItem.UpdateCartItemRequestDto;
import mate.academy.springintro.mapper.CartItemMapper;
import mate.academy.springintro.mapper.ShoppingCartMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository cartRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;


    @Override
    public ShoppingCartDto getShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        shoppingCart.setUser(user);
        return shoppingCartMapper.toDto(shoppingCart);

    }

    @Override
    public ShoppingCartDto addBookToCart(User user, AddBookToCartRequestDto requestDto) {
        requestDto.getBookId();

    }

    @Override
    public ShoppingCartDto updateCartItem(User user, Long cartItemId, UpdateCartItemRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteCartItem(User user, Long cartItemId) {

    }
}
