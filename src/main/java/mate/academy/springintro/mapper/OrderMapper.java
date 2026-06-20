package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.order.CreateOrderRequestDto;
import mate.academy.springintro.dto.order.OrderDto;
import mate.academy.springintro.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemsMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    Order toModel(CreateOrderRequestDto requestDto);
}
