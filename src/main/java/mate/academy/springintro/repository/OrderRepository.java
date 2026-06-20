package mate.academy.springintro.repository;

import java.util.Optional;
import mate.academy.springintro.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Optional<Order> findByUserId(Long id);
}
