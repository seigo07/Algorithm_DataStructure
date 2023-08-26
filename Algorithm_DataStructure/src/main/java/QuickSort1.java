/*
このプログラムはこのままでは実行できません。
第5回演習課題に対応する空欄を埋めてから実行してください。
*/

import java.util.Arrays;

//クイックソート（再帰版）
class QuickSort1{
    private static int numSwaps = 0; // 交換回数をカウントする変数
    //配列a[l]～a[r]を分割する。枢軸の添え字を返す
    private static int partition(int[] a, int l, int r){
        int i = l - 1;  // ポインタiの初期化
        int j = r;      // ポインタjの初期化
        int pivot = a[r];   //一番右端の要素を枢軸にする

        while(true) {// ポインタiとjがぶつかるまで繰り返す
            while(a[++i]<pivot);// ポインタiを右へ進める
            while(i<--j && pivot<a[j]);// ポインタjを左へ進める
            if(i>=j)// ポインタiとjがぶつかったらループを抜ける
                break;
            // iの指す要素とjの指す要素を交換する
            int temp = a[i]; a[i] = a[j]; a[j] = temp;
            numSwaps++; // 交換回数を増やす
        }
        // a[i]と枢軸を交換する
        int temp = a[i]; a[i] = a[r]; a[r] = temp;
        numSwaps++; // 交換回数を増やす
        return i;
    }

    //実際にクイックソートを行う
    private static void quickSort(int[] a, int l, int r){
        int v;      //枢軸
        if(l>=r) // 整列する要素が1つなら，何もしないで戻る
            return;
        // 空欄①枢軸vを基準に分割する
        // 空欄②左の部分配列a[l]～a[v-1]を整列する
        // 空欄③右の部分配列a[v+1]～a[r]を整列する
        v = partition(a, l, r); // 空欄① 枢軸vを基準に分割する
        quickSort(a, l, v - 1); // 空欄② 左の部分配列a[l]～a[v-1]を整列する
        quickSort(a, v + 1, r); // 空欄③ 右の部分配列a[v+1]～a[r]を整列する
    }

    //クイックソートによって配列を整列する
    public static void sort(int[] a){
        numSwaps = 0; // 交換回数をリセット
        quickSort(a, 0, a.length - 1);
    }
    
	//テスト用メインルーチン
    public static void main(String args[]){
        //int[] array = {55, 3, 74, 20, 13, 87, 46, 30}; //講義資料p.39のデータ列
        int[] array = {20, 30, 55, 74, 3, 45, 13, 6}; //第5回演習課題2のデータ列
        QuickSort1.sort(array);
        System.out.println( Arrays.toString(array));
        System.out.println("交換回数: " + numSwaps);
    }
}

