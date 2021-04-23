package persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class IngredientInReceptKey implements Serializable {
    @Column(name = "recept_id")
    Long receptId;

    @Column(name = "ingredient_id")
    Long ingredientId;
}
