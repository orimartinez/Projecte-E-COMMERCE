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

            int filesAfectades = pstmt.executeUpdate();
            return filesAfectades > 0;

        } catch (SQLException e) {
            System.out.println("Error a l'afegir el client a la BD: " + e.getMessage());
            return false;
        }
    }

    // 2. BAIXA DE CLIENT
    public boolean esborrarClient(String dni) {
        String sql = "DELETE FROM clients WHERE DNI = ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);

            int filesAfectades = pstmt.executeUpdate();
            return filesAfectades > 0;

        } catch (SQLException e) {
            System.out.println("Error al esborrar el client: " + e.getMessage());
            return false;
        }
    }

    // 3. MODIFICACIÓ DE CLIENT
    public boolean modificarClient(GestioClients client) {
        String sql = "UPDATE clients SET nom = ?, email = ?, telefon = ? WHERE DNI = ?";

        try (Connection conn = ConnexioBD.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getEmail());
            pstmt.setString(3, client.getTelefon());
            pstmt.setString(4, client.getDNI());

            int filesAfectades = pstmt.executeUpdate();
            return filesAfectades > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar el client: " + e.getMessage());
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
                llistaClients.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar clients: " + e.getMessage());
        }
        return llistaClients;
    }

    // 5. CONSULTA VENDES PER CLIENT (Añadido siguiendo vuestro estilo)
    public void consultarVendesPerClient(String dniConsultar) {
        // SQL adaptado a vuestra base de datos (todo en minúsculas)
        String sql = "SELECT c.dni, c.nom, COUNT(t.id) AS nombre_tiquets, " +
                     "COALESCE(SUM(t.total_final), 0) AS despesa_total " +
                     "FROM clients c " +
                     "LEFT JOIN tiquets t ON c.dni = t.dni_client " +
                     "WHERE c.dni = ? " +
                     "GROUP BY c.dni, c.nom";

        try (Connection conn = ConnexioBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dniConsultar);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Comprobamos si el cliente existe
                if (rs.next()) {
                    String dni = rs.getString("dni");
                    String nom = rs.getString("nom");
                    int numTiquets = rs.getInt("nombre_tiquets");
                    double totalDespesa = rs.getDouble("despesa_total");

                    // Imprimimos el resultado de forma sencilla por pantalla
                    System.out.println("\n==================================================");
                    System.out.println("   CONSULTA DE VENDES PER CLIENT");
                    System.out.println("==================================================");
                    System.out.println("NIF: " + dni);
                    System.out.println("Nom del client: " + nom);
                    System.out.println("--------------------------------------------------");
                    System.out.println("Nombre de tiquets: " + numTiquets);
                    System.out.printf("Quantitat total de despesa efectuada: %.2f €\n", totalDespesa);
                    System.out.println("==================================================\n");
                } else {
                    // Si el cliente no existe en la tabla
                    System.out.println("\n[!] El client amb NIF '" + dniConsultar + "' no existeix a la base de dades.\n");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en executar la consulta de vendes: " + e.getMessage());
        }
    }
}