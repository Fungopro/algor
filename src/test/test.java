package test;

import graph.Graph;
import org.junit.Test;
import search.Mylist;

import java.io.IOException;


public class test {
    Graph graph = new Graph(10);
    @Test
    public void testBranchAndBound(){
        graph.show();
        long time = System.currentTimeMillis();
        double A = graph.BranchAndBound();
        System.out.println(" BranchAndBound time: " +(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        graph.Greed();
        System.out.println(" Greed time: " +(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        double a =graph.mstPrim();
        System.out.println(" mstPrim time: " +(System.currentTimeMillis()-time));
        System.out.println("Относительная погрешность: " + Math.abs(A-a)/A);
    }

    @Test
    public void search() throws IOException {
        Mylist myList = new Mylist(30);
        System.out.println("Input: ");
        int toFind = System.in.read();
        myList.indexSearch(toFind);
        System.out.println("Input: ");
        toFind = System.in.read();
        myList.interpolationSearch(toFind);
    }
}