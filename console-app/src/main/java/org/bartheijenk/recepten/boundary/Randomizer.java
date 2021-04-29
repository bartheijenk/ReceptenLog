package org.bartheijenk.recepten.boundary;

import org.bartheijenk.recepten.ReceptUtils;
import persistence.RandomizerService;

import static org.bartheijenk.recepten.ConsoleApp.*;

public class Randomizer implements Boundary {
    private static Randomizer instance;

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
                    System.out.println("Nog niet geimplementeerd");
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
        RandomizerService.getInstance().getRandomizedList(hoeveel).forEach(printMapConsumer());
        vraagDetails(ReceptUtils::printRecept);
    }
}
