package b.aksoy.shopcard.controller;

import b.aksoy.shopcard.request.LoginRequest;
import b.aksoy.shopcard.response.ApiResponse;
import b.aksoy.shopcard.response.JwtResponse;
import b.aksoy.shopcard.security.jwt.JwtUtils;
import b.aksoy.shopcard.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    // Spring Security'nin kimlik doğrulama mekanizmasını yöneten bir bileşendir.
    // Spring Security, kullanıcıların kimlik bilgilerini doğrulamak ve yetkilendirme işlemlerini yapmak için AuthenticationManager arayüzünü kullanır.
    private final AuthenticationManager authenticationManager;
    // JWT (JSON Web Token) oluşturmak ve doğrulamak için kullanılan yardımcı sınıf.
    // JWT, kullanıcıların kimlik bilgilerini güvenli bir şekilde transfer etmek için kullanılan bir mekanizmadır.
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    private ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),loginRequest.getPassword()));
            // Spring Security, kimlik doğrulama başarılı olduğunda, kimlik bilgilerini güvenli bir şekilde transfer etmek için AuthenticationManager arayüzünü kullanır.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // JWT, kullanıcıların kimlik bilgilerini güvenli bir şekilde transfer etmek için kullanılan bir mekanizmadır.
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(),jwt);
            return ResponseEntity.ok(new ApiResponse("Login successful",jwtResponse));
        } catch (AuthenticationException e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(e.getMessage(),null));
        }

    }

}
