
import java.net.*;
import java.io.*;

public class BotProtocol {

    private static final int PRESENTAZIONE = 0;
    private static final int DOMANDA = 4;
    private static final int RISPOSTA = 1;
    private static final int ANOTHER = 2;

    private int state = PRESENTAZIONE;
    private int currentJoke = 0;

    private String[] questions = {"How are you?", "What's your favourite colour?", "What do you like?", "What time is it?", "Knock Knock"};
    private String[] answers = {"I'm fine, thank you!",
        "I really like blue.",
        "I'm a robot, I don't know.",
        "Summer time",
        "Who's there? Oh, it was a joke..."};

    public String processInput(String theInput) {
        String theOutput = null;
        if (state == PRESENTAZIONE) {
            theOutput = "Hi! I'm Alexa, and I'm here to answer your questions.";
            state = DOMANDA;
        } else if (state == DOMANDA) {
            if (theInput.equalsIgnoreCase("Hi Alexa") || theInput.equalsIgnoreCase("Hi")) {
                theOutput="Ask me something!";
                state = RISPOSTA;
            } else {
                theOutput = "You're supposed to say \"Hi Alexa\", you know...";
                state = DOMANDA;
            }

        } else if (state == RISPOSTA) {
            for (int i = 0; i < questions.length; i++) {
                if (questions[i].equalsIgnoreCase(theInput)) {
                    theOutput = answers[i]+" Want another? (Y/n)";
                    state = ANOTHER;
                }
            }
            if (state != ANOTHER) {
                theOutput = "I'm not able to answer this question, I'm sorry. Want another? (Y/n)";
                state = ANOTHER;
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput="Ask me something!";
                state = RISPOSTA;
            } else {
                theOutput = "Bye.";
                state = PRESENTAZIONE;
            }
        }
        return theOutput;
    }
}
