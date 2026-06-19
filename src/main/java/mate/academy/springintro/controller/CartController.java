package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartitem.AddBookToCartRequestDto;
import mate.academy.springintro.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Shopping cart management",
        description = "Endpoints for managing user's shopping cart")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get shopping cart",
            description = "Get current user's shopping cart")
    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCart(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add book to shopping cart",
            description = "Add a book to current user's shopping cart")
    public ShoppingCartDto addBookToCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddBookToCartRequestDto requestDto
    ) {
        return shoppingCartService.addBookToCart(user, requestDto);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update cart item quantity",
            description = "Update quantity of a book in current user's shopping cart")
    public ShoppingCartDto updateQuantityOfBook(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.updateCartItem(user, cartItemId, requestDto);
    }

    @DeleteMapping("/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete cart item",
            description = "Remove a book from current user's shopping cart")
    public void deleteBookFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId
    ) {
        shoppingCartService.deleteCartItem(user, cartItemId);
    }
}
