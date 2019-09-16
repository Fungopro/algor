package graph;

public class getTour {
    public static void main(String[] args) {
        int[] pa = new int[] {1, 2, 3,4};
        prmt(pa, 0);
    }

    private static void prmt(int[] pa, int i) {
        if (i == pa.length - 1) {


        } else {
            for (int j = i; j < pa.length; j++) {
                aswap(pa, i, j);
                prmt(pa, i + 1);
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
