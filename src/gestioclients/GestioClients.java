package gestioclients;
public class GestioClients {
    // 1. Atributos
    private String nom;
    private String email;
    private String DNI;
    private String telefon;

    // 2. Constructor
    public GestioClients(String nom, String email, String DNI, String telefon) {
        this.nom = nom;
        this.email = email;
        this.DNI = DNI;
        this.telefon = telefon;
    }

    // 4. Getters y Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    // 5. Método toString para poder imprimir el cliente
    @Override
    public String toString() {
        return "Client [DNI=" + DNI + ", Nom=" + nom + ", Email=" + email + ", Telèfon=" + telefon + "]";
    }
}


