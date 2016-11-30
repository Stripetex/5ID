package smartserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Marco
 */
public class SmartProtocol {

    private static final int WELCOME = 0;
    private static final int ASKING = 1;
    private static final int ANOTHERONE = 2;
    private static final int SUGGEST = 3;

    private int state = WELCOME;
    private String substate = null;
    private String currentTopic;
    private String help = "Here the things that you can ask me:"
            + "*1. You can ask me for some advice about movies."
            + "*2. You can ask me for some advice about music.";
    private ArrayList movieGenres = new ArrayList();
    private ArrayList genresObjects = new ArrayList();
    private ArrayList topicList = new ArrayList();
    private CsvReader csv;
    private final String[] topics = {"movie", "music"};
    private final String[] genres = {"Action","Adventure","Animation","Children","Comedy","Crime","Documentary","Drama","Fantasy","Film-Noir","Horror","Musical","Mystery","Romance","Sci-Fi","Thriller","War","Western","(no genres listed)"};
    private Random randomGenerator;

    public SmartProtocol() throws IOException, ClassNotFoundException, SQLException {
        csv = new CsvReader();
        for (int i = 0; i < topics.length; i++) {
            topicList.add(topics[i]);
        }
        for (int i = 0; i < genres.length; i++) {
            movieGenres.add(genres[i].toLowerCase());
        }
        loadMoviesGenres();
        randomGenerator = new Random();
    }

    public String processInput(String input) {
        String output = null;
        if (input.equals("help")) {
            return help;
        }
        if (state == WELCOME) {
            state = ASKING;
            return output = "Hello! My name is Alfred, how can I help you? "
                    + "Type help if you want to know what can I do";
        } else if (state == ASKING) {
            currentTopic = topic(input);
            if (topicList.contains(currentTopic)) {
                output = writeMovieGenres();
                state = SUGGEST;
                return "So, you want to ask me some advice about a " + currentTopic + ". " + "*" + output;
            } else {
                return "I'm sorry, I don't understand what you want me to do";
            }
        } else if (state == SUGGEST){
            if(movieGenres.contains(input) || Integer.parseInt(input) == movieGenres.indexOf(movieGenres.get(Integer.parseInt(input)))){
                String choosenOne = randomMovie(input);
                state = ANOTHERONE;
                return "I would suggest you to watch " + choosenOne + ". " + "*(click enter to ask me another question)";
            } else {
                return "I don't understand what genre did you ask me for, try with another one.";
            }
        } else if (state == ANOTHERONE){
            state = ASKING;
            return "So what do you want my suggestion on again?";
        } else {
            return "I'm sorry, what did you say?";    
        }
        
    }

    //Determinating current topic
    public String topic(String input) {
        String changeTopic;
        if (input.contains("movie") || input.contains("movies")) {
            return changeTopic = "movie";
        } else if (input.contains("song") || input.contains("good")) {
            return changeTopic = "music";
        } else {
            return "I'm sorry, I don't understand. Try with another statement";
        }
    }

    public void loadMoviesGenres() throws IOException, SQLException {
        for (int i = 0; i < movieGenres.size(); i++) {
            genresObjects.add(new Genre(movieGenres.get(i).toString(),csv.readFilms(movieGenres.get(i).toString())));
        }
        
        System.out.println("Server ready to serve!");
        
    }
    
    
    public String writeMovieGenres(){
        String output = "Here there are all the movie's genres I know: ";
        int cont = 0;
        for (int i = 0; i < movieGenres.size(); i++) {
            if(cont % 3 == 0){
                output += "*" + i + ". " + movieGenres.get(i);
            } else {
                output += " " + i + ". " + movieGenres.get(i);
            }
            cont++;
        }
        output += "*I hope you'll find the genre that you're looking for."
                + "*When you decide, please let me know what genres would you prefer.";
        return output;
    }
    
    public String randomMovie(String input){
        String randomMovie = null;
        int index = 0;
        if(Integer.parseInt(input) == movieGenres.indexOf(movieGenres.get(Integer.parseInt(input)))){
            index = Integer.parseInt(input);
        } else {
            index = movieGenres.indexOf(input);
        }
        int random = randomGenerator.nextInt(((Genre)genresObjects.get(index)).getMovies().size()); //Numero casuale basato sulla grandezza dell'arrayList contenente i film di un determinato genere
        Genre a = ((Genre)genresObjects.get(index));
        randomMovie = a.getMovies().get(random).toString(); //Nome di un film scelto a caso dall'arrayList dei film del genere selezionato
        return randomMovie;
    }
    

}

class Genre {
    String genreName;
    ArrayList movies;

    public Genre(String genreName, ArrayList movies) {
        this.genreName = genreName;
        this.movies = movies;
    }
    public ArrayList getMovies() {
        return movies;
    }

    public String getGenreName() {
        return genreName;
    }
    
    
}
