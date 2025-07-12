package b.aksoy.shopcard.controller;

import b.aksoy.shopcard.dto.ProductDto;
import b.aksoy.shopcard.entity.Product;
import b.aksoy.shopcard.exception.ProductNotFoundException;
import b.aksoy.shopcard.request.AddProductRequest;
import b.aksoy.shopcard.request.UpdateProductRequest;
import b.aksoy.shopcard.response.ApiResponse;
import b.aksoy.shopcard.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedToProductDto = productService.getConvertedProductDtoList(products);
            return ResponseEntity.ok(new ApiResponse("Found!", convertedToProductDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found!", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/{productName}/products")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> convertedToProductDto = productService.getConvertedProductDtoList(products);
            return ResponseEntity.ok(new ApiResponse("Found!", convertedToProductDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> convertedToProductDto = productService.getConvertedProductDtoList(products);
            return ResponseEntity.ok(new ApiResponse("Found!", convertedToProductDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> convertedToProductDto = productService.getConvertedProductDtoList(products);
            return ResponseEntity.ok(new ApiResponse("Found!", convertedToProductDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add operation success!", newProduct));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(value = "/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest productName) {
        try {
            Product product = productService.getProductById(productId);
            if (productName != null) {
                productService.updateProduct(productName, productId);
                return ResponseEntity.ok(new ApiResponse("Update operation success!", product));
            }
        }catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update operation failed!",HttpStatus.INTERNAL_SERVER_ERROR ));
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Delete operation success!", product));
        }catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/by/brand_and_name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
           List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
           if (products.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body(new ApiResponse("No products found", null));
           }
            List<ProductDto> convertedToProductDto = productService.getConvertedProductDtoList(products);
           return ResponseEntity.ok(new ApiResponse("Found!", convertedToProductDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/by/category_and_brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryNameAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryNameAndBrand(category, brand);
            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found", null));
            }
            List<ProductDto> convertedToProductDto = productService.getConvertedProductDtoList(products);
            return ResponseEntity.ok(new ApiResponse("Found!", convertedToProductDto));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String productName) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, productName);
            return ResponseEntity.ok(new ApiResponse("Product Count!", productCount));
        }catch (Exception e) {
                return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
