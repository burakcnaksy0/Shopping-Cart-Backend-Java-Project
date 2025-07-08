package b.aksoy.shopcard.service.product;

import b.aksoy.shopcard.entity.Product;
import b.aksoy.shopcard.request.AddProductRequest;
import b.aksoy.shopcard.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest productRequest);
    Product getProductById(long id);
    void deleteProduct(long id);
    Product updateProduct(UpdateProductRequest productRequest, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryNameAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);


}
