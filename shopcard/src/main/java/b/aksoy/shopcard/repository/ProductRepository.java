package b.aksoy.shopcard.repository;

import b.aksoy.shopcard.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*
    We can automatically use the following methods:
        findAll()
        findById()
        save()
        deleteById()
        etc.
     */

    // Special Query Methods

    // ex. sql query -> SELECT * FROM product WHERE category_id = (SELECT id FROM category WHERE name = ?)
    List<Product> findByCategoryName(String category);

    // ex. sql query -> SELECT * FROM product WHERE brand = ?
    List<Product> findByBrand(String brand);

    // ex. sql query -> SELECT * FROM product WHERE category_id = (SELECT id FROM category WHERE name = ?) AND brand = ?
    List<Product> findByCategoryNameAndBrand(String category, String brand);

    // ex. sql query -> SELECT * FROM product WHERE name LIKE %?%
    List<Product> findByName(String name);

    // ex. sql query -> SELECT * FROM product WHERE brand = ? AND name LIKE %?%
    List<Product> findByBrandAndName(String brand, String name);

    // ex. sql query -> SELECT COUNT(*) FROM product WHERE brand = ? AND name = ?
    Long countByBrandAndName(String brand, String name);
}
