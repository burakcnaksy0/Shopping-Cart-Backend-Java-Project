package b.aksoy.shopcard.service.cartItem;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.CartItem;
import b.aksoy.shopcard.entity.Product;
import b.aksoy.shopcard.exception.CartItemNotFoundException;
import b.aksoy.shopcard.exception.ProductNotFoundException;
import b.aksoy.shopcard.repository.CartItemRepository;
import b.aksoy.shopcard.repository.CartRepository;
import b.aksoy.shopcard.service.cart.ICartService;
import b.aksoy.shopcard.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        // 2. Get the product
        // 3. Check if the product already in the cart
        // 4. If yes , then increase the quantity with the requested quantity
        // 5. If no , the initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        Optional<CartItem> existingItem = cartItemRepository.findById(cartId);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        }else{
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setUnitPrice(product.getPrice());

            cart.addCartItem(newCartItem);
            cartItemRepository.save(newCartItem);
        }
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemoved = getCartItem(cartId,productId);
        cart.removeCartItem(itemToRemoved);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(item ->
                {
                    // If present
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                },
                    // Not If present
                    () -> {throw new CartItemNotFoundException("There is no such product in the cart.");}
                );
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    }
}
