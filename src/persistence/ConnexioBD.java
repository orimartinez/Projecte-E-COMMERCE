package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexioBD {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/?user=alumne_ecommerce"; 
    private static final String USER = "alumne_ecommerce"; 
    private static final String PASSWORD = "Password123!";

    public static Connection conectar() {
        Connection conexio = null;
        try {
            conexio = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexió establerta amb èxit!");
        } catch (SQLException e) {
            System.out.println("Error en connectar a la BD: " + e.getMessage());
        }
        return conexio;
    }
}