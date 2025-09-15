package com.divyanshu.backend.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static  final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    public  String generteToken(UserDetails userDetails){
        return  Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis() + EXPIRATION_TIME)))
                .signWith(SECRET_KEY)
                .compact();
    }
    public  boolean isTokeValid(String token,UserDetails userDetails){
        final  String username = extractUsername(token);
        return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private  boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }
    public  String extractUsername(String token){
        return  extractClaim(token , Claims::getSubject);
    }
    private  Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private  <T> T extractClaim(String token , Function<Claims ,T> claimsResolver ){
        final  Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims);
    }
    private  Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }
}
