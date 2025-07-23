package b.aksoy.shopcard.service.cart;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.User;
import b.aksoy.shopcard.exception.CartNotFoundException;
import b.aksoy.shopcard.repository.CartItemRepository;
import b.aksoy.shopcard.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

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
    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id); // First clears items ; delete operation database
        cart.getCartItems().clear();  // First clears items ; delete operation memory
        cartRepository.deleteById(id);  // Then clears cart

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
       return Optional.ofNullable(getCartByUserId(user.getId()))
               .orElseGet(()->{
                   Cart cart = new Cart();
                   cart.setUser(user);
                   return cartRepository.save(cart);
               });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

}
