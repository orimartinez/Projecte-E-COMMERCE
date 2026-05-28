package gestiovendes;

import java.time.LocalDate;
import java.util.Scanner;
import gestioclients.ClientDAO;
import gestioarticles.ArticleDAO; 
import model.Article;
import model.Camisa;
import model.Pantalo;

public class GestioVendes {

    private ClientDAO clientDAO;
    private ArticleDAO articleDAO; 

    public GestioVendes() {
        clientDAO = new ClientDAO();
        articleDAO = new ArticleDAO(); 
    }

    public void iniciarVenda(Scanner scanner) {
        System.out.println("\n--- NOU TIQUET ---");

        System.out.print("DNI del client (posa 000 si no el saps): ");
        String dniClient = scanner.nextLine();

        tikets tiquetActual = new tikets(1, LocalDate.now(), dniClient, 0.0, 0.0, 0.0);

        int idArticle = -1;
        
        while (idArticle != 0) {
            System.out.print("\nID de l'article (posa 0 per acabar): ");
            idArticle = Integer.parseInt(scanner.nextLine());

            if (idArticle != 0) {
                System.out.print("Quantitat: ");
                int quantitat = Integer.parseInt(scanner.nextLine());

                Article article = articleDAO.obtenirArticlePerId(idArticle);
                
                // Comprovacions
                if (article == null) {
                    System.out.println("Error: Aquest article no existeix.");
                } else if (article.getStock() < quantitat) {
                    System.out.println("No pots! Només queden " + article.getStock() + " en stock.");
                } else {
                    System.out.println("Afegint " + article.getNom() + "...");
                    
                    double preu = article.getPreu_base() * quantitat;

                    lineaFactura novaLinia = new lineaFactura(tiquetActual.getId(), article.getId(), quantitat, preu, article.getIva());
                    tiquetActual.afegirLinia(novaLinia);
                }
            }
        }

        System.out.println("\n--- RESUM DE LA VENDA ---");
        System.out.println(tiquetActual.toString());

        System.out.println("Guardant i restant stock...");
        
        // Restem l'stock de cada producte comprat
        for (lineaFactura linia : tiquetActual.getLinies()) {
            articleDAO.restarStock(linia.getId_article(), linia.getQuantitat());
        }

        System.out.println("Venda acabada!");
    }
}