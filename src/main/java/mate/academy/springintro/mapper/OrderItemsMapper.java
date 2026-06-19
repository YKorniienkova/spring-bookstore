package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.order.OrderItemDto;
import mate.academy.springintro.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemsMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);
}
