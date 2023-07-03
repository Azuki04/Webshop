package ch.web.web_shop.security.jwt;

import ch.web.web_shop.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Date;

/** JwtUtils is used to:
 * - generate a JWT token from username, date, expiration, secret
 * - validate a JWT token
 * - parse username from JWT token
 * - get JWT token from Authorization header (by removing Bearer prefix)
 */

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    // JWT secret key
    @Value("${webshop.app.jwtSecret}")
    private String jwtSecret;

    // 1 hour
    @Value("${webshop.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * generateJwtToken() method:
     * @param authentication
     * @return
     * - generate a JWT token from username, date, expiration, secret
     * - build JWT token using Jwts.builder()
     * - set claims of the token, like Issuer, Expiration, Subject, and the ID
     * - sign the JWT using the HS512 algorithm and secret key
     * - compact() method builds the JWT and serializes it to a compact, URL-safe string
     * - return the JWT
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}