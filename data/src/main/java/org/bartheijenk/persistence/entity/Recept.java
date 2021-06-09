package org.bartheijenk.persistence.entity;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "Recept.findAll", query = "select r from Recept r")
})
public class Recept implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titel;
    @Column(nullable = false)
    private int servings;
    private String bron;

    @Column(nullable = false)
    @Lob
    private String instructies;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "recept_id")
    @EqualsAndHashCode.Exclude
    @JsonbProperty
    private Set<IngredientInRecept> ingredienten = new HashSet<>();

    @Singular
    @ManyToMany(
            )
    @JoinTable(name = "recept_categorie",
            joinColumns = @JoinColumn(name = "recept_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Set<Categorie> categories;

    public void addIngredientInRecept(IngredientInRecept ingredientInRecept) {
        ingredientInRecept.setRecept(this);
        ingredienten.add(ingredientInRecept);
    }
}
