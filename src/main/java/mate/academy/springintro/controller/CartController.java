package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.dto.cartItem.AddBookToCartRequestDto;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "", description = "")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto findAll(User user) {
        return shoppingCartService.getShoppingCart(user);
    }

    @PostMapping
    public ShoppingCartDto addBook(User user, AddBookToCartRequestDto requestDto) {
        return shoppingCartService.addBookToCart(user, requestDto);
    }
}
