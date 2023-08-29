package com.sayan.AuthJwt.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtProvider {
    private final String secretKey = "273trgwfyeg982iygl83i7g19yoqr83hbfqeofq/fehufhwqifhbqoffakbfwpohfebjsa";
    private final long expireTime = 60000*60*5L;


    public Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }


    public Boolean validateToken(String token) {
        return parseToken(token) != null;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        if(claims != null){
            return claims.getSubject();
        }
        return null;
    }

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expireTime);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }
}
