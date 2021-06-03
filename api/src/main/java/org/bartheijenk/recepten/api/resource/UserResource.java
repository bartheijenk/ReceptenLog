package org.bartheijenk.recepten.api.resource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bartheijenk.persistence.entity.User;
import org.bartheijenk.persistence.service.IUserService;
import org.bartheijenk.recepten.api.util.KeyGenerator;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.LocalDateTime.now;

@Path("/users")
public class UserResource implements JsonResource {
    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    private IUserService userService;

    @POST
    @Path("/login")
    public User login(User u) {
        try {
            User user = userService.authenticate(u.getUsername(), u.getPassword());
            String token = issueToken(u.getUsername());
            user.setToken(token);
            u.setPassword("");

            return user;
        } catch (Exception e) {
            throw new NotAuthorizedException("User " + u + " is not authorized");
        }
    }

    @POST
    public User createUser(User user) {
        return this.userService.addUser(user);
    }

    private String issueToken(String username) {
        Key password = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(now().plusMinutes(30L)))
                .signWith(SignatureAlgorithm.HS512, password)
                .compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
