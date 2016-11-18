package server;

/**
 * @author Nico
 */
public class Protocol {

    private static final int INIZIO = 1;
    private static final int SMISTAMENTO = 2;
    private static final int ORDINE = 3;
    private static final int INDIRIZZO = 4;
    private static final int CONFERMAFINALE = 5;
    private static final int FINE = 6;

    private String textUser = "";

    private String[] salutoIniziale = {"ciao", "buonasera", "buona", "sera", "giorno", "buongiorno", "salve"};
    private String[] smistamentoOrdine = {"ordinare", "ordine", "fare"};
    private String[] smistamentoMenu = {"menu", "mostrami", "mostra", "vedere"};
    private String[][] listaPizze = {
        {"Margherita", "4.5"},
        {"Viennese", "5.5"},
        {"Prosciutto", "6.5"},
        {"Patatosa", "6.0"},
        {"Americana", "7.0"}};
    private int[] numPizze = {0, 0, 0, 0, 0};
    private String indirizzo;
    private double totale = 0.00;
    private int state;

    public Protocol() {
        state = INIZIO;
    }

    public String checkText(String s) {
        String forUser = null;
        textUser = s;

        switch (state) {
            case INIZIO:
                if (checkIsPresent(salutoIniziale)) {
                    forUser = "Ciao, sai già cosa ordinare o devo mostrarti il menu?";
                    state = SMISTAMENTO;
                } else {
                    forUser = "Dovresti salutarmi";
                }
                break;
            case SMISTAMENTO:
                if (checkIsPresent(smistamentoMenu)) {
                    // Mostro il menu
                    forUser = "Ecco la lista di tutte le nostre pizze:-l";
                    for (int i = 0; i < listaPizze.length; i++) {
                        forUser = forUser + listaPizze[i][0] + "\t\t€ " + listaPizze[i][1] + " -l";
                    }
                    forUser += "Appena hai pensato a cosa prendere dimmelo pure, ma metti una virgola tra le varie pizze.";
                    state = ORDINE;
                } else if (checkIsPresent(smistamentoOrdine)) {
                    // Mostro il menu
                    forUser = "Dimmi che pizze vuoi, ma metti una virgola tra le varie pizze.";
                    state = ORDINE;
                } else {
                    forUser = "Non ho ben capito cosa vuoi fare, potresti riformulare la risposta grazie.";
                }
                break;
            case ORDINE:
                if (checkIsPresent(arrayPizze())) {
                    //se entra vuol dire che c'è almeno una pizza scritta
                    //System.out.println("dentro ordine vuol dire che c'è una pizza");
                    //Controllo se c'è almeno una virgola
                    if (textUser.contains(",")) {
                        //System.out.println("c'è la virgola");
                        String[] splitted = textUser.split(",");
                        for (int i = 0; i < splitted.length; i++) {
                            numPizze[idPizzaByName(qualePizza(splitted[i]))] += 1;
                        }
                    } else {
                        numPizze[idPizzaByName(qualePizza(textUser))] += 1;
                    }
                    forUser = "Bene, ora dimmi l'indirizzo.";
                    state = INDIRIZZO;

                } else {
                    forUser = "Non ho ben capito che pizze mi vuoi ordinare, potresti ridirmele?";
                }
                break;
            case INDIRIZZO:
                indirizzo = textUser;

                forUser = "TOTALE DA PAGARE: " + totale() + "-l";
                forUser += "Indirizzo di Spedizione: " + indirizzo + "-l-l";
                forUser += "Confermi l'ordine? [Si/No]";

                state = CONFERMAFINALE;
                break;

            case CONFERMAFINALE:
                if (textUser.equalsIgnoreCase("si")) {
                    forUser = "Grazie per aver ordinato con AutOrder!-l"
                            + "Il suo ordine è in lavorazione";
                } else {
                    forUser = "Il suo ordine è stato ANNULLATO";
                }
                state = FINE;

                break;

            case FINE:
                forUser = "COMMAND x0 - QUIT";

                break;
        }

        return forUser;
    }

    private boolean checkIsPresent(String[] data) {
        int i = 0;
        while (i < data.length) {
            if ((textUser.toLowerCase()).contains(data[i].toLowerCase())) {
                return true;
            }
            i++;
        }
        return false;
    }

    private String qualePizza(String testo) {
        int i = 0;
        while (i < arrayPizze().length) {
            if ((testo.toLowerCase()).contains((arrayPizze()[i].toLowerCase()))) {
                return arrayPizze()[i];
            }
            i++;
        }
        return null;
    }

    private String[] arrayPizze() {
        String[] theReturn = new String[listaPizze.length];
        for (int i = 0; i < listaPizze.length; i++) {
            theReturn[i] = listaPizze[i][0];
        }
        return theReturn;
    }

    private int idPizzaByName(String nomePizza) {
        int i = 0;
        while (i < listaPizze.length) {
            if (listaPizze[i][0].contains(nomePizza)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private double totale() {
        for (int i = 0; i < numPizze.length; i++) {
            totale += numPizze[i] * Double.parseDouble(listaPizze[i][1]);
        }
        return totale;
    }
}
