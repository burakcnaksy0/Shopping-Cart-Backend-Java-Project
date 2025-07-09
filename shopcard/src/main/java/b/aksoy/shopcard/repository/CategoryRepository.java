package b.aksoy.shopcard.repository;

import b.aksoy.shopcard.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
    boolean existsByName(String name);
}
