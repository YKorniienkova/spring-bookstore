package mate.academy.springintro.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springintro.model.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"book"})
    Optional<OrderItem> findByIdAndOrderId(Long itemId, Long orderId);

    @EntityGraph(attributePaths = {"book"})
    List<OrderItem> findAllByOrderId(Long orderId);
}
