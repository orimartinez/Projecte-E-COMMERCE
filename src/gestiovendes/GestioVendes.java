package gestiovendes;

import java.time.LocalDate;
import java.util.Scanner;
import gestioclients.ClientDAO;
import gestioarticles.ArticleDAO; // ¡Importamos el DAO real!
import model.Article;
import model.Camisa;
import model.Pantalo;

public class GestioVendes {

    private ClientDAO clientDAO;
    private ArticleDAO articleDAO; // Ya lo podemos descomentar

    public GestioVendes() {
        this.clientDAO = new ClientDAO();
        this.articleDAO = new ArticleDAO(); // Lo inicializamos
    }

    public void iniciarVenda(Scanner scanner) {
        System.out.println("\n==================================");
        System.out.println("        NOU TIQUET (TPV)          ");
        System.out.println("==================================");

        // 1. Demanar el client
        System.out.print("Introdueix el DNI del client ('000' per client genèric): ");
        String dniClient = scanner.nextLine();

        // OJO: En un futuro el ID del tiquet debería venir de la BD. Ponemos 1 temporalmente.
        tikets tiquetActual = new tikets(1, LocalDate.now(), dniClient, 0.0, 0.0, 0.0);

        // 2. Bucle de compra d'articles
        boolean venent = true;
        
        while (venent) {
            System.out.print("\nIntrodueix l'ID de l'article (0 per finalitzar la venda): ");
            int idArticle = Integer.parseInt(scanner.nextLine());

            // Si posa 0, sortim del bucle per tancar el tiquet
            if (idArticle == 0) {
                venent = false;
                break;
            }

            System.out.print("Quantitat a comprar: ");
            int quantitat = Integer.parseInt(scanner.nextLine());

            // 3. LÒGICA DE COMPROVACIÓ D'STOCK CON BASE DE DATOS REAL
            // Llama al archivo de tu compañero para buscar el artículo en MySQL
            Article article = articleDAO.obtenirArticlePerId(idArticle);
            
            if (article == null) {
                System.out.println("❌ Error: Aquest article no existeix a la base de dades.");
            } else if (article.getStock() < quantitat) {
                System.out.println("❌ Avís: No es pot vendre! Stock insuficient. Stock actual: " + article.getStock());
            } else {
                System.out.println("✅ Article validat: " + article.getNom() + ". S'afegirà al tiquet.");
                
                // Calculamos el precio base total de esta línea
                double preuTotalLiniaSenseIva = article.getPreu_base() * quantitat;

                // Creamos la línea de factura
                lineaFactura novaLinia = new lineaFactura(
                    tiquetActual.getId(), 
                    article.getId(), 
                    quantitat, 
                    preuTotalLiniaSenseIva, 
                    article.getIva()
                );

                // Añadimos la línea al ticket
                tiquetActual.afegirLinia(novaLinia);
            }
        }

        // 4. Finalitzar la venda i restar stock
        System.out.println("\n----------------------------------");
        System.out.println("Generant tiquet...");
        System.out.println("Processant el pagament...");
        
        // Imprimimos el ticket
        System.out.println("\n" + tiquetActual.toString());

        // Recorremos las líneas de la compra para restar el stock en la base de datos
        System.out.println("\nActualitzant els stocks a la base de dades...");
        for (lineaFactura linia : tiquetActual.getLinies()) {
            boolean actualitzat = articleDAO.restarStock(linia.getId_article(), linia.getQuantitat());
            if (!actualitzat) {
                System.out.println("⚠️ Error al restar l'stock de l'article ID: " + linia.getId_article());
            }
        }

        System.out.println("✅ Venda finalitzada amb èxit!");
    }
}