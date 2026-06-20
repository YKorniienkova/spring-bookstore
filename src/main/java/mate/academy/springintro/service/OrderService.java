package mate.academy.springintro.service;

import java.util.List;
import mate.academy.springintro.dto.order.CreateOrderRequestDto;
import mate.academy.springintro.dto.order.OrderDto;
import mate.academy.springintro.dto.order.OrderItemDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequestDto;
import mate.academy.springintro.model.User;

public interface OrderService {
    OrderDto addOrder(User user, CreateOrderRequestDto requestDto);

    List<OrderDto> getAllOrders(User user);

    OrderDto updateStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
