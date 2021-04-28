package org.bartheijenk.recepten.boundary;

import org.bartheijenk.recepten.ReceptUtils;
import persistence.ReceptService;
import persistence.TagService;
import persistence.entity.Tag;

import java.util.function.BiConsumer;

import static org.bartheijenk.recepten.ConsoleApp.readLine;
import static org.bartheijenk.recepten.ConsoleApp.vraagDetails;

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
        vraagDetails(ReceptUtils::printRecept);
    }

    private void vraagCategorie() {
        System.out.println("Dit zijn de mogelijke categorien: ");
        tagService.getAllTags().forEach(tag -> System.out.println("(" + tag.getId().toString() + ") " + tag.getNaam()));
        vraagDetails(this::printCategorieRecepten);
    }

    private void printCategorieRecepten(Long aLong) {
        Tag tag = tagService.getTagById(aLong);
        if (tag == null) {
            System.out.println("Gegeven categorie is niet gevonden, probeer het nogmaals.");
            vraagDetails(this::printCategorieRecepten);
        } else {
            receptService.getReceptNamenEnIDPerTag(tag).forEach(printMapConsumer());
            vraagDetails(ReceptUtils::printRecept);
        }
    }



    private BiConsumer<Long, String> printMapConsumer() {
        return (aLong, s) -> System.out.println("(" + aLong.toString() + ") " + s);
    }

}
