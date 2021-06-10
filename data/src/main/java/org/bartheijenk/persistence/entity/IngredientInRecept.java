package org.bartheijenk.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredientinrecept")
@NamedQueries({
        @NamedQuery(name = "IngredientInRecept.findAll", query = "select r from IngredientInRecept r"),
        @NamedQuery(name = "IngredientInRecept.findAllByIds", query = "select t from IngredientInRecept t where t.id in :ids")
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
    @JsonBackReference
    private Recept recept;

}
