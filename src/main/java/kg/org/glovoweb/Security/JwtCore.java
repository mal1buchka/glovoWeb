package kg.org.glovoweb.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import kg.org.glovoweb.Exceptions.JwtValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtCore {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    private final int accessExpiration;
    private final int refreshExpiration;

    @Autowired
    public JwtCore(
            @Value("${jwt.access.secret}") String jwtAccess,
            @Value("${jwt.refresh.secret}") String jwtRefresh,
            @Value("${jwt.access.expiration}") int accessExpiration,
            @Value("${jwt.refresh.expiration}") int refreshExpiration
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccess));
        this.refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefresh));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }
    public String getTokenFromRequest(HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String generateAccessToken(UserDetailImpl userDetail) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationAccessInstant = new Date(now.getTime() + accessExpiration);
        return Jwts.builder()
                .subject(userDetail.getUsername())
                .issuedAt(now)
                .expiration(expirationAccessInstant)
                .signWith(accessSecretKey)
                .claim("roles", userDetail.getAuthorities())
                .claim("username", userDetail.getUsername())
                .compact();
    }

    public String generateRefreshToken(UserDetailImpl userDetailImpl) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationRefreshInstant = new Date(now.getTime() + refreshExpiration);
        return Jwts.builder()
                .issuedAt(now)
                .subject(userDetailImpl.getUsername())
                .expiration(expirationRefreshInstant)
                .signWith(refreshSecretKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessSecretKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshSecretKey);
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token has expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtValidationException("Unsupported token", e);
        } catch (MalformedJwtException e) {
            throw new JwtValidationException("Malformed token", e);
        } catch (SignatureException e) {
            throw new JwtValidationException("Invalid signature", e);
        } catch (IllegalArgumentException e) {
            throw new JwtValidationException("Illegal argument in token", e);
        }
    }

    public String getClaimFromAccessToken(String accessToken, String claimName) {
        Claims claims = getClaimsFromAccessToken(accessToken);
        if (claims != null) {
            return claims.get(claimName, String.class);
        }
        return null;
    }

    private Claims getClaimsFromAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(accessSecretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            log.error("Error extracting claims from AccessToken: {}", e.getMessage());
        }
        return null;
    }

    public boolean isExpired(String accessToken) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(accessSecretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
            return claims.getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            log.error("Token has expired: {}", e.getMessage());
            throw e;
        }catch (JwtException e) {
            log.error("Error has occured while checking the expiration of accessToken: {}", e.getMessage());
            throw  e;
        }
    }
    public String getEmailFromAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(accessSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public String getEmailFromRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(refreshSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}