package com.example.orderingfood.security;

import com.example.orderingfood.domain.User;
import com.example.orderingfood.exception.WrongLoginCredentialsException;
import com.example.orderingfood.model.AuthenticationRequest;
import com.example.orderingfood.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${secretKey}")
    private String secretKey;

    private final UserService userService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .claim("role", user.getUserType())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String[] jwtSubject = extractUsername(token).split(",");
        final String role = (String) extractClaim(token, claims -> claims.get("role"));
        return jwtSubject[1].equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && role.equals(userDetails.getAuthorities().iterator().next().getAuthority());
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateTokenOnLogin(AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        User user = userService.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new WrongLoginCredentialsException("Wrong login credentials"));
        if (!BCrypt.checkpw(authenticationRequest.getPassword(), user.getPassword())) {
            throw new WrongLoginCredentialsException("Wrong login credentials");
        }
        user.setUsername(authenticationRequest.getUsername());
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(user.getUserType())));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return generateToken(user);
    }

}