package org.bartheijenk.recepten.boundary;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.CategorieService;
import org.bartheijenk.persistence.service.RandomizerService;
import org.bartheijenk.recepten.util.ReceptUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.bartheijenk.recepten.util.InputOutputUtil.*;

public class Randomizer implements Boundary {
    private static Randomizer instance;
    private final RandomizerService randomizerService = RandomizerService.getInstance();

    public static Randomizer randomizer() {
        if (instance == null)
            instance = new Randomizer();
        return instance;
    }

    @Override
    public void start() {
        while (true) {
            System.out.println("--------------------Randomizer--------------------");
            System.out.println("Waarop wilt u randomizen?");
            System.out.println("(1) [Alles] ");
            System.out.println("(2) [Op Categorie] ");
            System.out.println("(x) [terug] ");

            switch (readLine()) {
                case "1":
                    randomizeAll();
                    break;
                case "2":
                    randomizeCategories();
                    break;
                case "x":
                    return;
            }
        }
    }

    private void randomizeAll() {
        System.out.println("------Alles----");
        System.out.println("Hoeveel recepten wilt u?");
        int hoeveel = Integer.parseInt(readLine());
        randomizerService.getRandomizedList(hoeveel).forEach(printRecipeConsumer());
        vraagDetails(ReceptUtils::printRecept);
    }

    private void randomizeCategories() {
        System.out.println("------Categorie----");
        System.out.println("Dit zijn de mogelijke categorien: ");
        CategorieService.getInstance().getAllCategories().forEach(categorie -> System.out.println("(" + categorie.getId().toString() + ") " + categorie.getNaam()));

        System.out.println("Meerdere opties zijn mogelijk, gescheiden door een komma.");
        String s = readLine();
        List<Long> categories = Arrays.stream(s.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        System.out.println("Hoeveel recepten wilt u?");
        int hoeveel = Integer.parseInt(readLine());

        List<Recept> randomizedListWithCategories = randomizerService.getRandomizedListWithCategories(hoeveel, categories);
        if (randomizedListWithCategories == null) {
            System.out.println("Verkeerde invoer! Probeer het nogmaals");
            randomizeCategories();
        } else {
            randomizedListWithCategories.forEach(printRecipeConsumer());

            vraagDetails(ReceptUtils::printRecept);
        }

    }
}
