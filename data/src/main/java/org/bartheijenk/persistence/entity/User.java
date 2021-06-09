package org.bartheijenk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bartheijenk.persistence.util.PasswordUtils;

import javax.persistence.*;

import static java.util.UUID.randomUUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "User.findByLoginAndPassword", query = "SELECT u FROM User u WHERE u.username = :login AND u.password = :password")
public class User implements Identifiable<String> {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String token;

    @PrePersist
    private void setRandomPass() {
        id = randomUUID().toString();
        password = PasswordUtils.digestPassword(password);
    }
}
