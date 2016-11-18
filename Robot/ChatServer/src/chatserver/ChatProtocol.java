package chatserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.relique.jdbc.csv.CsvDriver;
/**
 *
 * @author fabio
 */
public class ChatProtocol{
    
    
    private final String[] sport = {"calcio", "basket"};
    
    private String sportSel = null;
    
    
    
    private final String[] leghe = {"serie a", "serie b", "la liga", "premier league"};
    private final String[] leghePath = {"./csv/Calcio/SerieA","./csv/Calcio/SerieB", "./csv/Calcio/Liga", "./csv/Calcio/Premier"};
    
    private final String[] anniCalcio = {"2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016"};
    
    
    private final String[] anniBasket = {"2012","2013"};

    private static final int WAITING = 0;
    private static final int SENT = 1;
    private static final int RISPOSTA = 2;
    private static final int ANOTHER = 99;
 
 
    private int state = WAITING;
 
    public String processInput(String theInput) throws Exception {
        
        String theOutput = null;
        if(state == WAITING){
            theOutput = "Ciao di che sport vuoi avere informazioni?";
            state = SENT;
        }else if (state == SENT) {
            if(selezionaSport(theInput)){
                theOutput = "Cosa vuoi sapere?";
                state = RISPOSTA;
            }else theOutput="Mi dispiace devo ancora impare i risultati di questo sport *Dimmi un'altro sport";
        }else if (state == RISPOSTA) {
            theOutput = rispostaDomanda(theInput);
            state = ANOTHER;
        }  else if (state == ANOTHER) {
            if (Arrays.asList(theInput.toLowerCase().split(" ")).contains("si")||Arrays.asList(theInput.toLowerCase().split(" ")).contains("certo")) {
                theOutput = "Cosa vuoi sapere?";
                state = RISPOSTA;
            } else {
                theOutput = "Ok ciao";
                state = WAITING;
            }
        }
        return theOutput;
    }
    
    private String rispostaDomanda(String input){
        input = input.toLowerCase();
        String risposta = "Non posso fare quello che mi chiedi.. Vuoi fare qualcos'altro?";
        if(sportSel.equals("calcio")){
            if(Arrays.asList(input.split(" ")).contains("risultato")){
                risposta = risultatoCalcio(input)+"*Vuoi sapere altro?";
            }else if(Arrays.asList(input.split(" ")).contains("risultati")){
                risposta = risultatiGiornata(input) + "*Vuoi sapere altro?";
            }
        }else if(sportSel.equals("basket")){
            risposta = "Mi dispiace devo ancora impare i risultati di questo sport";
        }
        return risposta;
    }
    
    private boolean selezionaSport(String input){
        int pos = 0;
        boolean trovato = false;
        String[] parole = input.split(" ");
        while(pos<parole.length && !trovato){
            for (int i = 0; i < sport.length && !trovato; i++) {
                if(sport[i].equalsIgnoreCase(parole[pos])){
                    trovato = true;
                    sportSel = sport[i];
                }
            }
            pos++;
        }
        return trovato;
    }
    
    private String risultatoCalcio(String input){
        boolean cerca = true;
        String sCasa = null, sOspite = null, lega = null, anno = null;
        try{
            lega = leghePath[trovaLegaCalcio(input)];
            anno = anniCalcio[trovaAnnoCalcio(input)];
            String[] squadre = cercaSquadreCalcio(input,anno,lega);
            sCasa = squadre[0];
            sOspite = squadre[1];
        }catch(Exception e){
            cerca = false;
        }
        
        
        return cerca ? cercaRisultatoCalcio(sCasa, sOspite, lega, anno) : "Non ho capito la domanda. Vuoi farne un'altra?";
    }
    
    private String cercaRisultatoCalcio(String sCasa, String sOspite, String lega, String anno) {
        try {
            // Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");
            String risultato = "";
            // Create a connection.
            // A single connection is thread-safe for use by several threads.
            File f = new File(getClass().getResource(lega+"/"+anno+".csv").getPath());
            Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + f.getPath().substring(0, f.getPath().length()-9));
            // Create a Statement object to execute the query with.
            // A Statement is not thread-safe.
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT HomeTeam,AwayTeam,FTHG,FTAG FROM "+ anno + " WHERE HomeTeam='" + sCasa + "' AND AwayTeam='" + sOspite + "'");
            while(results.next()){
                risultato = results.getString("HomeTeam")+"-"+results.getString("AwayTeam")+" e' "+results.getInt("FTHG")+"-"+results.getInt("FTAG");
                
            }
            return risultato.equals("") ? "Non ho trovato questa partita" : "Il risultato di " + risultato;
            // Clean up
            //conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ChatProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        
    }
    
    private int trovaLegaCalcio(String input){
        int pos = 0, posizione = 0;
        boolean trovato = false;
        String[] parole = input.split(" ");
        while(pos<parole.length && !trovato){
            for (int i = 0; i < leghe.length && !trovato; i++) {
                if(leghe[i].equalsIgnoreCase(parole[pos])){
                    trovato = true;
                    posizione = i;
                }
                if(!trovato && pos<parole.length-1 && leghe[i].equalsIgnoreCase(parole[pos]+" "+parole[pos+1])){
                    trovato = true;
                    posizione = i;
                }
            }
            pos++;
        }
        return trovato ? posizione : -1;
    }
    
    private int trovaAnnoCalcio(String input){
        int pos = 0, posizione = 0;
        boolean trovato = false;
        String[] parole = input.split(" ");
        while(pos<parole.length && !trovato){
            for (int i = 0; i < anniCalcio.length; i++) {
                if(anniCalcio[i].equalsIgnoreCase(parole[pos])){
                    trovato = true;
                    posizione = i;
                }
            }
            pos++;
        }
        return trovato ? posizione : -1;
    }
    
    private String[] cercaSquadreCalcio(String input, String anno, String lega){
        String[] squadre = {"",""};
        try {
            
            // Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");
            // Create a connection.
            // A single connection is thread-safe for use by several threads.
            File f = new File(getClass().getResource(lega+"/"+anno+".csv").getPath());
            Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + f.getPath().substring(0, f.getPath().length()-9));
            // Create a Statement object to execute the query with.
            // A Statement is not thread-safe.
            Statement stmt = conn.createStatement();
            
            String[] parole = input.split(" ");
            boolean trovatoCasa = false, trovatoOspite = false;
            int pos = 0;
            while(pos<parole.length && !trovatoOspite){
                ResultSet results = stmt.executeQuery("SELECT HomeTeam FROM "+ anno);
                boolean trovato = false;
                while(results.next() && !trovato){
                    if(results.getString("HomeTeam").equalsIgnoreCase(parole[pos])){
                        if(!trovatoCasa){
                            trovatoCasa = true;
                            squadre[0] = results.getString("HomeTeam");
                        }else if(!trovatoOspite){
                            trovatoOspite = true;
                            squadre[1] = results.getString("HomeTeam");
                        }
                        trovato = true;
                    }else if(pos<parole.length-1 && results.getString("HomeTeam").equalsIgnoreCase(parole[pos]+" "+parole[pos+1])){
                        if(!trovatoCasa){
                            trovatoCasa = true;
                            squadre[0] = results.getString("HomeTeam");
                        }else if(!trovatoOspite){
                            trovatoOspite = true;
                            squadre[1] = results.getString("HomeTeam");
                        }
                        trovato = true;
                    }

                }
                pos++;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChatProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return squadre;
    }

    private String risultatiGiornata(String input){
        boolean cerca = true;
        String giorno = null, lega = null, anno = null;
        try{
            lega = leghePath[trovaLegaCalcio(input)];
            anno = anniCalcio[trovaAnnoCalcio(input)];
            giorno = cercaGiorno(lega,anno,input);
        }catch(Exception e){
            cerca = false;
        }
        
        return cerca ? cercaRisultatiCalcio(lega, anno, giorno) : "Non ho capito la domanda. Vuoi farne un'altra?";
    }
    
    private String cercaRisultatiCalcio(String lega, String anno, String giorno) {
        try {
            // Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");
            String risultato = "";
            // Create a connection.
            // A single connection is thread-safe for use by several threads.
            File f = new File(getClass().getResource(lega+"/"+anno+".csv").getPath());
            Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + f.getPath().substring(0, f.getPath().length()-9));
            // Create a Statement object to execute the query with.
            // A Statement is not thread-safe.
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT HomeTeam,AwayTeam,FTHG,FTAG FROM "+ anno + " WHERE Date='" + giorno + "'");
            while(results.next()){
                risultato = risultato + "*" + results.getString("HomeTeam")+"-"+results.getString("AwayTeam")+" "+results.getInt("FTHG")+"-"+results.getInt("FTAG")+"    ";
            }
            return risultato.equals("") ? "Non ho trovato questa partita" : "I risultati: " + risultato;
            // Clean up
            //conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ChatProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        
    }
    
    private String cercaGiorno(String lega, String anno, String input){
        String giorno = "";
        try {
            
            // Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");
            // Create a connection.
            // A single connection is thread-safe for use by several threads.
            File f = new File(getClass().getResource(lega+"/"+anno+".csv").getPath());
            Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + f.getPath().substring(0, f.getPath().length()-9));
            // Create a Statement object to execute the query with.
            // A Statement is not thread-safe.
            Statement stmt = conn.createStatement();
            
            String[] parole = input.split(" ");
            int pos = 0;            
            boolean trovato = false;
            while(pos<parole.length && !trovato){
                ResultSet results = stmt.executeQuery("SELECT Date FROM "+ anno);
                while(results.next() && !trovato){
                    if(results.getString("Date").equalsIgnoreCase(parole[pos])){
                        if(!trovato){
                            giorno = results.getString("Date");
                        }
                    }

                }
                pos++;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChatProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return giorno;
    }
}
