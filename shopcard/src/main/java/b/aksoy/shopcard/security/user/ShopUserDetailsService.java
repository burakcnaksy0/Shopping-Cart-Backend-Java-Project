package b.aksoy.shopcard.security.user;


import b.aksoy.shopcard.entity.User;
import b.aksoy.shopcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// Bu sınıf, Spring Security'nin "Bir kullanıcıyı kullanıcı adına göre nasıl bulurum?" sorusuna cevap verir.
@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return ShopUserDetails.buildUserService(user);
    }
}
