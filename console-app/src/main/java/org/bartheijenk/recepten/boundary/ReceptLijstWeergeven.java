package org.bartheijenk.recepten.boundary;

import persistence.ReceptService;
import persistence.entity.Recept;

import java.util.function.BiConsumer;

import static org.bartheijenk.recepten.ConsoleApp.readLine;

public class ReceptLijstWeergeven implements Boundary {

    private static ReceptLijstWeergeven instance;

    public static ReceptLijstWeergeven receptLijstWeergeven() {
        if (instance == null)
            instance = new ReceptLijstWeergeven();
        return instance;
    }

    private final ReceptService receptService = ReceptService.getInstance();

    @Override
    public void start() {
        while (true) {
            System.out.println("--------------------Recepten Weergeven--------------------");
            System.out.println("Wat wil je weergegeven hebben?");
            System.out.println("(1) [Alles] ");
            System.out.println("(x) [terug] ");

            switch (readLine()) {
                case "1":
                    printAll();
                    vraagDetails();
                    break;
                case "x":
                    return;
            }
        }
    }

    private void printAll() {
        receptService.getAllReceptNamenEnID().forEach(printListConsumer());
    }

    private void vraagDetails() {
        System.out.println("Welk recept wilt u hebben?");
        System.out.println("Geef het nummer dat boven staat op.");
        System.out.println("Of geef x om terug te gaan.");
        String s = readLine();

        if (!s.equals("x")) {
            Long keuze = Long.parseLong(s);
            printRecept(keuze);
        }
    }

    private void printRecept(Long keuze) {
        Recept rec = receptService.getReceptById(keuze);
        if (rec == null) {
            System.out.println("Gegeven recept is niet gevonden, probeer het nogmaals.");
            vraagDetails();
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
            System.out.println(rec.getInstructies());
        }
    }

    private BiConsumer<Long, String> printListConsumer() {
        return (aLong, s) -> System.out.println("(" + aLong.toString() + ") " + s);
    }

}
