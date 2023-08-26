//バブルソート
class BubbleSort{
    public static void sort(int[] a){
        int n = a.length;
        int compare = 0, exchange = 0;  //比較と交換の回数をカウントする
        //「compare++;」と「exchange++;」をソートメソッド内の適当な位置に追記すること
        
        System.out.print("バブルソート前　 : [ ");
        for(int k=0; k<n; k++){
            System.out.printf("%3d   ", a[k]);
        }
        System.out.println("]");
        
        for(int i=0; i<n-1; i++){
            for(int j=n-1; j>i; j--){
                if(a[j-1]>a[j]){
                    int temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
                    exchange++;     //交換回数を1つ増やす
                }
            }
            compare++;  //比較回数を1つ増やす
            System.out.print((i+1) + "回目のスキャン後: [ ");
            for(int k=0; k<n; k++){
                if(i==k){
                    System.out.printf("%3d | ", a[k]);
                }else{
                    System.out.printf("%3d   ", a[k]);
                }
            }
            System.out.println("]");
        }
        System.out.println("比較:" + compare + "回、交換:" + exchange + "回");
    }
}

//選択ソート
class SelectionSort{
    public static void sort(int[] a){
        int n = a.length;
        int compare = 0, exchange = 0;  //比較と交換の回数をカウントする
        
        System.out.print("選択ソート前　　 : [ ");
        for(int k=0; k<n; k++){
            System.out.printf("%3d   ", a[k]);
        }
        System.out.println("]");
        
        for(int i=0; i<n-1; i++){
            int lowest = i;
            int lowkey = a[i];
            for(int j=i+1; j<n; j++){
                if(a[j]<lowkey){
                    lowest = j;
                    lowkey = a[j];
                }
                compare++;  //比較回数を1つ増やす
            }
            int temp = a[i];
            a[i] = a[lowest];
            a[lowest] = temp;
            exchange++;     //交換回数を1つ増やす
            
            System.out.print((i+1) + "回目のスキャン後: [ ");
            for(int k=0; k<n; k++){
                if(i==k){
                    System.out.printf("%3d | ", a[k]);
                }else{
                    System.out.printf("%3d   ", a[k]);
                }
            }
            System.out.println("]");
        }
        System.out.println("比較:" + compare + "回、交換:" + exchange + "回");
    }
}

//挿入ソート
class InsertionSort{
    public static void sort(int[] a){
        int n = a.length;
        int compare = 0, exchange = 0;  //比較と交換の回数をカウントする
        
        System.out.printf("挿入ソート前　　 : [ %3d | ", a[0]);
        for(int k=1; k<n; k++){
            System.out.printf("%3d   ", a[k]);
        }
        System.out.println("]");
        
        for(int i=1; i<n; i++){
            int j = i;
            while(j>=1 && a[j-1]>a[j]){
                int temp = a[j];
                a[j] = a[j-1];
                a[j-1] = temp;
                j--;
                compare++;  //比較回数を1つ増やす
                exchange++; //交換回数を1つ増やす
                
            }
            //比較はしたが、交換の対象ではなかったとき
            compare++;  //比較回数を1つ増やす
            System.out.print((i) + "回目のスキャン後: [ ");
            for(int k=0; k<n; k++){
                if(i==k){
                    System.out.printf("%3d | ", a[k]);
                }else{
                    System.out.printf("%3d   ", a[k]);
                }
            }
            System.out.println("]");
        }
        System.out.println("比較:" + compare + "回、交換:" + exchange + "回");
    }
}



public class SimpleSort{
    
    public static void main(String[] args){
        int[] array = {20, 6, 55, 74, 3, 45, 13, 87, 46, 30};
        
        //ソートを実行
        BubbleSort.sort(array);
        SelectionSort.sort(array);
        InsertionSort.sort(array);

    }
}
