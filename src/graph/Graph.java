package graph;

import java.util.*;

public class Graph {

    //максимальное количество вершин в графе
    private int vertexMax;

    public int[][] getMatrix() {
        return matrix;
    }

    //массив для хранения вершин
    private int[][] matrix;

    private ArrayList<int[]> tours = new ArrayList<>();
    private int minimalPath;

    public Graph(int verMax)
    {
        vertexMax = verMax;
        matrix = new int[vertexMax][vertexMax];
        //перед началом работы заполняем матрицу весами
        for(int i = 0; i < vertexMax; i++){
            for(int j = i; j < vertexMax; j++){

                matrix[i][j] = (int) (Math.random()*20+1);
                matrix[j][i] =matrix[i][j];
                if (i==j)
                    matrix[i][j] =0;
            }
        }
    }

    public void show(){
        for(int i = 0; i < vertexMax; i++){
            for(int j = i; j < vertexMax; j++){
                matrix[i][j] = (int) (Math.random()*20+1);
                matrix[j][i] =matrix[i][j];
                if (i==j)
                    matrix[i][j] =0;
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }
    public int BranchAndBound(){
        int L = 0;
        for(int i = 0; i < vertexMax; i++)
            for(int j = i; j < vertexMax; j++)
                if (i==j&&i!= vertexMax -1)
                    L+=matrix[i][j+1];
        L+=matrix[0][vertexMax -1];
        System.out.println(" L: "+L);
        minimalPath=L;
        ArrayList<Integer> path = new ArrayList<>();
        path.add(0);
        branchAndBound(path,0,L/2);
        System.out.println( " minimalPath: " + minimalPath);
        return minimalPath;
    }


    private void branchAndBound (ArrayList<Integer> path, int Length, int min){
        if(path.size()== vertexMax &&min>Length) {
            Length+=matrix[path.get(path.size()-1)][0];
            if(minimalPath>Length)
                minimalPath=Length;
        }
        if(min>Length)
            for(int i = 1; i< vertexMax; i++){
                if(!path.contains(i)){
                    path.add(i);
                    branchAndBound(path,Length+matrix[path.get(path.size()-2)][path.get(path.size()-1)],min);
                    path.remove(path.indexOf(i));
                }
            }

    }

    public int mstPrim() {
        int L = 0;
        for(int i = 0; i < vertexMax; i++)
            for(int j = i; j < vertexMax; j++)
                if (i==j&&i!=vertexMax-1)
                    L+=matrix[i][j+1];
        L+=matrix[0][vertexMax-1];
        System.out.println(L);
        minimalPath=L;
        int INF = Integer.MAX_VALUE / 2;
        boolean[] used = new boolean [vertexMax]; // массив пометок
        int[] dist = new int [vertexMax]; // массив расстояния. dist[v] = вес_ребра(MST, v)

        Arrays.fill(dist, INF); // устанаавливаем расстояние до всех вершин INF
        dist[0] = 0; // для начальной вершины положим 0
        ArrayList<Integer> path = new ArrayList<>();
        for (;;) {
            int v = -1;
            for (int nv = 0; nv < vertexMax; nv++) // перебираем вершины
                if (!used[nv] && dist[nv] < INF && (v == -1 || dist[v] > dist[nv])) // выбираем самую близкую непомеченную вершину
                    v = nv;
            if (v == -1) break; // ближайшая вершина не найдена
            used[v] = true; // помечаем ее
            int minnv=0;
            for (int nv = 0; nv < vertexMax; nv++)
                if (!used[nv] && matrix[v][nv] < INF) { // для всех непомеченных смежных
                    if(dist[nv]>=matrix[v][nv]) {
                        minnv = nv;
                        dist[nv] = matrix[v][nv]; // улучшаем оценку расстояния (релаксация)
                    }
                }
            path.add(v);
            path.add(minnv);
        }
        int ret = 0; // вес MST
        // add elements to al, including duplicates
        for(int a=0;a<path.size();a++){
            for(int b=a+1;b<path.size();b++){
                if(path.get(a).equals(path.get(b))){
                    path.remove(b);
                    b--;
                }
            }
        }
        int PL=0;
        for(int i =1;i<path.size();i++){
            PL+=matrix[path.get(i-1)][path.get(i)];
        }
        PL+=matrix[path.get(path.size()-1)][0];

        for(int item: path){
            System.out.print(item +" ");
        }
        for (int v = 0; v < vertexMax; v++)
            ret += dist[v];
        System.out.println( " tour: "+PL);
        return PL;
    }


    public void Greed(){
        int L = 0;
        for(int i = 0; i < vertexMax; i++)
            for(int j = i; j < vertexMax; j++)
                if (i==j&&i!= vertexMax -1)
                    L+=matrix[i][j+1];
        L+=matrix[0][vertexMax -1];
        System.out.println(" minimalPath:" + + L);

        int[] pa=new int[vertexMax -1];
        for(int i = 1; i< vertexMax; i++)
            pa[i-1]=i;
        prmt(pa,0,0);
        int min=L;
        for(int[] temp: tours){
            int tempTour = matrix[0][temp[0]];
            for (int i = 0; i< vertexMax -2; i++){

                    tempTour+=matrix[temp[i]][temp[i+1]];
            }
            tempTour+=matrix[temp[vertexMax -2]][0];
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
