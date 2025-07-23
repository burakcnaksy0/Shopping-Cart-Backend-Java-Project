package b.aksoy.shopcard.request;

import b.aksoy.shopcard.entity.Cart;
import b.aksoy.shopcard.entity.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
