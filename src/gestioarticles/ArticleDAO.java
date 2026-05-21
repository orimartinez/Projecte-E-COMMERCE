package gestioarticles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Article;
import model.Camisa;
import model.Pantalo;
import persistence.ConnexioBD;

public class ArticleDAO {

    // =========================================================================
    // MÉTODOS IMPRESCINDIBLES PARA TU TPV (PUNTO 6)
    // =========================================================================

    public Article obtenirArticlePerId(int idArticle) {
        String sql = "SELECT * FROM articles WHERE id = ?";
        
        try (Connection conn = ConnexioBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idArticle);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String familia = rs.getString("familia");
                    
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    double preuBase = rs.getDouble("preu_base");
                    int iva = rs.getInt("IVA");
                    int stock = rs.getInt("stock");

                    if (familia.equalsIgnoreCase("Camises")) {
                        int tallaColl = rs.getInt("talla_coll");
                        int ampladaPit = rs.getInt("amplada_pit");
                        return new Camisa(id, nom, preuBase, iva, stock, tallaColl, ampladaPit);
                    } else if (familia.equalsIgnoreCase("Pantalons")) {
                        int llargadaCamal = rs.getInt("llargada_camal");
                        int tallaCintura = rs.getInt("talla_cintura");
                        return new Pantalo(id, nom, preuBase, iva, stock, llargadaCamal, tallaCintura);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar l'article per ID: " + e.getMessage());
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

            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar l'stock: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // MÉTODOS DEL CRUD DE ARTÍCULOS (PARA TU COMPAÑERO)
    // =========================================================================

    public boolean afegirArticle(Article article) {
        String sql = "INSERT INTO articles (id, nom, familia, preu_base, IVA, stock, talla_coll, amplada_pit, llargada_camal, talla_cintura) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnexioBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, article.getId());
            pstmt.setString(2, article.getNom());
            pstmt.setString(3, article.familia());
            pstmt.setDouble(4, article.getPreu_base());
            pstmt.setInt(5, article.getIva());
            pstmt.setInt(6, article.getStock());

            if (article instanceof Camisa) {
                Camisa c = (Camisa) article;
                pstmt.setInt(7, c.getTallaColl());
                pstmt.setInt(8, c.getAmpladaPit());
                pstmt.setNull(9, java.sql.Types.INTEGER);
                pstmt.setNull(10, java.sql.Types.INTEGER);
            } else if (article instanceof Pantalo) {
                Pantalo p = (Pantalo) article;
                pstmt.setNull(7, java.sql.Types.INTEGER);
                pstmt.setNull(8, java.sql.Types.INTEGER);
                pstmt.setInt(9, p.getLlargadaCamal());
                pstmt.setInt(10, p.getTallaCintura());
            }

            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error a l'afegir l'article: " + e.getMessage());
            return false;
        }
    }

    public boolean esborrarArticle(int idArticle) {
        String sql = "DELETE FROM articles WHERE id = ?";
        
        try (Connection conn = ConnexioBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idArticle);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error a l'esborrar l'article: " + e.getMessage());
            return false;
        }
    }
}