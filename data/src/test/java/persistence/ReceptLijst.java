package persistence;

import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;
import persistence.entity.Tag;

import java.util.List;
import java.util.Set;

public class ReceptLijst {
    public static List<Recept> getLijst() {
        Recept recept = Recept.builder()
                .titel("Spaghetti Bolognese")
                .instructies("kook het")
                .servings(4)
                .tag(Tag.builder()
                        .naam("Pasta").build())
                .build();

        recept.getIngredienten().addAll(
                Set.of(
                        IngredientInRecept.builder()
                                .ingredient(Ingredient.builder()
                                        .naam("Spaghetti")
                                        .build())
                                .hoeveelheid(500)
                                .eenheid("gram")
                                .recept(recept).build(),
                        IngredientInRecept.builder()
                                .ingredient(Ingredient.builder()
                                        .naam("Bolognese saus").build())
                                .hoeveelheid(250)
                                .eenheid("mL")
                                .recept(recept).build()
                ));
        Recept recept2 = Recept.builder()
                .titel("Pasta Bolognese")
                .instructies("kook het")
                .servings(4)
                .tag(Tag.builder()
                        .naam("Pasta").build())
                .build();

        recept.getIngredienten().addAll(
                Set.of(
                        IngredientInRecept.builder()
                                .ingredient(Ingredient.builder()
                                        .naam("Pasta")
                                        .build())
                                .hoeveelheid(500)
                                .eenheid("gram")
                                .recept(recept).build(),
                        IngredientInRecept.builder()
                                .ingredient(Ingredient.builder()
                                        .naam("Bolognese saus").build())
                                .hoeveelheid(250)
                                .eenheid("mL")
                                .recept(recept).build(),
                        IngredientInRecept.builder()
                                .ingredient(Ingredient.builder()
                                        .naam("Parmezaanse kaas").build())
                                .hoeveelheid(100)
                                .eenheid("gram")
                                .instructie("geraspt")
                                .recept(recept).build()
                ));
        return List.of(recept, recept2);
    }
}
