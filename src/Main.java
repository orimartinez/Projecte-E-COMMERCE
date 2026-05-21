import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Article;
import model.Camisa;
import model.Pantalo;
import persistence.GestioBD;
import persistence.GestioJSON;

public class Main {
    public static void main(String[] args) {
        Main p = new Main();
        p.principal();
    }

    // Atributs globals de la classe Main
    Scanner sc = new Scanner(System.in);
    GestioJSON gestorJSON = new GestioJSON();
    GestioBD gestorBD = new GestioBD();

    public void principal() {

        boolean sortirPrograma = false;

        while (sortirPrograma == false) {
            System.out.println("\n===== Selecciona una opció =====");
            System.out.println("1. Importació d'articles");
            System.out.println("2. Gestió d'articles");
            System.out.println("3. Gestió de clients");
            System.out.println("4. TPV");
            System.out.println("5. Consultes vendes per client");
            System.out.println("6. Consultes vendes per article");
            System.out.println("7. Càlcul de beneficis totals");
            System.out.println("8. Recompra automàtica d'articles");
            System.out.println("0. Sortir del menú");

            int opcioMenuPrincipal = llegirEnter();

            switch (opcioMenuPrincipal) {
                case 1:
                    executarImportacioArticles();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 0:
                    sortirPrograma = sortirMenu();
                    break;
                default:
                    System.out.println("Opció no vàlida. Torna-ho a provar.");
                    break;
            }
        }
    }

    public void executarImportacioArticles() {
        System.out.println("--- Iniciant importació d'articles des del JSON ---");

        // 1. Llegim el fitxer de text JSON utilitzant el gestor global
        ArrayList<Article> articlesImportats = gestorJSON.llegirArticlesJSON("data/PE11_articles.json");
        
        // Validació per si hi hagués problemes amb el fitxer o la ruta
        if (articlesImportats == null || articlesImportats.isEmpty()) {
            System.out.println("No s'ha pogut carregar cap article del JSON (fitxer buit o inexistent).");
            return;
        }

        // 2. Recompte en memòria del tipus d'articles (Requisit del Punt 5)
        int camises = 0;
        int pantalons = 0;

        for (Article art : articlesImportats) {
            if (art instanceof Camisa) {
                camises++;
            } else if (art instanceof Pantalo) {
                pantalons++;
            }
        }

        // 3. Mostrar dades del recompte a l'usuari
        System.out.println("Articles localitzats al fitxer:");
        System.out.println("Camises: " + camises);
        System.out.println("Pantalons: " + pantalons);
        System.out.print("Vols afegir aquestes dades a la Base de Dades? (S/N): ");

        // Netegem el buffer abans de llegir cadenes de text per seguretat
        sc.nextLine();
        String confirmacio = sc.nextLine();

        // 4. Flux de bolcat a la Base de Dades si l'usuari accepta
        if (confirmacio.equalsIgnoreCase("S")) {
            int totalAfegits = 0;
            int totalActualitzats = 0;

            for (Article art : articlesImportats) {
                // Utilitzem els mètodes del teu gestorBD per fer l'UPSERT
                if (gestorBD.existeixArticle(art.getId())) {
                    gestorBD.actualitzarArticle(art);
                    totalActualitzats++;
                } else {
                    gestorBD.inserirArticle(art);
                    totalAfegits++;
                }
            }

            // 5. Missatge final amb el resum segons demana l'enunciat
            System.out.println("--- Procés finalitzat amb èxit ---");
            System.out.println("Articles nous inserits a la BD: " + totalAfegits);
            System.out.println("Articles existents actualitzats a la BD: " + totalActualitzats);

        } else {
            System.out.println("Càrrega a la Base de Dades cancel·lada per l'usuari.");
        }
    }

    // Funció per llegir nombres enters
    public int llegirEnter() {
        int nombre = 0;
        try {
            nombre = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Introdueix un nombre");
            sc.nextLine();
        }
        return nombre;
    }

    // Funció per llegir text
    public String llegirString() {
        String text = " ";
        try {
            text = sc.next();
        } catch (InputMismatchException e) {
            System.out.println("Introdueix un text");
            sc.next();
        }
        return text;
    }

    // Funció per sortir del programa
    public boolean sortirMenu() {
        System.out.println("Sortint de l'aplicació...");
        return true;
    }
}