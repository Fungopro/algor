package search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Mylist {

    private ArrayList<Integer> table = new ArrayList<>();


    public ArrayList<Integer> getTable() {
        return table;
    }

    public void setTable(ArrayList<Integer> table) {
        this.table = table;
    }



    public Mylist(int size){
        Random random = new Random();
        for (int i = 0;i<size; i++){
            table.add(random.nextInt(100));
        }
        Collections.sort(table);
        for (int i = 0;i<size; i++){
            System.out.println(" item: "+table.get(i));
        }
    }


    public int interpolationSearch(int toFind) {
        // Возвращает индекс элемента со значением toFind или -1, если такого элемента не существует
        int mid;
        int low = 0;
        int high = table.size() - 1;
        int iter = 0;

        while (table.get(low) < toFind && table.get(high) > toFind) {
            mid = low + ((toFind - table.get(low)) * (high - low)) / (table.get(high) - table.get(low));
            System.out.println("mid: "+ mid +" low: " + low +" high: " + high);
            iter++;
            //System.out.println(" low: " + table.get(low) + " high: " + table.get(high) + " mid:  "+table.get(mid) +" toFind: "+toFind);
            System.out.println(" low: " + table.get(low) + " high: " + table.get(high) + " mid:  "+table.get(mid) +" toFind: "+toFind);
            if (table.get(mid) < toFind) {
                System.out.println("low = mid + 1");
                low = mid + 1;
            }
            else if (table.get(mid) > toFind){
                System.out.println("high = mid - 1");
                high = mid - 1;
            }else  {
                System.out.println(" item: " + table.get(mid)+" iter: " + iter);
                return mid;
            }
            System.out.println(table.get(low)+" < "+ toFind+" : " + (table.get(mid) < toFind) +" "+ table.get(low)+" < "+ toFind+" : " + (table.get(mid) > toFind));
        }



        if (table.get(low) == toFind) {
            System.out.println(" item: " + table.get(low)+" iter: " + iter);
            return low;
        }
        if (table.get(high) == toFind) {
            System.out.println(" item: " + table.get(high)+" iter: " + iter);
            return high;
        }
        System.out.println("Я тупой ничего не нашел");
        return -1; // Not found
    }


    public int indexSearch (int toFind){
        int i=0;
        int j=0;
        ArrayList<Integer> kIndex = new ArrayList<>(); // массив ключей индексной таблицы
        ArrayList<Integer> pIndex = new ArrayList<>(); // массив индексов индексной таблицы

        // Формирование индексной таблицы
        for (i = 0, j = 0; i < table.size(); i = i + 8, j++)
        {
            kIndex.add(table.get(i)); // переносим каждый 8-й ключ в индексную таблицу
            pIndex.add(i);    // запоминаем текущий индекс
        }
        pIndex.add(table.size());
        for (j = 0; j < kIndex.size()-1; j++)
        {
            if (toFind < kIndex.get(j)) // если находим ключ меньше введенного,
                break;           // выходим из цикла - мы нашли нужную область основной таблицы
        }
        if (j == 0)
            i = 0;      // присваиваем i начальный индекс диапазона поиска в основной таблице
        else
            i = pIndex.get(j - 1);
        for (; i < pIndex.get(j); i++) // осуществляем поиск в основной таблице
        {                         //до следующего индекса индексной таблицы
            if (table.get(i) == toFind)  // если найдено введенное значение, выводим его
                System.out.println(" item: "+table.get(i));
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        Mylist myList = new Mylist(30);
        Scanner in = new Scanner(System.in);
        System.out.println("Input: ");
        int toFind = in.nextInt();
        //System.out.println("toFind: "+ toFind);
        myList.indexSearch(toFind);
        //toFind = in.nextInt();
        //myList.interpolationSearch(toFind);
    }
}
