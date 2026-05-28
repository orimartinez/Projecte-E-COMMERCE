package gestioclients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistence.ConnexioBD;

public class ClientDAO {

    // 1. ALTA DE CLIENT
    public boolean afegirClient(GestioClients client) {
        String sql = "INSERT INTO clients (DNI, nom, email, telefon) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getDNI());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getTelefon());

            int files = pstmt.executeUpdate();
            return files > 0;

        } catch (SQLException e) {
            System.out.println("Error al afegir: " + e.getMessage());
            return false;
        }
    }

    public boolean esborrarClient(String dni) {
        String sql = "DELETE FROM clients WHERE DNI = ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);

            int filesAfectades = pstmt.executeUpdate();
            return filesAfectades > 0;

        } catch (SQLException e) {
            System.out.println("Error al esborrar.");
            return false;
        }
    }

    public boolean modificarClient(GestioClients client) {
        String sql = "UPDATE clients SET nom = ?, email = ?, telefon = ? WHERE DNI = ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getEmail());
            pstmt.setString(3, client.getTelefon());
            pstmt.setString(4, client.getDNI());

            int files = pstmt.executeUpdate();
            return files > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar.");
            return false;
        }
    }

    // 4. CONSULTA

    public List<GestioClients> obtenirTotsElsClients() {
        List<GestioClients> llistaClients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                GestioClients c = new GestioClients(
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("DNI"),
                        rs.getString("telefon"));
                rs.getString("telefon");
                llistaClients.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar.");
        }
        return llistaClients;
    }

    public void consultarVendesPerClient(String dniConsultar) {
        String sql = "SELECT c.dni, c.nom, COUNT(t.id) AS nombre_tiquets, " +
                "COALESCE(SUM(t.total_final), 0) AS despesa_total " +
                "FROM clients c " +
                "LEFT JOIN tiquets t ON c.dni = t.dni_client " +
                "WHERE c.dni = ? " +
                "GROUP BY c.dni, c.nom";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dniConsultar);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String dni = rs.getString("dni");
                String nom = rs.getString("nom");
                int numTiquets = rs.getInt("nombre_tiquets");
                double totalDespesa = rs.getDouble("despesa_total");

                System.out.println("\n--- VENDES DEL CLIENT ---");
                System.out.println("DNI: " + dni);
                System.out.println("Nom: " + nom);
                System.out.println("Tiquets totals: " + numTiquets);
                System.out.println("Diners gastats: " + totalDespesa + " euros");
                System.out.println("-------------------------");
            } else {
                System.out.println("\nAquest client no existeix.");
            }

        } catch (SQLException e) {
            System.out.println("Error a la base de dades.");
        }
    }
}