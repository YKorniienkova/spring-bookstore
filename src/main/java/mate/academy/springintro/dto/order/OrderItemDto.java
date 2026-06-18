package mate.academy.springintro.dto.order;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.Order;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
