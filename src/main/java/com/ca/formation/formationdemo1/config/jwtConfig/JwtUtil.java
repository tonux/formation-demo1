package com.ca.formation.formationdemo1.config.jwtConfig;


import com.ca.formation.formationdemo1.models.Utilisateur;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    // mettre le jwtSecret= "Base-64"
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
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 *1000))// 1 jour
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String refreshAccesToken(Utilisateur utilisateur){
        return Jwts.builder()
                .setSubject(utilisateur.getName()+","+utilisateur.getUsername())
                .setIssuer("formation.ca")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 *1000))// 1 semaine
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    // Recuperer le username
    public String getUsername(String token){
        Claims claims = getClaims(token);
       return claims.getSubject().split(",")[1];
    }

    // Recuperer les claims
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Recuperer la Date d'expiration
    public Date getExpirationDate(String token){
       return getClaims(token).getExpiration();
    }


    // Verifier la validité du token
    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            System.out.println("Invalide Signature Jwt - "+ex.getMessage());
        } catch (ExpiredJwtException ex){
            System.out.println("Expiration du Jwt - "+ex.getMessage());
        }catch (UnsupportedJwtException ex){
            System.out.println("Token jwt non supporté - "+ex.getMessage());
        }catch (IllegalArgumentException ex){
            System.out.println("Invalide claims Jwt - "+ex.getMessage());
        }catch (MalformedJwtException ex){
            System.out.println("Token jwt mal formatter - "+ex.getMessage());
        }

        return false;
    }
}
