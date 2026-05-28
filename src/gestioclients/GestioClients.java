package gestioclients;

public class GestioClients {
    
    // Variables
    private String nom;
    private String email;
    private String DNI;
    private String telefon;

    // Constructor
    public GestioClients(String nom, String email, String DNI, String telefon) {
        this.nom = nom;
        this.email = email;
        this.DNI = DNI;
        this.telefon = telefon;
    }

    // Getters i Setters
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

    @Override
    public String toString() {
        return "Client [DNI=" + DNI + ", Nom=" + nom + ", Email=" + email + ", Telèfon=" + telefon + "]";
    }
}