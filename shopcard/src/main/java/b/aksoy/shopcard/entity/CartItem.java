package b.aksoy.shopcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    // sepet ogeleri
    // Represents each product and its quantity in the cart.
    // A cart item is actually a product.
    // But what makes it different from a product is that it is in the cart and kept in quantity within the cart.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // Each item is linked to a product

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public void setTotalPrice() {
        // TotalPrice is calculated by multiplying unitPrice and quantity.
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }

}
