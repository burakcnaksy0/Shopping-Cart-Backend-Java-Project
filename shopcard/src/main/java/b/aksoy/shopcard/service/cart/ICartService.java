package b.aksoy.shopcard.service.cart;

import b.aksoy.shopcard.entity.Cart;

import java.math.BigDecimal;

public interface ICartService {
    public Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
