package b.aksoy.shopcard.service.cartItem;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.CartItem;
import b.aksoy.shopcard.entity.Product;
import b.aksoy.shopcard.exception.CartItemNotFoundException;
import b.aksoy.shopcard.exception.ProductNotFoundException;
import b.aksoy.shopcard.exception.QuantityNotNegativeValueException;
import b.aksoy.shopcard.repository.CartItemRepository;
import b.aksoy.shopcard.repository.CartRepository;
import b.aksoy.shopcard.service.cart.ICartService;
import b.aksoy.shopcard.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);

        // 2. Get the product
        Product product = productService.getProductById(productId);

        // 3. Check if the product already exists in the cart
        Optional<CartItem> existingItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (quantity < 0) {
            throw new QuantityNotNegativeValueException("Not enough quantity");
        }
        if (existingItem.isPresent()) {
            // 4. If exists, update quantity
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice();
            cartItemRepository.save(cartItem);
        } else {
            // 5. If not exists, create new CartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setUnitPrice(product.getPrice());
            newCartItem.setTotalPrice();

            // Add to cart (this will set the cart reference)
            cart.addCartItem(newCartItem);

            // Save the cart item
            cartItemRepository.save(newCartItem);
        }

        // Save the cart to update total amount
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);

        if (itemToRemove.getQuantity() > 1) {
            // Quantity'yi 1 azalt
            itemToRemove.setQuantity(itemToRemove.getQuantity() - 1);

            // Total price'ı güncelle
            cart.updateTotalAmount();

            // Değişiklikleri kaydet
            cartItemRepository.save(itemToRemove);
            cartRepository.save(cart);
        } else {
            // Quantity 1 ise tamamen kaldır
            cart.removeCartItem(itemToRemove);  // Bu zaten updateTotalAmount() çağırıyor
            cartItemRepository.delete(itemToRemove);
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("There is no such product in the cart."));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();

        cart.updateTotalAmount();
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Product not found in cart"));
    }
}