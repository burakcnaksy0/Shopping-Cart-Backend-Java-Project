package b.aksoy.shopcard.service.product;
import b.aksoy.shopcard.dto.ImageDto;
import b.aksoy.shopcard.dto.ProductDto;
import b.aksoy.shopcard.entity.Category;
import b.aksoy.shopcard.entity.Image;
import b.aksoy.shopcard.entity.Product;
import b.aksoy.shopcard.exception.ProductNotFoundException;
import b.aksoy.shopcard.repository.CategoryRepository;
import b.aksoy.shopcard.repository.ImageRepository;
import b.aksoy.shopcard.repository.ProductRepository;
import b.aksoy.shopcard.request.AddProductRequest;
import b.aksoy.shopcard.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest productRequest) {
        /*
        When adding a new product, it checks the category:
        If there is a category, it uses it,
        If not, it creates a new category and saves it.
        */
        /*
        The category name is checked with categoryRepository.findByName().
        If it does not exist, a new category is created and saved with save().
        The createProduct(...) method is called.
        The product is written to the database with productRepository.save().
         */
        Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName())).
                orElseGet(() -> {
                    Category newCategory = new Category(productRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        return productRepository.save(createProduct(productRequest, category));
    }

    public Product createProduct(AddProductRequest productRequest, Category category) {
        return new Product(
                productRequest.getName(),
                productRequest.getBrand(),
                productRequest.getPrice(),
                productRequest.getInventory(),
                productRequest.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

    }

    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id).
                ifPresentOrElse(productRepository::delete, () -> {
                    throw new ProductNotFoundException("Product not found");
                });
    }

    // The main method called from outside.
    // It will find the product and start the update process.
    @Override
    public Product updateProduct(UpdateProductRequest productRequest, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, productRequest))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    // A helper method that performs the actual update operation.
    // Replaces fields of the product with new information.
    // Assigning new attributes to existing product values
    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest productRequest) {
        existingProduct.setName(productRequest.getName());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setInventory(productRequest.getInventory());
        existingProduct.setDescription(productRequest.getDescription());

        Category category = categoryRepository.findByName(productRequest.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory_Name(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryNameAndBrand(String category, String brand) {
        return productRepository.findByCategory_NameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndNameContainingIgnoreCase(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProductDtoList(List<Product> products) {
        return products.stream().map(this :: convertToDto).toList();
    }


    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
