package persistence;

import model.Article;
import model.Camisa;
import model.Pantalo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioBD {

    // Mètode per comprovar si un article ja existeix a la Base de Dades
    public boolean existeixArticle(int id) {
        String sql = "SELECT COUNT(*) FROM articles WHERE id = ?";
        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en comprovar existència de l'article: " + e.getMessage());
        }
        return false;
    }

    // Mètode per inserir un article nou
    public int inserirArticle(Article art) {
        String sql = "INSERT INTO articles (id, nom, familia, preu_base, iva, stock, talla_coll, amplada_pit, llargada_camal, talla_cintura) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            configurarPreparedStatement(ps, art);
            return ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en inserir l'article: " + e.getMessage());
            return 0;
        }
    }

    // Mètode per actualitzar un article existent
    public int actualitzarArticle(Article art) {
        String sql = "UPDATE articles SET nom = ?, familia = ?, preu_base = ?, iva = ?, stock = ?, talla_coll = ?, amplada_pit = ?, llargada_camal = ?, talla_cintura = ? WHERE id = ?";
        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // Assignem els paràmetres segons l'ordre exacte de la consulta UPDATE
            ps.setString(1, art.getNom());
            
            ps.setString(2, art instanceof Camisa ? "camisa" : "pantaló");
            
            ps.setDouble(3, art.getPreu_base());
            ps.setInt(4, art.getIva());
            ps.setInt(5, art.getStock());

            if (art instanceof Camisa) {
                Camisa c = (Camisa) art;
                ps.setInt(6, c.getTallaColl());
                ps.setInt(7, c.getAmpladaPit());
                ps.setNull(8, java.sql.Types.INTEGER);
                ps.setNull(9, java.sql.Types.INTEGER);
            } else {
                Pantalo p = (Pantalo) art;
                ps.setNull(6, java.sql.Types.INTEGER);
                ps.setNull(7, java.sql.Types.INTEGER);
                ps.setInt(8, p.getLlargadaCamal());
                ps.setInt(9, p.getTallaCintura());
            }
            
            // L'ID és el darrer paràmetre que correspon al WHERE id = ?
            ps.setInt(10, art.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en actualitzar l'article: " + e.getMessage());
            return 0;
        }
    }

    // Mètode auxiliar privat per evitar duplicar codi a l'INSERT
    private void configurarPreparedStatement(PreparedStatement ps, Article art) throws SQLException {
        ps.setInt(1, art.getId());
        ps.setString(2, art.getNom());
        
        // TRADUCCIÓ: Forcem el text en minúscula i singular exigit per la regla CHECK 'articles_chk_3'
        ps.setString(3, art instanceof Camisa ? "camisa" : "pantaló");
        
        ps.setDouble(4, art.getPreu_base());
        ps.setInt(5, art.getIva());
        ps.setInt(6, art.getStock());

        if (art instanceof Camisa) {
            Camisa c = (Camisa) art;
            ps.setInt(7, c.getTallaColl());
            ps.setInt(8, c.getAmpladaPit()); 
            ps.setNull(9, java.sql.Types.INTEGER);
            ps.setNull(10, java.sql.Types.INTEGER);
        } else {
            Pantalo p = (Pantalo) art;
            ps.setNull(7, java.sql.Types.INTEGER);
            ps.setNull(8, java.sql.Types.INTEGER);
            ps.setInt(9, p.getLlargadaCamal()); 
            ps.setInt(10, p.getTallaCintura()); 
        }
    }
}