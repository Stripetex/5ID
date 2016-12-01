package server;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Stack;

public class Protocol {
    Date d = new Date();
    boolean esci= false;

    
    Random rando = new Random();
    private static final int PRESENT = 0;
    private static final int NOME = 1;
    private static final int USCITAM = 2;
    private static final int CENTRO = 3;
    Risp r = new Risp();
    Stack memoria = new Stack();
    private static String utName = null;

    private int state = PRESENT;

    public String processInput(String theInput) throws IOException, IOException {
        String theOutput = "";
        String f = theInput.toLowerCase();
       

        switch (state) {
            case PRESENT:
                theOutput = r.getRisp(0);
                state = NOME;
                break;
            case NOME:
                utName = inizioNome(theInput);
                if (utName.equals("Come ti chiami?")) {
                    theOutput = utName;
                } else {
                    theOutput = r.getParola(2)+" " + utName;
                    state = CENTRO;
                }
                break;

            case CENTRO:
                if (f.contains("io vado") && f.contains("ciao")) {
                    theOutput = "bye.";
                    
                } else if (f.contains("esci") || f.contains("escile")) {
                    if (memoria.search("escile") == -1) {
                        theOutput = Risp.getParola(1) +" "+ utName + " "+Risp.getRisp(1);
                        state = USCITAM;
                        memoria.push("escile");
                    } else {
                        theOutput = Risp.getRispNeg(0);
                    }
                } else if (f.equalsIgnoreCase("grazie")) {
                    theOutput = "Grazie a te!";    
                } else if (f.contains("che ore sono") || f.contains("l'ora")) {
                    theOutput = Risp.getRisp(2)+ d;
                } else if (f.contains("giorno") && f.contains("oggi")) {
                    Date data = new Date();
                    theOutput = Risp.getRisp(3) + data;

                } else if (f.contains("tua figlia") || f.contains("sofia")) {
                    if (f.contains("come sta") || f.contains("come va") || f.contains("tutto ok")) {
                        theOutput = Risp.getRisp(4)+" "+Risp.getParola(4);
                    } else if (f.contains("dorme") || f.contains("tua figlia è tranquilla?")) {
                        theOutput = Risp.getRisp(5);
                    }

                } else if (((f.contains("matri") || f.contains("moroso") || f.contains("fidanzato")) && f.contains("segnato")) || (f.contains("gol") && (f.contains("matri") || f.contains("moroso") || f.contains("fidanzato")))) {
                    if (memoria.search("MatriGol") == -1) {
                        theOutput = Risp.getRisp(6);
                        memoria.push("MatriGol");
                    }
                    else if (f.contains("come sta") || f.contains("come va") || f.contains("tutto ok")) {
                        theOutput = Risp.getRisp(4) + " " + Risp.getParola(4);
                    } else {
                        theOutput =  Risp.getRispNeg(2)+" "+ utName +" "+ Risp.getRispNeg(3);
                    }
                } else {
                    theOutput = Risp.getRispNeg(1);
                }
                //SISTEMARE OUTPUT "NULL"
                break;
            case USCITAM:
                if (f.contains("dai") || f.contains("escile")) {
                    theOutput = "Uffa..tieni, va bene: ( . )( . )";
                    state = CENTRO;
                } else if (f.contains("perch")) {
                    theOutput = "Perche MitraMatri mi controlla";
                } else {
                    theOutput = "Come? ripeti non ho capito";
                    state = CENTRO;
                    break;
                }
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
        }else if (input.contains("chiamo")) {
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
