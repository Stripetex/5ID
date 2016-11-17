package robotserver;


import static java.lang.Math.random;
import java.util.Random;

/**
 *
 * @author Andrea
 */
public class Risp {

    public void Risp() {
    }
    ;
    
    private static final String[] risposte = {
        "Ciao sono il barman Gekolo, tu chi sei?",};
    private static final String[] rispNegative = {
        "Ancora? Basta",
        "Non ho capito",
        "Di nuovo?",
        "me lo hai chiesto anche prima sei ripetitivo"
    };

    private static final String[] monosillabi = {
        "Bye.",
        "No",
        "Ciao",
        "ok",
        "grazie"
    };
    private static final String[] ubriaco = {//frasi senza senso
        "Spiego jesolo tu si è marghera no",
        "Se trovi quelle che ti prendi# non guardi tanto che sei# in adolescenza",
        "Quanto è cervo la lia un selvatico?",
        "Io ricordo l'anno corso 3",
        "Fino qualcuno a il mio giuostik?",
        "SCANFERLA IPHONE 7 E' COLPA SUA",
        "Io mi alzo' un po per puliami il culo",
        "Perche' cszzoglp",
        "Cosa e' stato roba"

    };

    private static final String[] menu = {
        /* {"Spritz","2.50"},
        {"Coca-Cola","3.00"},
        {"caffe","1.00"},
        {"vodka","5.00"},
        {"shot","3.00"}*/
        "spritz", "Coca-Cola", "caffe", "vodka", "shot"

    };
    private static final Double[] CostoM = {
        2.50, 
        3.00,
        1.00,
        5.00, 
        3.00
    };

    private static final String mn = "Spritz 2.50#Coca-Cola 3.00#caffe 1.00#vodka 5.00#shot 3.00#Cosa vuoi?";

    public static String getRisp(int pos) {
        return risposte[pos];
    }

    public static String getParola(int pos) {
        return monosillabi[pos];
    }

    public static String getRispNeg(int pos) {
        return rispNegative[pos];
    }

    public static String getUbriaco(int pos) {
        return ubriaco[pos];
    }

    public static double getMenu(String Drink) {
        for (int i = 0; i < menu.length; i++) {
            if(menu[i].equals(Drink)){
                return CostoM[i];
                
            }
            
        }
        return 0.0;
    }


    public static String getMn() {
        return mn;
    }

}
