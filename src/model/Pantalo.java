package model;

public class Pantalo extends Article {

    private int llargadaCamal;
    private int tallaCintura;

    public Pantalo(int id, String nom, double preu_base, int iva, int stock, int llargadaCamal, int tallaCintura) {
        super(id, nom, "Pantalons", preu_base, iva, stock);
        this.llargadaCamal = llargadaCamal;
        this.tallaCintura = tallaCintura;
    }

    @Override
    public String toString() {
        return "Pantaló [ID:" + id + ", nom: " + nom + ", llargadaCamal: " + llargadaCamal + ", tallaCintura: "
                + tallaCintura + ", stock: " + stock + "]";
    }

    public int getLlargadaCamal() {
        return this.llargadaCamal;
    }

    public int getTallaCintura() {
        return this.tallaCintura;
    }
    
    public double getPreuCost() {
        // Fórmula: preu_base * 0,30 + llargada_camal * 0,2
        return (this.getPreu_base() * 0.30) + (this.getLlargadaCamal() * 0.2);
    }
    
}
