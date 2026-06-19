package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.order.CreateOrderRequestDto;
import mate.academy.springintro.dto.order.OrderDto;
import mate.academy.springintro.dto.order.OrderItemDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequestDto;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders and order items")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create order",
            description = "Create a new order from user's shopping cart")
    public OrderDto addOrder(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateOrderRequestDto requestDto
    ) {
        return orderService.addOrder(user, requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order history",
            description = "Retrieve current user's order history")
    public List<OrderDto> getAllOrders(
            @AuthenticationPrincipal User user
    ) {
        return orderService.getAllOrders(user);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status",
            description = "Update status of an existing order")
    public OrderDto updateOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid UpdateOrderStatusRequestDto requestDto
    ) {
        return orderService.updateStatus(orderId, requestDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order items",
            description = "Retrieve all items from a specific order")
    public List<OrderItemDto> getOrderItems(
            @PathVariable Long orderId
    ) {
        return orderService.getOrderItems(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order item by id",
            description = "Retrieve a specific item from a specific order")
    public OrderItemDto getOrderItemsById(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.getOrderItemById(orderId, itemId);
    }
}
