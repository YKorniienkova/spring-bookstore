package mate.academy.springintro.dto.order;

import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;
}
