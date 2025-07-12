package b.aksoy.shopcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
    // shopping cart(alısverıs sepetı)
    // Represents a user's shopping cart.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO; // Total amount of the cart

    // The mappedBy = "cart" statement indicates that the relationship is managed by CartItem.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<CartItem> cartItems;

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);  // Adds a new item
        cartItem.setCart(this); // Establishes a two-way relationship
        updateTotalAmount();  // Updates the total amount
    }

    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);  // Removes the item from the cart
        cartItem.setCart(null);  // Breaks the relationship
        updateTotalAmount();  // Updates the total amount
    }

    private void updateTotalAmount() {
        this.totalAmount = cartItems.stream().map(item ->
        {
            BigDecimal unitprice = item.getUnitPrice();
            if (unitprice == null) {
                return BigDecimal.ZERO;
            }
            return unitprice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
