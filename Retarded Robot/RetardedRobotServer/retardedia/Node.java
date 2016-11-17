/**
 * Node.
 * @author Lesti | Studio.
 * @version 1.0.1
 */

package retardedia;

public class Node {

    protected String sentence;
    protected int[] sequel;
    protected int pos = 0;

    public Node(String s) {
        sentence = s;
        sequel = new int[10];
    }
    
    public Node(){
        sentence = "";
        sequel = new int[10];
    }

    public void addSequel(int n) {
        sequel[pos]=n;
        pos++;
    }

    @Override
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
