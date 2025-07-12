package b.aksoy.shopcard.service.cart;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.CartItem;
import b.aksoy.shopcard.exception.CartNotFoundException;
import b.aksoy.shopcard.repository.CartItemRepository;
import b.aksoy.shopcard.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id); // First clears items
        cart.getCartItems().clear();  // First clears items
        cartRepository.deleteById(id);  // Then clears cart

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
