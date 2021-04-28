package org.bartheijenk.recepten.boundary;

import org.bartheijenk.recepten.ReceptUtils;
import persistence.ReceptService;
import persistence.entity.Recept;

import java.util.List;

import static org.bartheijenk.recepten.ConsoleApp.prompt;
import static org.bartheijenk.recepten.ConsoleApp.vraagDetails;

public class ReceptZoeken implements Boundary {

    private static ReceptZoeken instance;

    public static ReceptZoeken receptZoeken() {
        if (instance == null)
            instance = new ReceptZoeken();
        return instance;
    }

    private final ReceptService receptService = ReceptService.getInstance();
    private List<Recept> resultaten;

    @Override
    public void start() {
        while (true) {
            System.out.println("--------------------Recept Zoeken--------------------");
            System.out.println("Voer uw zoektermen in, voer :X in om terug te gaan.");
            String zoektermen = prompt("Zoek: ");

            if (zoektermen.equals(":X"))
                return;
            else
                geefResultaten(zoektermen);
        }
    }

    private void geefResultaten(String zoektermen) {
        resultaten = receptService.zoekRecepten(zoektermen);

        for (int i = 1; i < resultaten.size() + 1; i++) {
            Recept recept = resultaten.get(i - 1);
            System.out.println("(" + i + ") " + recept.getTitel());
        }
        vraagDetails(this::printRecept);
    }

    private void printRecept(Long aLong) {
        ReceptUtils.printRecept(resultaten.get(aLong.intValue() - 1));
    }
}
