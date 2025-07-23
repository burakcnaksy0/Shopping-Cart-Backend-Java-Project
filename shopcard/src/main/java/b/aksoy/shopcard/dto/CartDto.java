package b.aksoy.shopcard.dto;

import b.aksoy.shopcard.entity.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private Set<CartItemDto> cartItems;
    private BigDecimal totalAmount;
    //private UserDto user;




}
