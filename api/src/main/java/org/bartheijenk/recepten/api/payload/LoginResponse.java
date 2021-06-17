package org.bartheijenk.recepten.api.payload;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bartheijenk.persistence.entity.User;

@Data
@NoArgsConstructor
public class LoginResponse {

    public LoginResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.token = user.getToken();
    }

    private String id;
    private String username;
    private String token;
}
