package b.aksoy.shopcard.security.user;

import b.aksoy.shopcard.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// veritabanınızda tuttuğunuz User nesnesini (entity), Spring Security'nin anlayabileceği bir formata dönüştürür.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopUserDetails implements UserDetails {
        private Long id;
        private String email;
        private String password;

        // Kullanıcının sahip olduğu yetkileri (rolleri) tutan bir koleksiyondur.
        // Spring Security, bir kullanıcının belirli bir sayfaya veya metoda erişim izni olup olmadığını kontrol etmek için bu koleksiyonu kullanır.
        private Collection<GrantedAuthority> authorities;

        // Bu metot, veritabanınızda tuttuğunuz User nesnesini (entity), Spring Security'nin anlayabileceği bir formata dönüştürür.
        public static ShopUserDetails buildUserService(User user) {
            List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new ShopUserDetails(user.getId(), user.getEmail(), user.getPassword(), authorities);
        }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
