/**
 * Retarded IA.
 * @author Lesti | Studio.
 * @version 1.0.1
 */

package retardedia;

import java.io.IOException;

public class RetardedIA {

    private Graph graph;
    protected Node node;
    private boolean first;
    private boolean add;

    public RetardedIA(Graph graph) throws IOException {
        this.graph = graph;
        node = new Node();
        first = true;
        add = false;
    }

    public String firstReply(String s) {
        String reply = "";
        node = graph.getNow(graph.find(s));
        node = graph.next(node);
        reply += node.sentence;
        System.out.println(node.sentence);
        first = !first;
        return reply;
    }

    public String reply(String s) { // risposta del client ### domanda IA
        
        String reply = "";
        
        if (first) {
            reply = firstReply(s);
        } else if (add) {
            
            graph.addNode(s);
            graph.download("graph.dat");
            add = false;
            
        } else {
            
            Node temp = graph.next(s, node);
            
            if (temp != null) {
                reply = temp.sentence;
            } else {
                add = true;
                reply = "Non saprei, tu come risponderesti?";
            }
        }
        
        return reply;
    }

    public void addReply(String s) {
        int n = graph.find(s);
        if (n == -1) { graph.addNode(s); }
        graph.addSequel(s, node);
    }
}