package b.aksoy.shopcard.security.jwt;

import b.aksoy.shopcard.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    // JWT (JSON Web Token) için gizli anahtar
    // Bu anahtar sayesinde token'ın bizim tarafımızdan oluşturulduğundan ve yolda değiştirilmediğinden emin oluruz.
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    // Oluşturulan bir token'ın ne kadar süre (milisaniye cinsinden) geçerli olacağını belirtir.
    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    // Verilen authentication objesi kullanarak bir token oluşturur.
    // Kullanıcı başarıyla giriş yaptığında, o kullanıcı için bir JWT oluşturur.
    // authentication.getPrincipal() metodu, o an kimliği doğrulanmış kullanıcı nesnesini verir. 
    // Bu nesneyi kendi ShopUserDetails sınıfımıza dönüştürüyoruz.
    // Kullanıcının rollerini (ROLE_USER, ROLE_ADMIN gibi) alıp bir liste haline getirir.
    public String generateTokenForUser(Authentication authentication) {
        
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .setSubject(userPrincipal.getEmail()) // Kullanıcının email adresini token'ın subject'ine atar.
                .claim("id",userPrincipal.getId()) // Kullanıcının id'sini token'ın claim'ine atar.
                .claim("roles",roles) // Kullanıcının rollerini token'ın claim'ine atar.
                .setIssuedAt(new Date()) // Token'ın oluşturulma tarihini atar.
                .setExpiration(new Date(new Date().getTime() + expirationTime)) // Token'ın son kullanma tarihini atar.
                .signWith(key(), SignatureAlgorithm.HS256) // JWT için kullanılan anahtarı oluşturur.
                .compact(); // Tüm bu bilgileri birleştirip String formatında bir JWT oluşturur.
    }

    // application.properties dosyasından alınan jwtSecret string'ini alır
    // Bu string'yi base64 decode eder ve bir anahtar olarak kullanır.
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // JWT token'ından kullanıcının email adresini alır.
    // Token'ı doğrulamak için imzalarken kullandığımız anahtarın aynısını kullanır.
    // Eğer anahtar uyuşmazsa veya token değiştirilmişse, bu adımda bir hata fırlatılır. 
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token) // Token'ı ayrıştırır ve doğrular.
                .getBody().getSubject(); // Token'ın subject'ini döndürür.
    }

    // JWT token'ın geçerli olup olmadığını kontrol eder.
    public boolean validateToken(String token) {
        // Token'ı ayrıştırmaya çalışır. Eğer parseClaimsJws metodu herhangi bir hata fırlatmazsa, 
        // bu token'ın imzasının doğru, formatının geçerli ve süresinin dolmamış olduğu anlamına gelir
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
