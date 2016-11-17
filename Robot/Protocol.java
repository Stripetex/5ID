package robotserver;

import java.io.IOException;
import java.util.Random;

public class Protocol {

    Random rando = new Random();
    private static final int PRESENTAZIONE = 0;
    private static final int NOME = 1;
    private static final int ORDINA = 2;
    private static final int CENTRO = 3;
    private static final int UBRIACO = 4;
    Risp r = new Risp();
    double Ordinazioni = 0.0;
    private static String utName = null;

    private int state = PRESENTAZIONE;

    public String processInput(String theInput) throws IOException, IOException {
        String theOutput = "";
        String f = theInput.toLowerCase();

        switch (state) {
            case UBRIACO:
                theOutput=Risp.getUbriaco(rando.nextInt(9))+"#Scusa "+utName+" sono un pelo ubriaco";
                boolean b = rando.nextBoolean();
                    if(b) state=UBRIACO;
                    else    state= CENTRO;
                
                break;
            case PRESENTAZIONE:
                theOutput = r.getRisp(0);
                state = NOME;
                break;
            case NOME:
                utName = inizioNome(theInput);
                if (utName.equals("Come ti chiami?")) {
                    theOutput = utName;
                } else {
                    theOutput = r.getParola(2) + " " + utName;
                    state = CENTRO;
                }
                break;
            case ORDINA:

                if (!f.contains("basta") && !f.contains("no")) {

                    if (f.contains("menu")|| f.contains("ordinare")) {
                        theOutput = Risp.getMn();
                    } else {
                        if(f.contains(" ")){
                        String temp[] = f.split(" ");
                        double costo = Risp.getMenu(temp[1]);
                        int unita = Integer.valueOf(temp[0]);
                        if(costo==0.0){
                            theOutput="Mi dispiace ma non lo abbiamo";
                        }
                        else{
                            Ordinazioni += costo*unita;
                            theOutput = "Serve altro?";
                        }
                        }
                        else{
                            theOutput="Non ho capito#ho un problema nella testa";
                        }
                        
                    }

                } else {//=basta

                    theOutput = "Mi devi: " + Ordinazioni+" sberle";
                    Ordinazioni=0;
                    state= UBRIACO;
                }

                break;
            case CENTRO:

                if (f.contains("io vado") && f.contains("ciao")) {
                    theOutput = "bye.";

                } else if (f.contains("menu")|| f.contains("ordinare")) {
                    theOutput = Risp.getMn();
                    state = ORDINA;
                   
                }
                else{
                    if(f.contains("moneta")){
                        
                        if(rando.nextBoolean()==false) theOutput="E' uscita testa";
                        else theOutput="E' uscita croce";
                        
                    }
                     else{
                        theOutput="Non ho capito#ho un problema nella testa";
                        }
                    
                }
               
      
                break;

            default:
                break;
        }
        return theOutput;
    }

    public String inizioNome(String input) {
        String theOutput = null;
        String[] inpArr = input.split(" ");
        if (inpArr.length == 1 && !(input.equalsIgnoreCase("ciao"))) {
            theOutput = inpArr[0];
        } else if (input.contains("chiamo")) {
            int i = 0;
            while (i != inpArr.length) {
                if ((inpArr[i].equalsIgnoreCase("chiamo"))) {
                    theOutput = inpArr[i + 1];
                    i = inpArr.length;
                } else {
                    i++;
                }

            }
        } else if (input.contains("sono")) {
            int i = 0;
            while (i != inpArr.length) {
                if ((inpArr[i].equalsIgnoreCase("sono"))) {
                    theOutput = inpArr[i + 1];
                    i = inpArr.length;
                } else {
                    i++;
                }

            }
        } else if (input.contains("mio nome")) {
            int i = 0;
            while (i != inpArr.length) {
                if ((inpArr[i].equalsIgnoreCase("nome"))) {
                    theOutput = inpArr[i + 2];
                    i = inpArr.length;
                } else {
                    i++;
                }

            }

        } else {
            theOutput = "Come ti chiami?";

        }
        return theOutput;
    }

}
