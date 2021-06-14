package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.User;
import org.bartheijenk.persistence.service.IUserService;
import org.bartheijenk.recepten.api.payload.LoginResponse;
import org.bartheijenk.recepten.api.util.JwtUtil;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/users")
public class UsersResource implements JsonResource {
    @Context
    private UriInfo uriInfo;

    @Inject
    private IUserService userService;

    @Inject
    private JwtUtil jwtUtil;

    @POST
    @Path("/login")
    public LoginResponse login(User u) {
        try {
            if (!u.getUsername().equals("User")) {
                User user = userService.authenticate(u.getUsername(), u.getPassword());
                String token = jwtUtil.issueToken(u.getUsername(), uriInfo);
                LoginResponse response = new LoginResponse(user);
                response.setToken(token);

                return response;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new NotAuthorizedException("User " + u + " is not authorized");
        }
    }

    @POST
    public User createUser(User user) {
        this.userService.addUser(user);
        user.setPassword("");
        return user;
    }
}
