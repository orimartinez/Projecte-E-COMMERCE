package gestioarticles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import model.Article;
import model.Camisa;
import model.Pantalo;
import persistence.ConnexioBD;

public class ArticleDAO {

    public void consultarVendesPerArticle(int idArticleConsultar) {
        String sql = "SELECT a.id, a.nom, COALESCE(SUM(lf.quantitat), 0) AS quantitat_total " +
                "FROM articles a " +
                "LEFT JOIN linies_factura lf ON a.id = lf.id_article " +
                "WHERE a.id = ? " +
                "GROUP BY a.id, a.nom";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idArticleConsultar);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    int quantitatTotal = rs.getInt("quantitat_total");

                    System.out.println("\n==================================================");
                    System.out.println("   CONSULTA DE VENDES PER ARTICLE");
                    System.out.println("==================================================");
                    System.out.println("Codi de l'article: " + id);
                    System.out.println("Nom de l'article: " + nom);
                    System.out.println("--------------------------------------------------");
                    System.out.println("Quantitat total venuda: " + quantitatTotal + " unitats");
                    System.out.println("==================================================\n");
                } else {
                    System.out.println(
                            "\n[!] L'article amb codi '" + idArticleConsultar + "' no existeix a la base de dades.\n");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en executar la consulta d'articles: " + e.getMessage());
        }
    }

    public Article obtenirArticlePerId(int idArticle) {
        String sql = "SELECT * FROM articles WHERE id = ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idArticle);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String familia = rs.getString("familia");
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                double preuBase = rs.getDouble("preu_base");
                int iva = rs.getInt("IVA");
                int stock = rs.getInt("stock");

                // SOLUCIÓ: Comprovem el text real en minúscula o singular de la BD
                if (familia.equalsIgnoreCase("camisa") || familia.equalsIgnoreCase("camises")) {
                    int tallaColl = rs.getInt("talla_coll");
                    int ampladaPit = rs.getInt("amplada_pit");
                    return new Camisa(id, nom, preuBase, iva, stock, tallaColl, ampladaPit);
                } else if (familia.equalsIgnoreCase("pantaló") || familia.equalsIgnoreCase("pantalo")
                        || familia.equalsIgnoreCase("pantalons")) {
                    int llargadaCamal = rs.getInt("llargada_camal");
                    int tallaCintura = rs.getInt("talla_cintura");
                    return new Pantalo(id, nom, preuBase, iva, stock, llargadaCamal, tallaCintura);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error BD en obtenir article per ID: " + e.getMessage());
        }
        return null;
    }

    public boolean restarStock(int idArticle, int quantitatVenuda) {
        String sql = "UPDATE articles SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantitatVenuda);
            pstmt.setInt(2, idArticle);
            pstmt.setInt(3, quantitatVenuda);

            int files = pstmt.executeUpdate();
            return files > 0;

        } catch (SQLException e) {
            System.out.println("Error al restar stock.");
            return false;
        }
    }

    public boolean afegirArticle(Article article) {
        String sql = "INSERT INTO articles (id, nom, familia, preu_base, IVA, stock, talla_coll, amplada_pit, llargada_camal, talla_cintura) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, article.getId());
            pstmt.setString(2, article.getNom());

            pstmt.setString(3, article instanceof Camisa ? "camisa" : "pantaló");

            pstmt.setDouble(4, article.getPreu_base());
            pstmt.setInt(5, article.getIva());
            pstmt.setInt(6, article.getStock());

            if (article instanceof Camisa) {
                Camisa c = (Camisa) article;
                pstmt.setInt(7, c.getTallaColl());
                pstmt.setInt(8, c.getAmpladaPit());
                pstmt.setNull(9, Types.INTEGER);
                pstmt.setNull(10, Types.INTEGER);
            } else if (article instanceof Pantalo) {
                Pantalo p = (Pantalo) article;
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setInt(9, p.getLlargadaCamal());
                pstmt.setInt(10, p.getTallaCintura());
            }

            int files = pstmt.executeUpdate();
            return files > 0;

        } catch (SQLException e) {
            System.out.println("Error al afegir article: " + e.getMessage());
            return false;
        }
    }

    public boolean esborrarArticle(int idArticle) {
        String sql = "DELETE FROM articles WHERE id = ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idArticle);
            int files = pstmt.executeUpdate();
            return files > 0;

        } catch (SQLException e) {
            System.out.println("Error al esborrar.");
            return false;
        }
    }

    
    public void calcularMargeBeneficisTotals() {
        // Sumem el preu final (amb IVA) i el preu base de totes les línies de factura
        // venudes
        String sql = "SELECT COALESCE(SUM(quantitat * preu_base), 0) AS total_base, " +
                "COALESCE(SUM(quantitat * (preu_base * (1 + iva/100.0))), 0) AS total_final " +
                "FROM linies_factura";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                double totalBase = rs.getDouble("total_base");
                double totalFinal = rs.getDouble("total_final");
                double impostos = totalFinal - totalBase;

                System.out.println("\n==================================================");
                System.out.println("   INFORME FINANCIAL DE BENEFICIS TOTALS");
                System.out.println("==================================================");
                System.out.printf("Facturació Total (Sense IVA): %.2f €\n", totalBase);
                System.out.printf("Total IVA Recaudit:          %.2f €\n", impostos);
                System.out.printf("Facturació Bruta (Amb IVA):  %.2f €\n", totalFinal);
                System.out.println("==================================================");
            }
        } catch (SQLException e) {
            System.out.println("Error en calcular els beneficis: " + e.getMessage());
        }
    }

    
    public void propostaRecompraAutomatica() {
        // Busquem els articles que tenen un estoc molt baix (per exemple, menys de 3
        // unitats)
        String sql = "SELECT id, nom, stock FROM articles WHERE stock < 3";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n==================================================");
            System.out.println("   PROPOSTA AUTOMÀTICA DE COMANDA (ESTOC BAIX)");
            System.out.println("==================================================");
            boolean hiHaAlerta = false;

            while (rs.next()) {
                hiHaAlerta = true;
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int stock = rs.getInt("stock");
                int quantitatAComprar = 10 - stock; // Intentem reposar fins a tenir un estoc de 10

                System.out.println("- ALERTA! Article ID: " + id + " | " + nom + " (Estoc actual: " + stock + ")");
                System.out.println("  -> Suggeriment: Demanar al proveïdor " + quantitatAComprar + " unitats.");
            }

            if (!hiHaAlerta) {
                System.out.println("Tots els articles tenen un nivell d'estoc correcte (>= 3 unitats).");
            }
            System.out.println("==================================================");

        } catch (SQLException e) {
            System.out.println("Error en generar la proposta de recompra: " + e.getMessage());
        }
    }
}