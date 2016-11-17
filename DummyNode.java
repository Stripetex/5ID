package dummyia;

import java.util.ArrayList;

/**
 *
 * @author Blank
 */
public class DummyNode {

    protected String sentence;
    protected int[] sequel;
    protected int pos = 0;

    public DummyNode(String s) {
        sentence = s;
        sequel = new int[10];
    }
    
    public DummyNode(){
        sentence = "";
        sequel = new int[10];
    }

    public void addSequel(int n) {
        sequel[pos]=n;
        pos++;
    }

    public String toString() {
        String s = "";
        s += sentence + "_";
        s +=sequel[0];
        for (int i = 1; i < pos; i++) {
            s += ";" +  sequel[i];
        }
        return s;
    }
    
    public boolean equals(String s){
        return sentence.equals(s);
    }
}
