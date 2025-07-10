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

    // ex. sql query -> SELECT * FROM product WHERE category.name = ?
    List<Product> findByCategory_Name(String categoryName);

    // ex. sql query -> SELECT * FROM product WHERE brand = ?
    List<Product> findByBrand(String brand);

    // ex. sql query -> SELECT * FROM product WHERE category.name = ? AND brand = ?
    List<Product> findByCategory_NameAndBrand(String categoryName, String brand);

    // ex. sql query -> SELECT * FROM product WHERE name LIKE %?%
    List<Product> findByName(String name);

    // ex. sql query -> SELECT * FROM product WHERE brand = ? AND name LIKE %?%
    List<Product> findByBrandAndNameContainingIgnoreCase(String brand, String name);

    // ex. sql query -> SELECT COUNT(*) FROM product WHERE brand = ? AND name = ?
    Long countByBrandAndName(String brand, String name);
}
