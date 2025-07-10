package b.aksoy.shopcard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price; // BigDecimal --> It makes precise calculations and is the best choice for money.
    private int inventory; // Stock Number
    private String description;

    /*
    !! CascadeType.ALL usage is dangerous , Because when a product is deleted, the category is also deleted! (If there are no other products.)
    When an action is performed on the product (such as registration, update, deletion), apply the same action to the category.
    category_id == foreign key --> There will be a category_id column in the product table. This column will show which category this product belongs to
    */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;  // Each product has a category. But each category have one or more than one product.

    /*
     mappedBy = "product" --> This relationship is established with the following field in the Image class: {private Product product;} In other words, the other party manages the relationship (Image class).
     cascade = CascadeType.ALL --> If a product is deleted, the images associated with that product will also be deleted.
     orphanRemoval = true --> If you stop associating a product image with that product, that image will be automatically deleted from the database.
    */
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> images; // Each product have one or more than one image.

    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
