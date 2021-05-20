package org.bartheijenk.recepten.boundary;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.service.CategorieService;
import org.bartheijenk.persistence.service.ReceptService;
import org.bartheijenk.recepten.util.ReceptUtils;

import java.util.Comparator;

import static org.bartheijenk.recepten.util.InputOutputUtil.*;

public class ReceptLijstWeergeven implements Boundary {

    private static ReceptLijstWeergeven instance;

    public static ReceptLijstWeergeven receptLijstWeergeven() {
        if (instance == null)
            instance = new ReceptLijstWeergeven();
        return instance;
    }

    private final ReceptService receptService = ReceptService.getInstance();
    private final CategorieService categorieService = CategorieService.getInstance();

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
        receptService.getAllReceptNamenEnID().forEach(printRecipeConsumer());
        vraagDetails(ReceptUtils::printRecept);
    }

    private void vraagCategorie() {
        System.out.println("Dit zijn de mogelijke categorien: ");
        categorieService.getAllCategories().stream()
                .sorted(Comparator.comparingLong(Categorie::getId))
                .forEach(categorie -> System.out.println("(" + categorie.getId().toString() + ") " + categorie.getNaam()));
        vraagDetails(this::printCategorieRecepten);
    }

    private void printCategorieRecepten(Long aLong) {
        Categorie categorie = categorieService.getTagById(aLong);
        if (categorie == null) {
            System.out.println("Gegeven categorie is niet gevonden, probeer het nogmaals.");
            vraagDetails(this::printCategorieRecepten);
        } else {
            receptService.getReceptNamenEnIDPerCategorie(categorie).forEach(printRecipeConsumer());
            vraagDetails(ReceptUtils::printRecept);
        }
    }

}
