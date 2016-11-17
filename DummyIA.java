package dummyia;

import java.io.IOException;

/**
 *
 * @author Blank
 */
public class DummyIA {

    private DummyGraph graph;
    protected DummyNode node;
    private boolean first;
    private boolean add;

    public DummyIA(DummyGraph g) throws IOException {
        graph = g;
        node = new DummyNode();
        first = true;
        add = false;

    }

    public String firstReply(String s) {
        String reply = "";
        int n = graph.find(s);
        node = graph.getNow(n);
        node = graph.next(node);
        reply += node.sentence;
        System.out.println(node.sentence);
        first = !first;
        return reply;
    }

    public String reply(String s) {// deve essere trasmessa sia la risposta del client alla domanda fatta dall'IA sia la domanda che lui pone separate da ###
        String reply = "";
        if (first) {
            reply = firstReply(s);
        } else if (add) {
            graph.addNode(s);
            graph.download("graph.dat");
            add=false;
        } else {
            DummyNode temp = graph.next(s, node);
            if (temp != null) {
                reply = temp.sentence;
            } else {
                add = true;
                reply = "Non so come continuare la conversazione dimmi tu";
            }
        }
        return reply;
    }

    public void addReply(String s) {
        int n = graph.find(s);
        if (n == -1) {
            graph.addNode(s);
        }
        graph.addSequel(s, node);

    }

}
