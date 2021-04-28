package persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;
import persistence.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReceptServiceIT {
    static EntityManager em = Persistence.createEntityManagerFactory("H2-test").createEntityManager();

    private final ReceptService receptService = new ReceptService(em);

    private static Recept recept;

    @BeforeAll
    static void setUp() {
        em.getTransaction().begin();

        recept = Recept.builder()
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
    }

    @AfterEach
    void clearTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
    void saveRecept() {
        Recept actual = receptService.saveRecept(recept);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getIngredienten().stream().findFirst().get().getId()).isNotNull();

    }
}