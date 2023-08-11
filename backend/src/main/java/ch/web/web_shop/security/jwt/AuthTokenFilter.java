package ch.web.web_shop.security.jwt;

import java.io.IOException;

import ch.web.web_shop.security.services.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/** AuthTokenFilter is used to:
 * - get JWT token from Authorization header (by removing Bearer prefix)
 * - if the request has JWT token, validate it, parse username from it
 * - from username, get UserDetails to create an Authentication object
 * - set the current UserDetails in SecurityContext using setAuthentication(authentication) method
 * - continue filter execution chain by calling FilterChain.doFilter(request, response)
 * - doFilterInternal() method is called for every HTTP request
 * - if the request is authenticated, Spring Security will set the authentication inside SecurityContext and allow the request to be successfully completed
 * - OncePerRequestFilter ensures that this filter is only executed once per request
 */

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /** doFilterInternal() method:
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     * - get JWT token from Authorization header (by removing Bearer prefix)
     * - if the request has JWT token, validate it, parse username from it
     * - from username, get UserDetails to create an Authentication object
     * - set the current UserDetails in SecurityContext using setAuthentication(authentication) method
     * - continue filter execution chain by calling FilterChain.doFilter(request, response)
     * - doFilterInternal() method is called for every HTTP request
     * - if the request is authenticated, Spring Security will set the authentication inside SecurityContext and allow the request to be successfully completed
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /** parseJwt() method:
     * @param request
     * @return
     * - get JWT token from Authorization header (by removing Bearer prefix)
     * - if the request has JWT token, validate it, parse username from it
     * - from username, get UserDetails to create an Authentication object
     * - set the current UserDetails in SecurityContext using setAuthentication(authentication) method.
     * - return the JWT
     */
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}