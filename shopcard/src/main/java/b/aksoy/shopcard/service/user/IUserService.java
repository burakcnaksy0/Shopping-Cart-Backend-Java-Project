package b.aksoy.shopcard.service.user;

import b.aksoy.shopcard.dto.UserDto;
import b.aksoy.shopcard.entity.User;
import b.aksoy.shopcard.request.CreateUserRequest;
import b.aksoy.shopcard.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest userRequest);
    User updateUser(UpdateUserRequest userRequest , Long userId);
    void deleteUser(Long id);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
