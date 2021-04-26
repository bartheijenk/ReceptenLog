import persistence.ReceptInvoerenService;
import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;
import persistence.entity.Tag;

import java.util.Set;

public class ConsoleApp {
    public static void main(String[] args) {
        new ConsoleApp().start();
    }

    public void start() {
        ReceptInvoerenService receptInvoerenService = new ReceptInvoerenService();

        Recept recept = Recept.builder()
                .titel("Bolognese")
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
                                .recept(recept).build()
                ));


        receptInvoerenService.saveRecept(recept);
    }
}
