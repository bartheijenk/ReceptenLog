package org.bartheijenk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "Categorie.findAll", query = "select r from Categorie r")
})
public class Categorie implements Identifiable<Long> {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String naam;
}
