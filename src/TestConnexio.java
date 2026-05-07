import java.sql.Connection;
import persistence.ConnexioBD;

//Aquest document serveix per poder probar si funciona la connexió a la BD.
public class TestConnexio {
    public static void main(String[] args) {
        System.out.println("Provant la connexió...");
        
        Connection con = ConnexioBD.conectar();
        
        if (con != null) {
            System.out.println("Estàs connectat a la base de dades!!");
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No s'ha pogut establir la connexió.");
            System.out.println("Revisa: URL, Usuari, Contrasenya o si el MySQL està encès.");
        }
    }
}