//線形探索法のプログラム
public class LinearSearch{
    //テーブルのエントリ
    static private class Entry{
        int key; // 比較の対象となるキー
        Object data; // それ以外の情報
        
        //エントリを生成する
        private Entry(int key, Object data){
            this.key = key;
            this.data = data;
        }
    }
    
    final static int MAX = 100; // データの最大個数
    Entry[] table = new Entry[MAX]; // データを格納する配列
    int n = 0; // table に登録されているデータの個数

    //データを登録する
    //key: キー、data: キーkey に対応するデータ
    public void add(int key, Object data){
        if (n >= MAX) {
            throw new IllegalStateException("データの個数が多すぎます");
        }
        table[n++] = new Entry(key, data);
    }
    
    //キーkey に対応するデータを探す
    //key: キー
    public Object search(int key){
        int i = 0;                      //（1）
        while (i < n) {                 //（2）
            if (table[i].key == key)    //（3）
                return (table[i].data); //（4）見つかった
            i++;                        //（5）
        }
        return null;                    //（6）見つからなかった
    }
    
    //テスト用メインルーチン
    public static void main(String[] args){
        LinearSearch table = new LinearSearch(); //テーブルを登録する
        //データを登録する
        table.add(1,  "one");
        table.add(10, "ten");
        table.add(2,  "two");
        table.add(8,  "eight");
        table.add(6,  "six");
        table.add(5,  "five");
        table.add(9,  "nine");
        table.add(4,  "four");
        table.add(3,  "three");
        table.add(7,  "seven");
        
        String x;
        x = (String)table.search(2); //キーが「2」であるデータを探索する
        if (x != null){
	        System.out.println("value=" + x);
        }else{
	        System.out.println("Not found");
        }
    }
}
