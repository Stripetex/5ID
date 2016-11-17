package dummyia;

import java.io.*;
import java.util.Random;

/**
 *
 * @author Blank
 */
public class DummyGraph {

    protected DummyNode[] graph;
    private int pos;

    public DummyGraph(String file) {//constructor method from file
        pos = 0;
        try {

            BufferedReader read = new BufferedReader(new FileReader(file));
            String temp = read.readLine();
            graph = new DummyNode[Integer.parseInt(temp)];
            String[] s = null;
            String[] t = null;
            while ((temp = read.readLine()) != null) {
                s = temp.split("_");
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

        int index = find(s);
        if (index == -1) {

            if (pos >= graph.length) {

                DummyNode[] temp = new DummyNode[graph.length * 2];//graph.length*2 to limit this operation as possible

                for (int i = 0; i < graph.length; i++) {
                    temp[i] = graph[i];
                }

                graph = temp;
            }

            DummyNode temp = new DummyNode(s);
            graph[pos] = temp;
            //System.out.println(graph[pos].toString());
            pos++;

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

    public void addSequel(String s, DummyNode now) {

        int n = find(s);

        if (n == -1) {
            addNode(s);
            now.addSequel(pos);
        } else {
            now.addSequel(n);
        }
    }

    public DummyNode getNow(int i) {
        return graph[i];
    }

    public void download(String file) {

        try {

            BufferedWriter write = new BufferedWriter(new FileWriter(file));
            write.write("");
            write.append(Integer.toString(pos));
            write.newLine();
            for (int i = 0; i < pos; i++) {
                write.append(graph[i].toString());
                write.newLine();
            }
            write.close();
        } catch (Exception e) {
            System.out.println("Cannot write the file!");
        }
    }

    public DummyNode next(DummyNode now) {
        Random r = new Random();
        int n=r.nextInt(now.pos);
        System.out.println(n);
        now = graph[n];
        return now;
    }

    public DummyNode next(String s, DummyNode now) {
        boolean find = false;
        int n = -1;
        for (int i = 0; i < now.pos && !find; i++) {
            find = (graph[now.sequel[i]].sentence).equals(s);
            if (find) {
                n = i;
            }
        }
        if(n==-1){
            n=find(s);
            if(n==-1){
                return null;
            }else{
                now.addSequel(n);
                next(s,now);
            }
        }
        else{
            return graph[now.sequel[n]];
        }
        return null;
    }

}
