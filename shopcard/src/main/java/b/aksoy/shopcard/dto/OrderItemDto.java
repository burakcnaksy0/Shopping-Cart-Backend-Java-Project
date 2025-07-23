package b.aksoy.shopcard.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private String productDescription;
    private int quantity;
    private BigDecimal price;
}
