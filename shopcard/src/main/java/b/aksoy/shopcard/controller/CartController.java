package b.aksoy.shopcard.controller;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.exception.CartNotFoundException;
import b.aksoy.shopcard.response.ApiResponse;
import b.aksoy.shopcard.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCartResponse(@PathVariable long cartId) {

            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));

    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable long cartId) {
                    cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear operation is success", null));
            }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable long cartId) {

            BigDecimal total = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", total));


    }
}
