import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Main p = new Main();
        p.principal();
    }

    Scanner sc = new Scanner(System.in);

    public void principal() {

        boolean sortirPrograma = false;

        while (sortirPrograma == false) {
            System.out.println("===== Selecciona una opció =====");
            System.out.println("1. Importació d'articles");
            System.out.println("2. Gestió d'articles");
            System.out.println("3. Gestió de clients");
            System.out.println("4. TPV");
            System.out.println("5. Consultes vendes per client");
            System.out.println("6. Consultes vendes per article");
            System.out.println("7. Càlcul de beneficis totals");
            System.out.println("8. Recompra automàtica d'articles");
            System.out.println("9. Sortir del menú");

            int opcioMenuPrincipal = llegirEnter();

            switch (opcioMenuPrincipal) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    sortirPrograma = sortirMenu();
                    break;
                default:
                    break;
            }
        }
    }

    //Funció per llegir nombres enters
    public int llegirEnter() {
        int nombre = 0;

        try {
            nombre = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Introdueix un nombre");
            sc.nextLine();
        }
        return nombre;
    }
    
    //Funció per sortir del programa
    public boolean sortirMenu() {
        return true;
    }
}
