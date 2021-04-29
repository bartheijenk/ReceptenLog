package org.bartheijenk.recepten.boundary;

import org.bartheijenk.recepten.ReceptUtils;
import persistence.RandomizerService;
import persistence.TagService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bartheijenk.recepten.ConsoleApp.*;

public class Randomizer implements Boundary {
    private static Randomizer instance;
    private RandomizerService randomizerService = RandomizerService.getInstance();
    ;

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
                    randomizeTags();
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
        randomizerService.getRandomizedList(hoeveel).forEach(printMapConsumer());
        vraagDetails(ReceptUtils::printRecept);
    }

    private void randomizeTags() {
        System.out.println("------Categorie----");
        System.out.println("Dit zijn de mogelijke categorien: ");
        TagService.getInstance().getAllTags().forEach(tag -> System.out.println("(" + tag.getId().toString() + ") " + tag.getNaam()));
        System.out.println("Meerdere opties zijn mogelijk, gescheiden door een komma.");
        String s = readLine();
        List<Long> tags = Arrays.stream(s.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        System.out.println("Hoeveel recepten wilt u?");
        int hoeveel = Integer.parseInt(readLine());
        Map<Long, String> randomizedListWithTags = randomizerService.getRandomizedListWithTags(hoeveel, tags);
        if (randomizedListWithTags == null) {
            System.out.println("Verkeerde invoer! Probeer het nogmaals");
            randomizeTags();
        } else {
            randomizedListWithTags.forEach(printMapConsumer());

            vraagDetails(ReceptUtils::printRecept);
        }

    }
}
