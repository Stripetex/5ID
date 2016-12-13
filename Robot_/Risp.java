package robotserver;


import static java.lang.Math.random;
import java.util.Random;

/**
 *
 * @author Andrea
 */
import java.util.Date;

public class Risp {
	Date d;
	public String[][] risposte = new String[7][4];

	public Risp() {
		d = new Date();
		risposte[0] = fare;
		risposte[1] = ora;
		risposte[2] = Chisei;
		risposte[3] = Chelivellosei;
		risposte[4] = kakarot;
		risposte[5] = zamasu;
		risposte[6] = distruggere;
	}

	private String[] domande = {"Cosa fai?", "Dimmi l'ora", "Chi sei?", "Che livello sei?", "Hai battuto kakarot?", "Con Zamasu come va?", "Ti distruggerò"};

	private String[] fare = {
		"Mi alleno per superare kakarot!",
		"....",
		"Mi stai facendo arrabbiare",
		"Ti uccidero"};

	private String[] ora = {
		"Sono le " + d,
		"Io sono un principe sei tu che¶ dovresti dirmi l'ora",
		"No",
		"Il tempo è relativo"
	};

	private String[] Chisei = {
		"Sono Vegeta il principe dei sayan.",
		"Sono quello che ti distruggerà",
		"Sono il tuo nemico",
		"Colui che ti fara vedere¶ la potenza di un principe"
	};

	private String[] Chelivellosei = {
		"Sono Vegeta il più dei¶ sayan.",
		"Sono più fortre di te",
		"AL LIVELLO DI UN DIO",
		"Più del tuo"
	};

	private String[] kakarot = {
		"Non ancora",
		"Lo distruggero.",
		"Lo supererò",
		"Manca poco"
	};

	private String[] zamasu = {
		"Lo distruggero",
		"E' solo un montato",
		"Non ,mi fa affatto¶ paura",
		"Manca poco alla sua fine"
	};

	private String[] distruggere = {
		"Vorresti.",
		"La tua arroganza portera¶ alla tua distruzione",
		"Non osare a minacciare¶ un dio",
		"Non montarti la testa"
	};

	public String cercaDomanada(String s) {
		int y = (int) (Math.random() * fare.length);
		for (int i = 0; i < domande.length; i++) {
			if (domande[i].equals(s)) {
				return risposte[i][y]; 
			}
		}
		return "Non ho capito terreste";
	}


}

