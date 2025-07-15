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


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createNewCart() {
        try {
            Long cartId = cartService.initializeNewCart();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("New cart created successfully", cartId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error creating new cart: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCartResponse(@PathVariable long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear operation is success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable long cartId) {
        try {
            BigDecimal total = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", total));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }










}
