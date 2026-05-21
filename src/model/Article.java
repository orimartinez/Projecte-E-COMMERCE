package model;

public abstract class Article {
    // Creem les variables
    protected int id;
    protected String nom;
    protected String familia;
    protected double preu_base;
    protected int iva;
    protected int stock;

    // Constructor
    public Article(int id, String nom, String familia, double preu_base, int iva, int stock) {
        this.id = id;
        this.nom = nom;
        this.familia = familia;
        this.preu_base = preu_base;
        this.setIva(iva); // Utilitzem els setters per validar dades
        this.setStock(stock);
    }

    // Getters i setters
    public void setIva(int iva) {
        if (iva >= 4 && iva <= 21) {
            this.iva = iva;
        } else {
            this.iva = 21; // En cas de que no posi un valor "correcte" es posarà el valor d'IVA per defecte (21%)
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            this.stock = 0; // EL mateix amb el stock, si possa un valor incorrecte es posa 0 per defecte
        }
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String familia() {
        return familia;
    }

    public double getPreu_base() {
        return preu_base;
    }

    public int getIva() {
        return iva;
    }

    public int getStock() {
        return stock;
    }

    public abstract String toString();
}