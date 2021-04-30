package org.bartheijenk.recepten;

import org.bartheijenk.persistence.util.DataInitializer;
import org.bartheijenk.recepten.boundary.HomeScreen;

public class ConsoleApp {


    public static void main(String[] args) {
        System.out.println("Loading Database connection....");
        DataInitializer.start();
        new HomeScreen().start();
    }
}
