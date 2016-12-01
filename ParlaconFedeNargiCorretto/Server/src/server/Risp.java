/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Nicola
 */
public class Risp {
    public void Risp(){};
    
    private static final String[] risposte = {
        "Ciao sono Federica Nargi, chi sei?", 
        "non te le esco", 
        "L'ora? ti dico anche che data è perchè sono brava: ",
        "Giorno? ti dico tutto, compresa l'ora: ",
        "Tutto ok",
        "Sisi è una brava bambina",
        "Si ha fatto un gran gol al 89°"
        };
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

    
    
    public static String getRisp(int pos){
            return risposte[pos];
        }
    public static String getParola(int pos){
        return monosillabi[pos];
    }
    public static String getRispNeg(int pos){
        return rispNegative[pos];
    }

    
}
