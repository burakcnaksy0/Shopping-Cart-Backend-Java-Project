package b.aksoy.shopcard.service.image;

import b.aksoy.shopcard.dto.ImageDto;
import b.aksoy.shopcard.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    // MultipartFile : Used for file upload operations from the user. It is provided by Java.
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files , Long productId); // (List of files uploaded by the user, Id of the product the image is linked to. )
    void updateImage(MultipartFile file , Long imageId);
}
