package org.bartheijenk.persistence;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Ingredient;
import org.bartheijenk.persistence.entity.IngredientInRecept;
import org.bartheijenk.persistence.entity.Recept;

import java.util.List;
import java.util.Set;

public class ReceptLijst {
    public static List<Recept> getLijst() {
        Recept recept = Recept.builder()
                .titel("Spaghetti Bolognese")
                .instructies("kook het")
                .servings(4)
                .category(Categorie.builder()
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
                .category(Categorie.builder()
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
