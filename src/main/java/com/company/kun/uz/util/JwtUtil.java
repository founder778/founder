package com.company.kun.uz.util;

import com.company.kun.uz.dto.profile.ProfilJwtDto;
import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.expections.AuthExeption;
import com.company.kun.uz.expections.ForBiddenExeption;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "kalitso`z";
    public static String create(Integer id){

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+ (60 * 60 *1000)));
        jwtBuilder.setIssuer("kun.uz");
        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static String create(Integer id, ProfileRole role){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+ (60 * 60 *1000)));
        jwtBuilder.setIssuer("kun.uz");
        jwtBuilder.claim("role" ,role.name());
        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public  static ProfilJwtDto decode(String jwt){
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();
        ProfileRole role = ProfileRole.valueOf((String) claims.get("role"));
        return new ProfilJwtDto(Integer.parseInt(id),role);
    }

    public  static Integer decodeandGetId(String jwt){
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims) jws.getBody();
        Integer id  = Integer.valueOf(claims.getSubject());
        return id;
    }
    public static Integer getCurrentUser(HttpServletRequest request) throws RuntimeException {

        Integer userId = (Integer) request.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return userId;
    }


    public static ProfilJwtDto getProfile(HttpServletRequest request,ProfileRole role) throws RuntimeException {

        ProfilJwtDto jwtDTO = (ProfilJwtDto) request.getAttribute("profilejwtdto");
        if (jwtDTO == null) {
            throw new AuthExeption("Not Authorized");
        }
        if(!jwtDTO.getRole().equals(role)){
            throw new ForBiddenExeption("forbidden");
        }
        return jwtDTO;
    }

    public static ProfilJwtDto getProfile(HttpServletRequest request) {

        ProfilJwtDto jwtDTO = (ProfilJwtDto) request.getAttribute("profilejwtdto");
        if (jwtDTO == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return jwtDTO;
    }
}
