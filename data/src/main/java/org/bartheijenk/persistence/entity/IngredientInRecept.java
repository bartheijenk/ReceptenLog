package org.bartheijenk.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredientinrecept")
@NamedQueries({
        @NamedQuery(name = "IngredientInRecept.findAll", query = "select r from IngredientInRecept r")
})
public class IngredientInRecept implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double hoeveelheid;
    @Column(nullable = false)
    private String eenheid;

    private String instructie;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "recept_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Recept recept;

}
