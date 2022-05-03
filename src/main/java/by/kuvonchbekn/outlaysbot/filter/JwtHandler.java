package by.kuvonchbekn.outlaysbot.filter;

import by.kuvonchbekn.outlaysbot.entity.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class JwtHandler {

    private final long expireTime = 1000 * 36 * 60 * 60;
    private final String secretKey = "xiaizotqxaqxilisecretkeyword";

    public String generateToken(String username, Set<Role> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token){
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (SignatureException e){
            log.error("Invalid JWT signature");
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token");
        }catch (ExpiredJwtException e){
            log.error("Expired JWT token");
        }catch (UnsupportedJwtException e){
            log.error("Unsupported JWT token");
        }catch (IllegalArgumentException e){
            log.error("JWT claims string is empty");
        }catch (Exception e){
            log.error("Not known error");
        }
        return null;
    }
}
