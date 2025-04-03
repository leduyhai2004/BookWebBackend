package com.duyhai.bookweb_backend.service;

import com.duyhai.bookweb_backend.entity.Privilege;
import com.duyhai.bookweb_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Autowired
    private UserService userService;
    //create jwt base on username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<String, Object>();
        User user = userService.findByUsername(username);
        boolean isAdmin = false;
        boolean isStaff = false;
        boolean isUser = false;

        //set quyen cho claims de khi giai ma claims thi lay dc ten quyen
        if(user != null && user.getPrivilegeList().size() > 0) {
            List<Privilege> privilegeList = user.getPrivilegeList();
            for(Privilege pri : privilegeList){
                if(pri.getName().equals("ADMIN")){
                    isAdmin = true;
                }
                if(pri.getName().equals("STAFF")){
                    isStaff = true;
                }
                if(pri.getName().equals("USER")){
                    isUser = true;
                }
            }
        }
        claims.put("isAdmin", isAdmin);
        claims.put("isStaff", isStaff);
        claims.put("isUser", isUser);
        return createToken(claims,username);
    }

    //Tao JWT voi cac  claims da chon
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 30L * 24 * 60 * 60 * 1000)) //30days
                .signWith(SignatureAlgorithm.HS256,getSignKey())
                .compact();  //Het han sau 30p
    }

    //get seceret key
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //extract claims: trich xuat thong tin
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
    }

    // trich xuat thon tin cho 1 claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsFunction){
        final Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    //kiem tra thoi gian het han cua jwt
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //kiem tra username cua jwt
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //kiem tra xem 1 token hop le hay khong
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // kiem tra tinh hop le cua token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
