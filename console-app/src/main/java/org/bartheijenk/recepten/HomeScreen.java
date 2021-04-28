package org.bartheijenk.recepten;

import org.bartheijenk.recepten.boundary.Boundary;

import static org.bartheijenk.recepten.ConsoleApp.readLine;
import static org.bartheijenk.recepten.boundary.ReceptInvoeren.receptInvoeren;
import static org.bartheijenk.recepten.boundary.ReceptLijstWeergeven.receptLijstWeergeven;

public class HomeScreen implements Boundary {
    public void start() {
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("Dit is het Recepten Log");
            System.out.println("Wat wilt u doen?");
            System.out.println("----------------------------------------------");

            System.out.println("(1) [Recept Invoeren] ");
            System.out.println("(2) [Recepten Weergeven] ");
            System.out.println("(x) [Afsluiten] ");

            try {
                switch (readLine()) {
                    case "1":
                        receptInvoeren().start();
                        break;
                    case "2":
                        receptLijstWeergeven().start();
                        break;
                    case "x":
                        System.out.println("Tot ziens.");
                        return;
                    default:
                        System.out.println("Ongeldige keuze; probeer opnieuw.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Dit is ongeldige invoer. Probeer het opnieuw.");
            } catch (RuntimeException t) {
                System.out.println("Er ging iets mis... Probeer het opnieuw. ");
                System.out.println("Foutmelding: " + t.getMessage());
                t.printStackTrace();
            } catch (Exception e) {
                System.out.println("Er ging iets vreselijk mis... ");
                System.out.println("Foutmelding: " + e.getMessage());
                System.out.println("Neem contact op met de leverancier.");
                System.out.println("Tot ziens.");
            }
        }
    }
}
