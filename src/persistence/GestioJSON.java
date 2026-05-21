package persistence;

import model.Article;
import model.Camisa;
import model.Pantalo;

// Imports exclusius de la llibreria json-simple-1.1.1.jar
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class GestioJSON {

    public ArrayList<Article> llegirArticlesJSON(String rutaFitxer) {
        ArrayList<Article> llistaArticles = new ArrayList<>();
        JSONParser parser = new JSONParser();
        
        try (FileReader reader = new FileReader(rutaFitxer)) {
            // Llegim el fitxer de text i el transformem en un array de JSON
            Object obj = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            // Recorrem l'array utilitzant un bucle foreach compatible amb json-simple
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                
                // Convertim de forma segura els valors numèrics de Long a int
                int id = ((Long) jsonObject.get("id")).intValue();
                String nom = (String) jsonObject.get("nom");
                String familia = (String) jsonObject.get("familia");
                double preuBase = (Double) jsonObject.get("preu_base");
                int iva = ((Long) jsonObject.get("iva")).intValue();
                int stock = ((Long) jsonObject.get("stock")).intValue();

                // Lògica d'instanciació segons el tipus d'article
                if (familia.equalsIgnoreCase("camisa")) {
                    int tallaColl = ((Long) jsonObject.get("talla_coll")).intValue();
                    int ampladaPit = ((Long) jsonObject.get("amplada_pit")).intValue();
                    
                    Camisa camisa = new Camisa(id, nom, preuBase, iva, stock, tallaColl, ampladaPit);
                    llistaArticles.add(camisa);
                    
                } else if (familia.equalsIgnoreCase("pantaló") || familia.equalsIgnoreCase("pantalo")) {
                    int llargadaCamal = ((Long) jsonObject.get("llargada_camal")).intValue();
                    int tallaCintura = ((Long) jsonObject.get("talla_cintura")).intValue();
                    
                    Pantalo pantalo = new Pantalo(id, nom, preuBase, iva, stock, llargadaCamal, tallaCintura);
                    llistaArticles.add(pantalo);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error durant la lectura del fitxer JSON: " + e.getMessage());
        }
        
        return llistaArticles;
    }
}