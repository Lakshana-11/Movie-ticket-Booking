package com.cts.movieusermanagement.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
    
    private final String secret = "SECRET";

    private static long VALIDITY = 2 * 60 * 1000 * 10;

    private final Map<String, Object> claims = new HashMap<>();

    private <T> T extractClaimFromToken(String token, Function<Claims,T> claimsTFunction){
        final Claims allClaims = extractAllClaims(token);
        return claimsTFunction.apply(allClaims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(token).getBody();
    }

    //generate token
    public String generateToken(UserDetails user){
        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+VALIDITY))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public String getUserName(String token){
        return extractClaimFromToken(token,Claims::getSubject);
    }

    public Date getExpiration(String token){
        return extractClaimFromToken(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public boolean validate(String token, UserDetails user){
        final String userName = user.getUsername();
        return userName.equals(getUserName(token)) && !isTokenExpired(token);
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token.substring(7));
    }
}

