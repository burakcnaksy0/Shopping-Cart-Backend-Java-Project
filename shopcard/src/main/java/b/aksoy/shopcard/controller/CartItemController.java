package b.aksoy.shopcard.controller;

import b.aksoy.shopcard.exception.CartItemNotFoundException;
import b.aksoy.shopcard.exception.CartNotFoundException;
import b.aksoy.shopcard.response.ApiResponse;
import b.aksoy.shopcard.service.cart.CartService;
import b.aksoy.shopcard.service.cartItem.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final CartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            Long finalCartId = cartId;
            if (finalCartId == null) {
                // If cartId is not provided, initialize a new cart
                // You need to add a method to ICartService to initialize a new cart and return its ID
                // For example, in ICartService: Long initializeNewCart();
                // And in CartService:
                // @Override
                // public Long initializeNewCart() {
                //     Cart newCart = new Cart();
                //     return cartRepository.save(newCart).getId();
                // }
                finalCartId = cartService.initializeNewCart(); // Assume you have this method
            }

            cartItemService.addItemToCart(finalCartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully. Cart ID: " + finalCartId, null));
        } catch (CartNotFoundException e) { // Catch CartNotFoundException specifically
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // Use NOT_FOUND for resource not found
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (
                CartItemNotFoundException e) { // This exception should ideally not be thrown by addItemToCart unless it's for product not found in cart initially, but not for new adds
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // Use BAD_REQUEST if something is wrong with the request payload
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) { // Catch other potential exceptions like ProductNotFoundException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An unexpected error occurred: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Remove item from cart", null));
        } catch (CartItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update item quantity", null));
        } catch (CartItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


}
