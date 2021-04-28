package org.bartheijenk.recepten.boundary;

import org.apache.commons.text.StringEscapeUtils;
import persistence.ReceptService;
import persistence.TagService;
import persistence.entity.Recept;
import persistence.entity.Tag;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.bartheijenk.recepten.ConsoleApp.readLine;

public class ReceptLijstWeergeven implements Boundary {

    private static ReceptLijstWeergeven instance;

    public static ReceptLijstWeergeven receptLijstWeergeven() {
        if (instance == null)
            instance = new ReceptLijstWeergeven();
        return instance;
    }

    private final ReceptService receptService = ReceptService.getInstance();
    private final TagService tagService = TagService.getInstance();

    @Override
    public void start() {
        while (true) {
            System.out.println("--------------------Recepten Weergeven--------------------");
            System.out.println("Wat wil je weergegeven hebben?");
            System.out.println("(1) [Alles] ");
            System.out.println("(2) [Op Categorie] ");
            System.out.println("(x) [terug] ");

            switch (readLine()) {
                case "1":
                    printAllRecepten();
                    break;
                case "2":
                    vraagCategorie();
                    break;
                case "x":
                    return;
            }
        }
    }

    private void printAllRecepten() {
        receptService.getAllReceptNamenEnID().forEach(printMapConsumer());
        vraagDetails(this::printRecept);
    }

    private void vraagCategorie() {
        System.out.println("Dit zijn de mogelijke categorien: ");
        tagService.getAllTags().forEach(tag -> System.out.println("(" + tag.getId().toString() + ") " + tag.getNaam()));
        vraagDetails(this::printCategorieRecepten);
    }

    private void vraagDetails(Consumer<Long> consumer) {
        System.out.println("\nWelke optie wilt u hebben?");
        System.out.println("Geef het nummer dat boven staat op.");
        System.out.println("Of geef x om terug te gaan.");
        String s = readLine();

        if (!s.equals("x")) {
            Long keuze = Long.parseLong(s);
            consumer.accept(keuze);
        }
    }

    private void printCategorieRecepten(Long aLong) {
        Tag tag = tagService.getTagById(aLong);
        if (tag == null) {
            System.out.println("Gegeven recept is niet gevonden, probeer het nogmaals.");
            vraagDetails(this::printCategorieRecepten);
        } else {
            receptService.getReceptNamenEnIDPerTag(tag).forEach(printMapConsumer());
            vraagDetails(this::printRecept);
        }
    }

    private void printRecept(Long keuze) {
        Recept rec = receptService.getReceptById(keuze);
        if (rec == null) {
            System.out.println("Gegeven recept is niet gevonden, probeer het nogmaals.");
            vraagDetails(this::printRecept);
        } else {

            System.out.println(
                    "Titel: " + rec.getTitel() +
                            "\nServings: " + rec.getServings()
            );

            System.out.print(rec.getBron() == null ? "" : "Bron: " + rec.getBron());

            System.out.println("Tags: ");
            rec.getTags().forEach(tag -> System.out.print(tag.getNaam() + ", "));

            System.out.println("\nIngredienten: ");
            rec.getIngredienten().forEach(
                    i -> System.out.println("- " + i.getHoeveelheid() + " " + i.getEenheid() + " " + i.getIngredient().getNaam() + ", " + i.getInstructie())
            );

            System.out.println("Bereidingswijze: ");
            System.out.println(StringEscapeUtils.unescapeJava(rec.getInstructies()));
        }
    }

    private BiConsumer<Long, String> printMapConsumer() {
        return (aLong, s) -> System.out.println("(" + aLong.toString() + ") " + s);
    }

}
