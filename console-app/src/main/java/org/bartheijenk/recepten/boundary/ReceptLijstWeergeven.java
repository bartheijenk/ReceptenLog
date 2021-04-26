package org.bartheijenk.recepten.boundary;

import persistence.ReceptService;

import java.util.Map;

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
                    break;
                case "x":
                    return;
            }
        }
    }

    private void printAll() {
        Map<Long, String> allReceptNamenEnID = receptService.getAllReceptNamenEnID();

        allReceptNamenEnID.forEach((aLong, s) -> System.out.println("(" + aLong.toString() + ") " + s));
    }

}
