package org.bartheijenk.recepten.util;

import org.bartheijenk.persistence.entity.Recept;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class InputOutputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static String readMultiple() {
        StringBuilder output = new StringBuilder();
        System.out.println("Als je klaar bent voer :X in op een nieuwe regel.");
        while (scanner.hasNext()) {
            String s = readLine();
            if (s.equals(":X"))
                break;
            output.append(s);
        }
        return output.toString();
    }

    public static List<String> readMultipleAsList() {
        List<String> list = new LinkedList<>();
        System.out.println("Als je klaar bent voer :X in op een nieuwe regel.");
        while (scanner.hasNext()) {
            String s = readLine();
            if (s.equals(":X"))
                break;
            list.add(s);
        }
        return list;
    }

    public static void vraagDetails(Consumer<Long> consumer) {
        System.out.println("\nWelke optie wilt u hebben?");
        System.out.println("Geef het nummer dat boven staat op.");
        System.out.println("Of geef x om terug te gaan.");
        String s = readLine();

        if (!s.equals("x")) {
            Long keuze = Long.parseLong(s);
            consumer.accept(keuze);
        }
    }

    public static Consumer<? super Recept> printRecipeConsumer() {
        return (obj) -> System.out.println("(" + obj.getId() + ") " + obj.getTitel());
    }


}

