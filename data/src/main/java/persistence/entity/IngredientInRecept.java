package persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import persistence.util.Identifiable;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "IngredientInRecept.findAll", query = "select r from IngredientInRecept r")
})
public class IngredientInRecept implements Identifiable<IngredientInReceptKey> {

    @EmbeddedId
    private IngredientInReceptKey id;

    @Column(nullable = false)
    private double hoeveelheid;
    @Column(nullable = false)
    private String eenheid;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @MapsId("receptId")
    @JoinColumn(name = "recept_id")
    private Recept recept;
}
