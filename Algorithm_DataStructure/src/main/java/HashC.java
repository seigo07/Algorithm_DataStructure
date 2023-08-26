/*
チェイン法によるハッシュのプログラム
このプログラムは第7回演習課題に対応しています。
空欄を含んでいますので、このままでは動きません。
*/
public class HashC{
    private static class Cell{
        String   key; //キー（文字列）
        Object  data; //データ
        Cell    next; //次のセル
        
        private Cell(String key, Object data){
            this.key = key;
            this.data = data;
        }
    }
    
    Cell[] table;   //ハッシュ表の実体
    int bucketSize; //バケットの個数
    int nElements;  //登録されているデータの個数
    
    //ハッシュ表を生成する
    public HashC(int bucketSize){ //bucketSize：バケットの個数
        //ハッシュ表の実体の配列を割り当てる
        table = new Cell[bucketSize];
        //ハッシュ表のバケットの個数を記録しておく
        this.bucketSize = bucketSize;
        //要素の個数を0にしておく
        nElements = 0;
    }
    
    //ハッシュ値を求めるハッシュ関数
    private int hash(String key){ //key：キー
        int sum = 0;
        //文字列に含まれるすべての文字コードの和を求める    
        for(int i=0; i<key.length(); i++){
            sum += (int)key.charAt(i);
        }
        //キーのハッシュ値を返す
        return sum % 10;
    }
    
    //ハッシュ表を探索する
    public Object find(String key){ //ky：探すべきキー
        for(Cell p=table[hash(key)]; p!=null; p=p.next){
            if(key.equals(p.key)){
                //一致するキーが見つかった場合、そのデータを返す
                return p.data; 
            }
        }
        //一致するキーが見つからなかった場合、nullを返す
        return null; 
    }
    
    //ハッシュ表にデータを挿入する
    public boolean insert(String key, Object data){ //key：キー、data：登録するデータ
        if(find(key)!=null){
            //すでに同じキーを持つデータが存在していたらfalseを返す（登録失敗）
            return false;
        }
        //新しいセルを作成し、対応するバケットに連なる連結リストの先頭に挿入する
        Cell p = new Cell(key, data);
        int h = hash(key);
        p.next = table[h];
        table[h] = p;

        nElements++; //要素数を1つ増やす
        return true; //trueを返す（登録成功）
    }
    
    //ハッシュ表からデータを削除する
    public boolean delete(String key){ //key：削除すべきデータのキー
        int h = hash(key);
        
        if(find(key)==null){
            //そのバケットが空ならfalseをを返す（削除失敗）
            return false;
        }
        
        //そのバケットに連なる連結リストを先頭から順番に確認していく
        //連結リストの先頭が削除すべきデータだった場合の削除処理
        if(key.equals(table[h].key)){
            Cell p = table[h];
            table[h] = p.next;
            nElements--; //要素数を1つ減らす
            return true; //trueを返す（削除成功）
        }

        Cell p; //現在のセルを記録しておくための変数
        Cell q; //1つ前のセルを記録しておくための変数
        //連結リストの2番目以降のセルについて順番に確認する
        for(q=table[h], p=q.next; p!=null; q=p, p=p.next){
            //削除すべきデータのキーと現在のセルのキーが一致したらデータを削除する
            if(key.equals(p.key)){
                q.next = p.next;
                nElements--; //要素数を1つ減らす
                return true; //trueを返す（削除成功）
            }
        }
        //連結リストをすべて探索しても一致するキーが見つからなかった場合
        return false; //falseを返す（削除失敗）
    }
    
    //ハッシュ表の内容を表す文字列を作成する
    public String toString(){
        String str = "";
        
        //すべてのバケットを順番に処理する
        for(int i=0; i<table.length; i++){
            str += "バケット " + i + ":";
            //このバケットの内容を文字列sに追加する
            for(Cell p=table[i]; p!=null; p=p.next){
                str += "[" + p.key + ":" + p.data + "]";
            }
            str += "\n";
        }
        //最後に、登録されている要素の個数を追加する
        str += "要素の個数:" + nElements + "\n";
        //ハッシュ表の内容を表す文字列を返す
        return str; 
    }
    
    //テスト用メインルーチン
    public static void main(String args[]){
        //ハッシュ表を生成する（バケットの個数：10）
        HashC table = new HashC(10);

        //問1のキーの順番でデータを登録する
        //データ本体は例として漢数字を登録している
        table.insert("iti", "一");
        table.insert("ni", "二");
        table.insert("san", "三");
        table.insert("yon", "四");
        table.insert("go", "五");
        table.insert("roku", "六");
        table.insert("nana", "七");
        table.insert("hati", "八");
        table.insert("kyu", "九");
        table.insert("ju", "十");
        table.insert("juiti", "十一");
        table.insert("juni", "十二");

        //ハッシュ表の中身を表示する
        System.out.println(table); // 問題1の答え

    }
}
