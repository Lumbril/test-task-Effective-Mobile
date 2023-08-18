package effective.mobile.code.utils.jwt.services;

import effective.mobile.code.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@NoArgsConstructor
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.access-token.expiration}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);

        return claims.get("user_id", Long.class);
    }

    public String extractType(String token) {
        Claims claims = extractAllClaims(token);

        return claims.get("token_type", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, "access", ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(
            User userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, "refresh", REFRESH_TOKEN_EXPIRATION);
    }

    public String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            String type,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setHeaderParam("typ","JWT")
                .claim("user_id", userDetails.getId())
                .claim("token_type", type)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        if (!isTokenExpired(token)) {
            return false;
        }

        return true;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
