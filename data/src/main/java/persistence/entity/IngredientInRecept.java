package persistence.entity;

import lombok.*;
import persistence.util.Identifiable;

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
    @JoinColumn(name = "recept_id")
    @ToString.Exclude
    private Recept recept;

}
