package b.aksoy.shopcard.repository;

import b.aksoy.shopcard.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // DELETE FROM cart_item WHERE cart_id = ?1
    // Deletes all CartItems belonging to a specific cart ID
    void deleteAllByCartId(Long id);
}
