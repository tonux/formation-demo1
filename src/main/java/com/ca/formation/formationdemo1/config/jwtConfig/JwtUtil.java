package com.ca.formation.formationdemo1.config.jwtConfig;


import com.ca.formation.formationdemo1.models.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    // mettre le jwtSecret= ""
    private final String jwtSecret="TWV0dHJlIG1vbiB0b2tlbiBlbiBiYXNlIDY0IA==";

    // generer JWT

    public String generateAccesToken(Utilisateur utilisateur){
        Claims claims = Jwts.claims().setSubject(utilisateur.getUsername());
        claims.put("scopes", utilisateur.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(utilisateur.getName()+","+utilisateur.getUsername())
                .setIssuer("formation.ca")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1 * 24*60*60*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
