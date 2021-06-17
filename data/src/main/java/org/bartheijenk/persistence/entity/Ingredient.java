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
        @NamedQuery(name = "Ingredient.findAll", query = "select r from Ingredient r"),
        @NamedQuery(name = "Ingredient.findAllByIds", query = "select t from Ingredient t where t.id in :ids")
})
public class Ingredient implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String naam;
    private int calorienPerHonderd;

    @OneToOne
    private Recept isRecept = null;


}
