package b.aksoy.shopcard.controller;

import b.aksoy.shopcard.entity.Category;
import b.aksoy.shopcard.exception.CategoryNotFoundException;
import b.aksoy.shopcard.response.ApiResponse;
import b.aksoy.shopcard.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {

        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Found!",categories));

    }

    @GetMapping("/category/{categoryId}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {

        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponse("Found!",category));

    }
    @GetMapping("/category/{categoryName}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {

        Category category = categoryService.getCategoryByName(categoryName);
        return ResponseEntity.ok(new ApiResponse("Found!",category));

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {

        Category category = categoryService.addCategory(name);
        return ResponseEntity.ok(new ApiResponse("Add operation is success!",category));

    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {

        Category category = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse("Delete operation is success!",category));

    }

    @PutMapping("/category/{categoryId}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category name) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category !=null){
                categoryService.updateCategory(name,categoryId);
                return ResponseEntity.ok(new ApiResponse("Update operation is success!",category));
            }
        }catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update operation is failed!",HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
