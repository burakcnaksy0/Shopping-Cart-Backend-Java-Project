package b.aksoy.shopcard.service.cart;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.User;

import java.math.BigDecimal;

public interface ICartService {
    public Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
}
