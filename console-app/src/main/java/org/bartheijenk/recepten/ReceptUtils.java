package org.bartheijenk.recepten;

import org.apache.commons.text.StringEscapeUtils;
import persistence.ReceptService;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;

import java.util.Comparator;

import static org.bartheijenk.recepten.ConsoleApp.readLine;
import static org.bartheijenk.recepten.ConsoleApp.vraagDetails;

public class ReceptUtils {

    public static void printRecept(Long keuze) {
        Recept rec = ReceptService.getInstance().getReceptById(keuze);
        if (rec == null) {
            System.out.println("Gegeven recept is niet gevonden, probeer het nogmaals.");
            vraagDetails(ReceptUtils::printRecept);
        } else {
            printRecept(rec);
        }
    }

    public static void printRecept(Recept rec) {
        System.out.println(
                "Titel: " + rec.getTitel() +
                        "\nServings: " + rec.getServings()
        );

        System.out.print(rec.getBron() == null ? "" : "Bron: " + rec.getBron() + "\n");

        System.out.print("Tags: ");
        rec.getTags().forEach(tag -> System.out.print(tag.getNaam() + ", "));

        System.out.println("\nIngredienten: ");
        rec.getIngredienten().stream()
                .sorted(Comparator.comparingLong(IngredientInRecept::getId))
                .forEach(
                        i -> System.out.println("- " + i.getHoeveelheid() + " " + i.getEenheid() + " " + i.getIngredient().getNaam() + (
                                i.getInstructie() == null || i.getInstructie().equals("") ? "" : ", " + i.getInstructie())
                        )
                );
        System.out.println("Bereidingswijze: ");
        System.out.println(StringEscapeUtils.unescapeJava(rec.getInstructies()));
        System.out.println("\nDruk op enter om weer terug te gaan.");
        readLine();
    }
}
