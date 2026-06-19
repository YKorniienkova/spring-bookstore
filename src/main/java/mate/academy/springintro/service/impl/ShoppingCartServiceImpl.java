package mate.academy.springintro.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartitem.AddBookToCartRequestDto;
import mate.academy.springintro.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.CartItemMapper;
import mate.academy.springintro.mapper.ShoppingCartMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.CartItemRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        return shoppingCartMapper.toDto(shoppingCart);

    }

    @Override
    public ShoppingCartDto addBookToCart(User user, AddBookToCartRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: "
                        + requestDto.getBookId())
        );
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems()
                .stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();
        if (existingCartItem.isEmpty()) {
            CartItem cartItem = cartItemMapper.toModel(requestDto);
            cartItem.setBook(book);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
        } else {
            existingCartItem.get().setQuantity(existingCartItem.get()
                    .getQuantity() + requestDto.getQuantity());
        }
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateCartItem(User user, Long cartItemId,
                                          UpdateCartItemRequestDto requestDto) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                        shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id: " + cartItemId));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return getShoppingCart(user);
    }

    @Override
    public void deleteCartItem(User user, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + user.getId()));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                        shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by id: " + cartItemId));
        shoppingCart.getCartItems().remove(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

}
