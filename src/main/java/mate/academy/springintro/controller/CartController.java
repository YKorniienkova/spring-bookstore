package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartitem.AddBookToCartRequestDto;
import mate.academy.springintro.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Shopping cart management",
        description = "Endpoints for managing user's shopping cart")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")

    public ShoppingCartDto findAll(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCart(user);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")

    public ShoppingCartDto addBookToCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddBookToCartRequestDto requestDto
    ) {
        return shoppingCartService.addBookToCart(user, requestDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")

    public ShoppingCartDto updateQuantityOfBook(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.updateCartItem(user, cartItemId, requestDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")

    public void deleteBookFromCart(
            @AuthenticationPrincipal User user, @RequestBody @Valid Long cartItemId
    ) {
        shoppingCartService.deleteCartItem(user, cartItemId);
    }
}
