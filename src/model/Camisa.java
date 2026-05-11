package model;

public class Camisa extends Article {
    private int tallaColl;
    private int ampladaPit;

    public Camisa(int id, String nom, double preu_base, int iva, int stock, int tallaColl, int ampladaPit){
        super(id, nom, "Camises", preu_base, iva, stock);
        this.tallaColl = tallaColl;
        this.ampladaPit = ampladaPit;
    }

    @Override
    public String toString(){
        return "Camisa: [ID: " + id + ", nom: " + nom + ", coll: " + tallaColl + ", pit: " + ampladaPit + ", stock: " + stock + "]"; 
    }
}
