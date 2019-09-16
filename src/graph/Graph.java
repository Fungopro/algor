package graph;

import java.util.*;

public class Graph {

    //максимальное количество вершин в графе
    private final int VERTEX_MAX = 10;

    public int[][] getMatrix() {
        return matrix;
    }

    //массив для хранения вершин
    private int[][] matrix;

    private ArrayList<int[]> tours = new ArrayList<>();
    private int minimalPath;

    public Graph()
    {
        matrix = new int[VERTEX_MAX][VERTEX_MAX];
        //перед началом работы заполняем матрицу весами
        for(int i = 0; i < VERTEX_MAX; i++){
            for(int j = i; j < VERTEX_MAX; j++){
                matrix[i][j] = (int) (Math.random()*20+1);
                matrix[j][i] =matrix[i][j];
                if (i==j)
                    matrix[i][j] =0;
            }
        }
    }

    public void show(){
        for(int i = 0; i < VERTEX_MAX; i++){
            for(int j = i; j < VERTEX_MAX; j++){
                matrix[i][j] = (int) (Math.random()*20+1);
                matrix[j][i] =matrix[i][j];
                if (i==j)
                    matrix[i][j] =0;
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }
    public void BranchAndBound(){
        int L = 0;
        for(int i = 0; i < VERTEX_MAX; i++)
            for(int j = i; j < VERTEX_MAX; j++)
                if (i==j&&i!=VERTEX_MAX-1)
                    L+=matrix[i][j+1];
        L+=matrix[0][VERTEX_MAX-1];
        System.out.println(L);
        minimalPath=L;
        ArrayList<Integer> path = new ArrayList<>();
        path.add(0);
        branchAndBound(path,0,L/2);
        System.out.print(minimalPath);

    }


    private void branchAndBound (ArrayList<Integer> path, int Length, int min){
        if(path.size()==VERTEX_MAX&&min>Length) {
            Length+=matrix[path.get(path.size()-1)][0];
            if(minimalPath>Length)
                minimalPath=Length;
        }
        if(min>Length)
            for(int i=1;i<VERTEX_MAX;i++){
                if(!path.contains(i)){
                    path.add(i);
                    branchAndBound(path,Length+matrix[path.get(path.size()-2)][path.get(path.size()-1)],min);
                    path.remove(path.indexOf(i));
                }
            }

    }

    public void Greed(){
        int L = 0;
        for(int i = 0; i < VERTEX_MAX; i++)
            for(int j = i; j < VERTEX_MAX; j++)
                if (i==j&&i!=VERTEX_MAX-1)
                    L+=matrix[i][j+1];
        L+=matrix[0][VERTEX_MAX-1];
        System.out.println(L);

        int[] pa=new int[VERTEX_MAX-1];
        for(int i=1; i<VERTEX_MAX;i++)
            pa[i-1]=i;
        prmt(pa,0,0);
        int min=L;
        for(int[] temp: tours){
            int tempTour = matrix[0][temp[0]];
            for (int i=0;i<VERTEX_MAX-2;i++){
                if(L<tempTour)
                    break;
                else
                    tempTour+=matrix[temp[i]][temp[i+1]];
            }
            tempTour+=matrix[temp[VERTEX_MAX-2]][0];
            if(min<=tempTour){
                continue;
            }
            if(min>tempTour){
                min=tempTour;}
            for(int i : temp)
                System.out.print(i+" ");
            System.out.println(tempTour);
        }
    }

    private void prmt(int[] pa, int i,int tourLength) {
        if (i == pa.length - 1) {
            tours.add(pa.clone());
        } else {
            for (int j = i; j < pa.length; j++) {
                aswap(pa, i, j);
                prmt(pa, i + 1,tourLength);
                aswap(pa, i, j);
            }
        }
    }

    private static void aswap(int[] pa, int i, int j) {
        int k = pa[i];
        pa[i] = pa[j];
        pa[j] = k;
    }
}
