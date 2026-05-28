import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Article;
import model.Camisa;
import model.Pantalo;
import persistence.GestioBD;
import persistence.GestioJSON;

import gestioarticles.ArticleDAO;
import gestioclients.ClientDAO;
import gestioclients.GestioClients;
import gestiovendes.GestioVendes;

public class Main {
    public static void main(String[] args) {
        Main p = new Main();
        p.principal();
    }

    // Atributs globals de la classe Main
    Scanner sc = new Scanner(System.in);
    GestioJSON gestorJSON = new GestioJSON();
    GestioBD gestorBD = new GestioBD();

    // Instàncies dels controladors reals
    ArticleDAO articleDAO = new ArticleDAO();
    ClientDAO clientDAO = new ClientDAO();
    GestioVendes gestioVendes = new GestioVendes();

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
            System.out.print("Seleciona una opció: ");

            int opcioMenuPrincipal = llegirEnter();

            switch (opcioMenuPrincipal) {
                case 1:
                    executarImportacioArticles();
                    break;
                case 2:
                    gestioArticles();
                    break;
                case 3:
                    gestioClients();
                    break;
                case 4:
                    executarTPV();
                    break;
                case 5:
                    consultarVendesClient();
                    break;
                case 6:
                    consultarVendesArticle();
                    break;
                case 7:
                    calcularBeneficisTotals();
                    break;
                case 8:
                    executarRecompraAutomatica();
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

    // ================= PUNT 1: IMPORTACIÓ D'ARTICLES =================
    public void executarImportacioArticles() {
        System.out.println("--- Iniciant importació d'articles des del JSON ---");

        ArrayList<Article> articlesImportats = gestorJSON.llegirArticlesJSON("data/PE11_articles.json");

        if (articlesImportats == null || articlesImportats.isEmpty()) {
            System.out.println("No s'ha pogut carregar cap article del JSON (fitxer buit o inexistent).");
            return;
        }

        int camises = 0;
        int pantalons = 0;

        for (Article art : articlesImportats) {
            if (art instanceof Camisa) {
                camises++;
            } else if (art instanceof Pantalo) {
                pantalons++;
            }
        }

        System.out.println("Articles localitzats al fitxer:");
        System.out.println("Camises: " + camises);
        System.out.println("Pantalons: " + pantalons);
        System.out.print("Vols afegir aquestes dades a la Base de Dades? (S/N): ");

        String confirmacio = llegirString();

        if (confirmacio.equalsIgnoreCase("S")) {
            int totalAfegits = 0;
            int totalActualitzats = 0;

            for (int i = 0; i < articlesImportats.size(); i++) {
                Article art = articlesImportats.get(i);

                if (gestorBD.existeixArticle(art.getId())) {
                    gestorBD.actualitzarArticle(art);
                    totalActualitzats++;
                } else {
                    gestorBD.inserirArticle(art);
                    totalAfegits++;
                }
            }

            System.out.println("--- Procés finalitzat amb èxit ---");
            System.out.println("Articles nous inserits a la BD: " + totalAfegits);
            System.out.println("Articles existents actualitzats a la BD: " + totalActualitzats);

        } else {
            System.out.println("Càrrega a la Base de Dades cancel·lada per l'usuari.");
        }
    }

    // ================= PUNT 2: GESTIÓ D'ARTICLES (SUBMENÚ) =================
    public void gestioArticles() {
        boolean tornarEnrere = false;
        while (!tornarEnrere) {
            System.out.println("\n=== SUBMENÚ GESTIÓ D'ARTICLES ===");
            System.out.println("1. Alta d'articles");
            System.out.println("2. Baixa d'articles");
            System.out.println("3. Modificació d'articles (Stock)");
            System.out.println("4. Consultar articles (Llistar)");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Seleciona una opció: ");

            int opcioMenuGA = llegirEnter();

            switch (opcioMenuGA) {
                case 1:
                    menuAltaArticle();
                    break;
                case 2:
                    menuBaixaArticle();
                    break;
                case 3:
                    menuModificacioArticle();
                    break;
                case 4:
                    menuConsultaArticles();
                    break;
                case 0:
                    tornarEnrere = true;
                    break;
                default:
                    System.out.println("Opció no vàlida.");
                    break;
            }
        }
    }

    public void menuAltaArticle() {
        System.out.println("Quin tipus és? Pantaló / Camisa");
        String tipusArticle = llegirString();

        if (tipusArticle.equalsIgnoreCase("Camisa")) {
            System.out.println("Introdueix l'id");
            int idNouArticle = llegirEnter();
            System.out.println("Introdueix el nom");
            String nomNouArticle = llegirString();
            System.out.println("Introdueix la talla del coll");
            int tallaCollNouArticle = llegirEnter();
            System.out.println("Introdueix l'amplada del pit");
            int ampladaPitNouArticle = llegirEnter();
            System.out.println("Introdueix el preu_base");
            double preuNouArticle = llegirDouble();
            System.out.println("Introdueix l'IVA");
            int ivaNouArticle = llegirEnter();
            System.out.println("Introdueix l'estoc");
            int stockNouArticle = llegirEnter();

            Camisa novaCamisa = new Camisa(idNouArticle, nomNouArticle, preuNouArticle, ivaNouArticle, stockNouArticle,
                    tallaCollNouArticle, ampladaPitNouArticle);

            boolean OK = articleDAO.afegirArticle(novaCamisa);
            if (OK) {
                System.out.println("Camisa afegida correctament a la Base de Dades!");
            } else {
                System.out.println("Error: No s'ha pogut afegir la camisa.");
            }

        } else if (tipusArticle.equalsIgnoreCase("Pantaló") || tipusArticle.equalsIgnoreCase("Pantalo")) {
            System.out.println("Introdueix l'id");
            int idNouArticle = llegirEnter();
            System.out.println("Introdueix el nom");
            String nomNouArticle = llegirString();
            System.out.println("Introdueix la talla de la cintura");
            int tallaCinturaNouArticle = llegirEnter();
            System.out.println("Introdueix la llargada de la cama");
            int llargadaCamaNouArticle = llegirEnter();
            System.out.println("Introdueix el preu_base");
            double preuNouArticle = llegirDouble();
            System.out.println("Introdueix l'IVA");
            int ivaNouArticle = llegirEnter();
            System.out.println("Introdueix l'estoc");
            int stockNouArticle = llegirEnter();

            Pantalo nouPantalo = new Pantalo(idNouArticle, nomNouArticle, preuNouArticle, ivaNouArticle,
                    stockNouArticle, llargadaCamaNouArticle, tallaCinturaNouArticle);

            boolean OK = articleDAO.afegirArticle(nouPantalo);
            if (OK) {
                System.out.println("Pantaló afegit correctament a la Base de Dades!");
            } else {
                System.out.println("Error: No s'ha pogut afegir le pantaló.");
            }
        } else {
            System.out.println("Error en afegir un article: Tipus no vàlid.");
        }
    }

    public void menuBaixaArticle() {
        System.out.println("--- Baixa d'Article ---");
        System.out.print("Introdueix l'ID de l'article que vols esborrar: ");
        int id = llegirEnter();

        boolean OK = articleDAO.esborrarArticle(id);
        if (OK) {
            System.out.println("Article esborrat correctament de la Base de Dades.");
        } else {
            System.out.println("No s'ha trobat cap article amb aquest ID o no s'ha pogut esborrar.");
        }
    }

    public void menuModificacioArticle() {
        System.out.println("--- Modificació d'Article ---");
        System.out.print("Introdueix l'ID de l'article a modificar: ");
        int id = llegirEnter();

        if (gestorBD.existeixArticle(id)) {
            System.out.print("Introdueix el nou estoc: ");
            int nouEstoc = llegirEnter();

            Article art = articleDAO.obtenirArticlePerId(id);
            if (art != null) {
                art.setStock(nouEstoc);
                gestorBD.actualitzarArticle(art);
                System.out.println("Estoc de l'article actualitzat correctament.");
            }
        } else {
            System.out.println("No s'ha trobat l'article amb aquest ID.");
        }
    }

    public void menuConsultaArticles() {
        System.out.println("--- Llistat de tots els Articles ---");
        System.out.println("Aquesta opció es pot consultar directament a la Base de Dades o importat des del JSON.");
    }

    // ================= PUNT 3: GESTIÓ DE CLIENTS =================
    public void gestioClients() {
        boolean tornarEnrere = false;
        while (!tornarEnrere) {
            System.out.println("\n=== SUBMENÚ GESTIÓ DE CLIENTS ===");
            System.out.println("1. Alta de client");
            System.out.println("2. Baixa de client");
            System.out.println("3. Modificació de client");
            System.out.println("4. Llistar tots els clients");
            System.out.println("0. Tornar al menú principal");
            System.out.print("Seleciona una opció: ");

            int opcioClients = llegirEnter();

            switch (opcioClients) {
                case 1:
                    System.out.print("Introdueix el DNI: ");
                    String dni = llegirString();
                    System.out.print("Introdueix el nom: ");
                    String nom = llegirString();
                    System.out.print("Introdueix l'email: ");
                    String email = llegirString();
                    System.out.print("Introdueix el telèfon: ");
                    String telefon = llegirString();

                    GestioClients nouClient = new GestioClients(nom, email, dni, telefon);
                    boolean okAlta = clientDAO.afegirClient(nouClient);
                    if (okAlta) {
                        System.out.println("Client registrat amb èxit.");
                    } else {
                        System.out.println("Error al registrar el client.");
                    }
                    break;
                case 2:
                    System.out.print("Introdueix el DNI del client a esborrar: ");
                    String dniEsborrar = llegirString();
                    boolean okBaixa = clientDAO.esborrarClient(dniEsborrar);
                    if (okBaixa) {
                        System.out.println("Client eliminat de la Base de Dades.");
                    } else {
                        System.out.println("No s'ha pogut eliminar el client.");
                    }
                    break;
                case 3:
                    System.out.print("Introdueix el DNI del client a modificar: ");
                    String dniModificar = llegirString();
                    System.out.print("Introdueix el nou nom: ");
                    String nouNom = llegirString();
                    System.out.print("Introdueix el nou email: ");
                    String nouEmail = llegirString();
                    System.out.print("Introdueix el nou telèfon: ");
                    String nouTelefon = llegirString();

                    GestioClients clientModificat = new GestioClients(nouNom, nouEmail, dniModificar, nouTelefon);
                    boolean okModificar = clientDAO.modificarClient(clientModificat);
                    if (okModificar) {
                        System.out.println("Client modificat correctament.");
                    } else {
                        System.out.println("No s'ha pogut modificar el client.");
                    }
                    break;
                case 4:
                    System.out.println("--- Llistat de Clients Registrats ---");
                    // Es crida correctament al mètode obtenirTotsElsClients() i es recorre la
                    // llista
                    List<GestioClients> llista = clientDAO.obtenirTotsElsClients();
                    if (llista.isEmpty()) {
                        System.out.println("No hi ha cap client registrat.");
                    } else {
                        for (GestioClients c : llista) {
                            System.out.println(c.toString());
                        }
                    }
                    break;
                case 0:
                    tornarEnrere = true;
                    break;
                default:
                    System.out.println("Opció no vàlida.");
                    break;
            }
        }
    }

    // ================= PUNT 4: TERMINAL PUNT DE VENDA (TPV) =================
    public void executarTPV() {
        gestioVendes.iniciarVenda(sc);
    }

    // ================= PUNT 5: VENDES PER CLIENT =================
    public void consultarVendesClient() {
        System.out.print("Introdueix el DNI del client a consultar: ");
        String dni = llegirString();
        clientDAO.consultarVendesPerClient(dni);
    }

    // ================= PUNT 6: VENDES PER ARTICLE =================
    public void consultarVendesArticle() {
        System.out.print("Introdueix l'ID de l'article a consultar: ");
        int id = llegirEnter();
        articleDAO.consultarVendesPerArticle(id);
    }

    // ================= PUNT 7: CÀLCUL DE BENEFICIS TOTALS =================
    public void calcularBeneficisTotals() {
        // Ara crida al mètode real que calcula les dades de la BD
        articleDAO.calcularMargeBeneficisTotals();
    }

    // ================= PUNT 8: RECOMPRA AUTOMÀTICA D'ARTICLES =================
    public void executarRecompraAutomatica() {
        // Ara crida al mètode real que avisa de l'estoc baix
        articleDAO.propostaRecompraAutomatica();
    }
    // ========== MÈTODES DE LECTURA DE TECLAT ==========

    public int llegirEnter() {
        int nombre = 0;
        try {
            nombre = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Introdueix un nombre enter vàlid.");
            sc.nextLine();
        }
        return nombre;
    }

    public double llegirDouble() {
        double nombre = 0;
        try {
            nombre = sc.nextDouble();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Introdueix un nombre decimal vàlid.");
            sc.nextLine();
        }
        return nombre;
    }

    public String llegirString() {
        String text = "";
        try {
            text = sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Introdueix un text vàlid.");
            sc.nextLine();
        }
        return text;
    }

    public boolean sortirMenu() {
        System.out.println("Sortint de l'aplicació...");
        return true;
    }
}