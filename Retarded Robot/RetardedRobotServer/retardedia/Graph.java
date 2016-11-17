/**
 * Graph.
 * @author Lesti | Studio.
 * @version 1.0.1
 */

package retardedia;

import java.io.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Graph {

    private Semaphore semaphore;
    
    private Node[] graph;
    private int pos;

    public Graph(String file) {

        pos = 0;
        semaphore = new Semaphore(1);

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            graph = new Node[Integer.parseInt(reader.readLine())];

            String[] s = null;
            String[] t = null;

            String line = "";
            while ((line = reader.readLine()) != null) {

                s = line.split("_");
                addNode(s[0]);

                if (s.length > 1) {
                    t = s[1].split(";");
                    for (int i = 0; i < t.length; i++) {
                        graph[pos - 1].addSequel(Integer.parseInt(t[i]));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot read the file!");
        }
    }

    public void addNode(String s) {

        try {
        
            semaphore.acquire();
            
            int index = find(s);
            if (index == -1) {

                if (pos >= graph.length) {

                    Node[] temp = new Node[graph.length * 2];

                    for (int i = 0; i < graph.length; i++) {
                        temp[i] = graph[i];
                    }

                    graph = temp;
                }

                Node temp = new Node(s);
                graph[pos] = temp;
                pos++;
            }
        
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    public int find(String s) {

        boolean find = false;
        int index = -1;

        for (int i = 0; i < pos && !find; i++) {
            find = graph[i].equals(s);
            if (find) {
                index = i;
            }
        }

        return index;
    }

    public void addSequel(String s, Node now) {

        try {
        
            semaphore.acquire();
        
            int n = find(s);

            if (n == -1) {
                addNode(s);
                now.addSequel(pos);
            } else {
                now.addSequel(n);
            }
            
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public Node getNow(int i) {
        return graph[i];
    }

    public void download(String file) {

        try {

            PrintWriter writer = new PrintWriter(new FileWriter(file));
            
            writer.println(Integer.toString(pos));
            for (int i = 0; i < pos; i++) {
                writer.append(graph[i].toString());
            }
            
            writer.close();
            
        } catch (Exception e) {
            System.out.println("Cannot write the file!");
        }
    }

    public Node next(Node now) {
        now = graph[new Random().nextInt(now.pos)];
        return now;
    }

    public Node next(String s, Node now) {
        
        boolean find = false;
        int n = -1;
        
        for (int i = 0; i < now.pos && !find; i++) {
            find = (graph[now.sequel[i]].sentence).equals(s);
            if (find) { n = i; }
        }
        
        if (n == -1) {
            
            n = find(s);
            if (n == -1) {
                return null;
            } else {
                now.addSequel(n);
                next(s, now);
            }
            
        } else {
            return graph[now.sequel[n]];
        }
        
        return null;
    }
}
