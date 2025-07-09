package b.aksoy.shopcard.service.category;

import b.aksoy.shopcard.entity.Category;
import b.aksoy.shopcard.exception.AlreadyExistsCategoryException;
import b.aksoy.shopcard.exception.CategoryNotFoundException;
import b.aksoy.shopcard.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(()-> new AlreadyExistsCategoryException(category.getName()+ " already exists."));
    }

    @Override
    public Category updateCategory(Category category , Long id ) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public Category deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete ,
                () -> {throw new CategoryNotFoundException("Category not found");});
        return null;
    }
}
