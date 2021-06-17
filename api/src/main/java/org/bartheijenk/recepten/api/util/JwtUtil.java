package org.bartheijenk.recepten.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import static java.time.LocalDateTime.now;

@ApplicationScoped
public class JwtUtil {


    @Inject
    private KeyGenerator keyGenerator;

    public String issueToken(String username, UriInfo uriInfo) {
        Key password = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(now().plusMinutes(30L)))
                .signWith(SignatureAlgorithm.HS512, password)
                .compact();
    }

    public String refreshToken(String token) {
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        System.out.println(payload);
        return payload;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
