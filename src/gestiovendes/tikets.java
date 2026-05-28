package gestiovendes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class tikets {

    private int id;
    private LocalDate dataCompra;
    private String dniClient;
    private double totalBase;
    private double totalIva;
    private double totalFinal;
    private ArrayList<lineaFactura> linies;

    public tikets(int id, LocalDate dateCompra, String deniClient, double totalBase, double totalIva,
            double totalFinal) {
        this.id = id;
        this.dataCompra = LocalDate.now(); 
        this.dniClient = deniClient; 
        this.linies = new ArrayList<>();
        this.totalBase = 0;
        this.totalIva = 0;
        this.totalFinal = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getDniClient() {
        return dniClient;
    }

    public void setDniClient(String dniClient) {
        this.dniClient = dniClient;
    }

    public double getTotalBase() {
        return totalBase;
    }

    public void setTotalBase(double totalBase) {
        this.totalBase = totalBase;
    }

    public double getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(double totalIva) {
        this.totalIva = totalIva;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }

    public ArrayList<lineaFactura> getLinies() {
        return linies;
    }

    public void setLinies(ArrayList<lineaFactura> linies) {
        this.linies = linies;
    }

    public void afegirLinia(lineaFactura linia) {
        this.linies.add(linia);
        recalcularTotals();
    }

    public void recalcularTotals() {
        // Reiniciem les variables per evitar acumulacions de valors
        this.totalBase = 0;
        this.totalIva = 0;
        this.totalFinal = 0;

        for (int i = 0; i < linies.size(); i++) {
            lineaFactura liniaActual = linies.get(i);
            this.totalBase = this.totalBase + liniaActual.getPreuBase(); // Suma del preu de linea del total del tiquet
            double ivaAquestaLinia = liniaActual.getPreuBase() * (liniaActual.getIva() / 100.0);
            this.totalIva = this.totalIva + ivaAquestaLinia;
        }
        this.totalFinal = this.totalBase + this.totalIva;
    }

    @Override
    public String toString() {
        String resum = "TIQUET NÚM: " + this.id + "\n" +
                "DATA: " + this.dataCompra + "\n" +
                "CLIENT: " + this.dniClient + "\n" +
                "--------------------------\n" +
                "TOTAL BASE: " + this.totalBase + "€\n" +
                "TOTAL IVA:  " + this.totalIva + "€\n" +
                "TOTAL FINAL: " + this.totalFinal + "€";

        return resum;
    }
}