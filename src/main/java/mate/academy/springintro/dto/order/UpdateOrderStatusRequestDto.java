package mate.academy.springintro.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.Order;

@Getter
@Setter
public class UpdateOrderStatusRequestDto {
    @NotNull
    private Order.Status status;
}
