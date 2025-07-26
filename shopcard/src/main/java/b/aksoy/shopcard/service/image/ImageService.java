package b.aksoy.shopcard.service.image;

import b.aksoy.shopcard.dto.ImageDto;
import b.aksoy.shopcard.entity.Image;
import b.aksoy.shopcard.entity.Product;
import b.aksoy.shopcard.exception.ImageNotFoundException;
import b.aksoy.shopcard.repository.ImageRepository;
import b.aksoy.shopcard.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService; // Used to get the product an image is linked to.

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {

        Image image = getImageById(id);
        imageRepository.delete(image);
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                byte[] bytes = file.getBytes();

                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(bytes); // artık byte[]
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                ImageDto dto = new ImageDto();
                dto.setId(savedImage.getId());
                dto.setFileName(savedImage.getFileName());
                // downloadUrl burada atanmasın, controller'da eklenebilir

                savedImageDtos.add(dto);

            } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + file.getOriginalFilename(), e);
            }
        }

        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes()); // artık byte[]
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update image with ID: " + imageId, e);
        }
    }
}
