package robotserver;

import java.io.IOException;
import java.util.Random;

import java.net.*;
import java.io.*;
import java.util.Date;

public class Protocol {
	private static final int INIZIO = 0;
	private static final int ASPETTA = 1;
	private static final int RISPONDI = 2;
	private static final int ALTRE = 3;
	private static final int goku = 4;

	private static final int NUMJOKES = 5;
	private Risp risposta = new Risp();
	private int state = INIZIO;
	private int currentJoke = 0;

	public String processInput(String theInput) {
		String theOutput = null;
		String name = "";

		if (state == INIZIO) {
			theOutput = "Sono Vegeta il principe dei sayan.¶ Dimmi il tuo nome?";

			state = goku;
		} else if (state == goku) {
			if (theInput.equalsIgnoreCase("Goku")) {
				theOutput = "KAKAROOOT, ti batterooo.";
				state = RISPONDI;

			} else {
				name = theInput;
				theOutput = "Ciao " + name;
				state = ASPETTA;
			}

		} else if (state == ASPETTA) {

			theOutput = name + " fammi una domanda";
			state = RISPONDI;

		} else if (state == RISPONDI) {
			theOutput = risposta.cercaDomanada(theInput);
			if (!theOutput.equals("Non ho capito terreste")) {
				state = ALTRE;
			} else {
				state = ASPETTA;
			}

		} else if (state == ALTRE) {
			if (theInput.equalsIgnoreCase("y")) {
				theOutput = "Cannone galickkkkk!";
				state = ASPETTA;
			} else {
				theOutput = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
				state = INIZIO;
			}
		}
		return theOutput;
	}

}