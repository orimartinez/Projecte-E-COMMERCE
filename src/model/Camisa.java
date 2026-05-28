package model;

public class Camisa extends Article {
    private int tallaColl;
    private int ampladaPit;

<<<<<<< HEAD
    public Camisa(int id, String nom, double preu_base, int iva, int stock, int tallaColl, int ampladaPit) {
=======
    public Camisa(int id, String nom, double preu_base, int iva, int stock, int tallaColl, int ampladaPit){
>>>>>>> developmentAd
        super(id, nom, "Camises", preu_base, iva, stock);
        this.tallaColl = tallaColl;
        this.ampladaPit = ampladaPit;
    }

<<<<<<< HEAD
    @Override
    public String toString() {
        return "Camisa: [ID: " + id + ", nom: " + nom + ", coll: " + tallaColl + ", pit: " + ampladaPit + ", stock: "
                + stock + "]";
    }

    public int getTallaColl() {
        return this.tallaColl;
    }

    public int getAmpladaPit() {
        return this.ampladaPit;
    }
}
=======
    public int getTallaColl() {
        return tallaColl;
    }

    public int getAmpladaPit() {
        return ampladaPit;
    }

    @Override
    public String toString(){
        return "Camisa: [ID: " + getId() + ", nom: " + getNom() + ", coll: " + tallaColl + ", pit: " + ampladaPit + ", stock: " + getStock() + "]"; 
    }
}
>>>>>>> developmentAd
