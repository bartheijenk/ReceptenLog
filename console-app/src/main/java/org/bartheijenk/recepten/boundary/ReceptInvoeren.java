package org.bartheijenk.recepten.boundary;

import org.bartheijenk.persistence.entity.Ingredient;
import org.bartheijenk.persistence.entity.IngredientInRecept;
import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.entity.Tag;
import org.bartheijenk.persistence.service.ReceptService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bartheijenk.recepten.util.InputOutputUtil.*;

public class ReceptInvoeren implements Boundary {

    private static ReceptInvoeren instance;

    public static ReceptInvoeren receptInvoeren() {
        if (instance == null)
            instance = new ReceptInvoeren();
        return instance;
    }

    @Override
    public void start() {
        System.out.println("--------------------Recept Invoer--------------------");
        Recept recept = leesInvoerVoorRecept();

        recept = ReceptService.getInstance().saveRecept(recept);

        System.out.println("Ingevoerde recept: " + recept.getTitel());
    }

    private Recept leesInvoerVoorRecept() {
        System.out.println("Wat is de titel van het recept?");
        String titel = prompt("Titel: ");

        System.out.println("Hoeveel servings heeft dit recept?");
        int servings = Integer.parseInt(prompt("Servings: "));

        System.out.println("Heeft dit recept een bron? (Mag leeg zijn)");
        String bron = prompt("Bron: ");
        if (bron.equals(""))
            bron = null;

        Set<IngredientInRecept> ingredienten = leesIngredienten();

        System.out.println("Voer nu de bereidingsinstructies in. Elke stap moet met een enter gescheiden zijn.");
        List<String> instructies = readMultipleAsList();
        StringBuilder stringBuilder = new StringBuilder();
        instructies.forEach(s -> stringBuilder.append(s).append("\\n"));

        Set<Tag> tags = leesTags();
        return Recept.builder()
                .titel(titel)
                .servings(servings)
                .bron(bron)
                .ingredienten(ingredienten)
                .instructies(stringBuilder.toString())
                .tags(tags)
                .build();
    }

    private Set<Tag> leesTags() {
        Set<Tag> output = new HashSet<>();
        System.out.println("Voer alle tags (categorien) in voor dit recept. Deze moeten met commas gescheiden worden.");
        String tags = prompt("Tags: ");
        String[] tagsplit = tags.split(",");
        for (String s : tagsplit) {
            output.add(Tag.builder().naam(s.trim()).build());
        }
        return output;
    }

    private Set<IngredientInRecept> leesIngredienten() {
        Set<IngredientInRecept> output = new HashSet<>();

        System.out.println("Voer nu alle ingredienten in. Deze moeten in de vorm: " +
                "'-<hoeveelheid> <eenheid> <naam van ingredient>, <eventueel instructie>" +
                "\nHet hoeft niet per se met enters gescheiden worden maar wel met dashes (-).");

        String ingredientenString = readMultiple();
        String[] ingredientenSplits = ingredientenString.split("-");
        ingredientenSplits = Arrays.copyOfRange(ingredientenSplits, 1, ingredientenSplits.length);

        for (String ingr : ingredientenSplits) {
            ingr = ingr.trim();
            String[] splitIngr = ingr.split(" ", 3);
            String[] ingredientEnInstructie = splitIngr[2].split(",");
            output.add(IngredientInRecept.builder()
                    .hoeveelheid(Double.parseDouble(splitIngr[0].trim()))
                    .eenheid(splitIngr[1].trim())
                    .ingredient(Ingredient.builder().naam(ingredientEnInstructie[0].trim()).build())
                    .instructie(
                            ingredientEnInstructie.length == 2 ? ingredientEnInstructie[1].trim() : ""
                    )
                    .build()
            );
        }
        return output;
    }
}
