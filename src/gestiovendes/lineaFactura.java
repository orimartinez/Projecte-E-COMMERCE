package gestiovendes;

public class lineaFactura {

    private int id_tiquet;
    private int id_article;
    private int quantitat;
    private double preu_base;
    private double iva;
    private double preu_final;

    public lineaFactura(int id_tiquet, int id_article, int quantitat, double preu_base, double iva) {
        this.id_tiquet = id_tiquet;
        this.id_article = id_article;
        this.quantitat = quantitat;
        this.preu_base = preu_base;
        this.iva = iva;
        this.preu_final = preu_base + (preu_base * (iva / 100.0));
    }

    public double getPreuBase() {
        return preu_base;
    }

    public double getIva() {
        return iva;
    }

    public int getId_tiquet() {
        return id_tiquet;
    }

    public void setId_tiquet(int id_tiquet) {
        this.id_tiquet = id_tiquet;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public double getPreuFinal() {
        return preu_final;
    }

    public void setPreu_base(double preu_base) {
        this.preu_base = preu_base;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public void setPreu_final(double preu_final) {
        this.preu_final = preu_final;
    }
}