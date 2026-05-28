package model;

public class Pantalo extends Article {
<<<<<<< HEAD

    private int llargadaCamal;
    private int tallaCintura;

    public Pantalo(int id, String nom, double preu_base, int iva, int stock, int llargadaCamal, int tallaCintura) {
=======
    
    private int llargadaCamal;
    private int tallaCintura;

    public Pantalo(int id, String nom, double preu_base, int iva, int stock, int llargadaCamal, int tallaCintura){
>>>>>>> developmentAd
        super(id, nom, "Pantalons", preu_base, iva, stock);
        this.llargadaCamal = llargadaCamal;
        this.tallaCintura = tallaCintura;
    }
<<<<<<< HEAD

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
}
=======
    
    // Novos Getters necessários para a Base de Dados
    public int getLlargadaCamal() {
        return llargadaCamal;
    }

    public int getTallaCintura() {
        return tallaCintura;
    }
    
    @Override
    public String toString(){
        return "Pantaló [ID:" + id + ", nom: " + nom + ", llargadaCamal: " + llargadaCamal + ", tallaCintura: " + tallaCintura + ", stock: " + stock + "]";
    }
}
>>>>>>> developmentAd
