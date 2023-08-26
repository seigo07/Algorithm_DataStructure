import java.util.Arrays;

class ShellSort{
    public static void sort(int[] a){
        int  n = a.length;
        int compare = 0, exchange = 0, process = 0;  //比較と交換の回数をカウントする
        int  h = 1, ht, h_div = 3;

        for(ht=1; ht<n/9; ht=ht*3+1){
            h = ht;
        }
        //第4回課題の順でhソートを実行する場合は次の行のコメントアウトを外してください
        h = 4; h_div = 2;

        while(h>0){
            for (int i=h; i<n; i++) {
                int j = i;
                while (j>=h && a[j-h] > a[j]) {
                    int temp = a[j];
                    a[j]     = a[j-h];
                    a[j-h]   = temp;
                    j -= h;
                    exchange++;     //交換回数を1つ増やす
                }
            }
            compare++;  //比較回数を1つ増やす
            h /= h_div;
        }
        System.out.println("比較:" + compare + "回、交換:" + exchange + "回");
        System.out.println("process:" + process + "回");
    }

    public static void main(String[] args){
//        int[] array = {20, 30, 55, 74, 3, 45, 13, 6};
        int[] array = {8,3,5,2,1,9,6,4,7};

        ShellSort.sort(array);  //ソートを実行
        System.out.println( Arrays.toString(array));
    }
}
