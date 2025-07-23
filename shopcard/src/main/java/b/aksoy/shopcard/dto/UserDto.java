package b.aksoy.shopcard.dto;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
