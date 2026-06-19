package mate.academy.springintro.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.order.CreateOrderRequestDto;
import mate.academy.springintro.dto.order.OrderDto;
import mate.academy.springintro.dto.order.OrderItemDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.exception.OrderProcessingException;
import mate.academy.springintro.mapper.OrderItemsMapper;
import mate.academy.springintro.mapper.OrderMapper;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.OrderItem;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.OrderItemRepository;
import mate.academy.springintro.repository.OrderRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto addOrder(User user, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find shopping cart by user id:"
                                + user.getId())
                );
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Shopping cart is empty");
        }
        Order order = orderMapper.toModel(requestDto);
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());

            order.getOrderItems().add(orderItem);

            totalPrice =
                    totalPrice.add(orderItem.getPrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        order.setTotal(totalPrice);
        Order savedOrder = orderRepository.save(order);

        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderDto> getAllOrders(User user) {
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto updateStatus(
            Long orderId, UpdateOrderStatusRequestDto requestDto
    ) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(
                        "Can't find order with id: " + orderId
                )
        );
        order.setStatus(requestDto.getStatus());
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {
        orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Can't find order with id: " + orderId)
        );

        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderItemsMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Can't find order with id: " + orderId
                        )
                );
        return orderItemsMapper.toDto(orderItem);
    }
}
