package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.cart.ShoppingCartDto;
import mate.academy.springintro.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
