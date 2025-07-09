package b.aksoy.shopcard.service.category;

import b.aksoy.shopcard.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    Category deleteCategory(Long id);

}
