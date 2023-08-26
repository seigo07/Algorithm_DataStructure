public class HashOA {

    //バケットの状態を表す列挙型の定数を設定 {データ格納, 空, 削除済み}
    private enum Status {OCCUPIED, EMPTY, DELETED};
    private static class Bucket{ String key; //キー(文字列)
        Object data; //対応するデータ
        Status status; //状態
        private Bucket(String key, Object data, Status status){ this.key = key;
            this.data = data;
            this.status = status;
        }
        //データ登録用のメソッド
        void set(String key, Object data, Status status) {
            this.key = key; this.data = data; this.status = status;
        } }
    Bucket[] table;
    int bucketSize;
    int nElements;
    int nRehash;
    int[] Sequence;
    //ハッシュ表の実体 //バケットの個数 //登録されているデータの個数 //再ハッシュの合計回数 //再ハッシュの数列
    //ハッシュ表を生成する //bucketSize:バケットの個数、Sequence:再ハッシュの数列
    public HashOA(int bucketSize, int[] Sequence){
        //ハッシュ表の実体の配列を割り当てて、 //すべての要素を Status.EMPTY で初期化しておく //key と data は null で初期化しておく
        table = new Bucket[bucketSize];
        for(int i=0; i<bucketSize; i++){
                table[i] = new Bucket(null, null, Status.EMPTY);
            }
        //ハッシュ表のバケットの個数を格納しておく
        this.bucketSize = bucketSize; //再ハッシュ用の数列を格納しておく、数列を使用しない場合は null
        this.Sequence = Sequence;
        nElements=0; //要素の個数を0にしておく
        nRehash = 0; //再ハッシュの回数を 0 にしておく
    }

    //ハッシュ値を求める
    private int hash(String key){ //key:キー
        //文字列に含まれるすべての文字コードの和を求める
        for(int i=0; i<key.length(); i++){
            sum += (int)key.charAt(i);
        }
        return sum % bucketSize; //与えられたキーに対するハッシュ値を返す
    }
    int sum = 0;
    //再ハッシュを行う
    private int rehash(int h, int k){ //h:ハッシュ値、k:再ハッシュの回数
        nRehash++; //再ハッシュの合計回数を 1 つ増やす
        // 数列が与えらえていない場合の再ハッシュ値
        // 隣のバケットを順に調べていく
        if(Sequence == null){
            return (h + k) % bucketSize;
        }
        //数列が与えられていた場合の再ハッシュ値
        //数列に従ってバケットを調べていく
        return (h + this.Sequence[k]) % bucketSize;
    }
    //ハッシュ表を探索する
    public Object find(String key){ //key:探索したいキー
        int count = 0;
        int h0; //h0:与えられたキーのハッシュ値を格納
        int hk; //hk:k 回目の再ハッシュ値を格納(k は count で与えられる)
        h0 = hk = hash(key); //初回のハッシュ値を求める
        //空のバケットに当たるまで探索する
        while(table[hk].status != Status.EMPTY){
            //削除済みの場合、再ハッシュ
            if(table[hk].status != Status.DELETED && key.equals(table[hk].key)){
            //探索したいキーと一致していたらそのキーの data を返す
                return table[hk].data; }
            if(++count >= bucketSize){
                return null; //再ハッシュの回数がバケットの個数を超えたら探索失敗
            }
            hk = rehash(h0, count); //k 回目の再ハッシュ
        }
        //空のバケットに当たったため探索失敗
        return null;
    }

    //ハッシュ表にデータを挿入する //key:挿入したいデータのキー、data:登録するデータ
    public boolean insert(String key, Object data){
        int count = 0;
        int h0;
        int hk;
        h0 = hk = hash(key);
        //データが格納されているバケットの間、繰り返す //(空もしくは削除済みのバケットに当たるまで繰り返す)
        while(table[hk].status == Status.OCCUPIED ){
            if(key.equals(table[hk].key)){
            //すでに同じキーのデータが存在していたら登録失敗
                return false;
            }
            if(++count >= bucketSize){
                return false; //再ハッシュの回数がバケットの個数を超えたら登録失敗
            }
            hk = rehash(h0, count); //k 回目の再ハッシュ
        }
        //空もしくは削除済みのバケットが見つかったらデータを登録する
        // データの登録は set メソッドを使って登録する
        if(table[hk].status == Status.OCCUPIED){
            table[hk].set(key, data, Status.DELETED);
        } else if (table[hk].status == Status.EMPTY) {
            table[hk].set(key, data, Status.OCCUPIED);
        }
        nElements++; //要素の個数を 1 つ増やす
        return true; //登録成功
    }
    //ハッシュ表からデータを削除する
    public boolean delete(String key){ //key:削除するデータのキー
        int count = 0;
        int h0;
        int hk;
        h0 = hk = hash(key);
        //空のバケットに当たるまで繰り返す
        while(table[hk].status != Status.EMPTY){
            //削除済みの場合、再ハッシュ
            if(table[hk].status != Status.DELETED && key.equals(table[hk].key)){
                //削除したいデータのキーと一致していたら「削除済み」をセットする //key と data は null で初期化しておく
                table[hk].set(null, null, Status.DELETED);
                nElements--; //要素の個数を 1 つ減らす
                return true; //削除成功
            }
            if(++count >= bucketSize){
                return false; //再ハッシュの回数がバケットの個数を超えたら削除失敗
            }
            hk = rehash(h0, count); //k 回目の再ハッシュ
        }
        //空のバケットに当たったため削除失敗
        return false;
    }

    //ハッシュ表の内容を表す文字列を作成する
    public String toString(){
        String str = "";
        //すべてのバケットを順番に処理する
        for(int i=0; i<table.length; i++){
            str += "バケット " + i + ":"; //バケットの中身が、空なら「空」、削除済みなら「削除済み」と出力する
            if(table[i].status == Status.EMPTY){
            str += "空¥n";
        } else if(table[i].status == Status.DELETED){
            str += "削除済み¥n";
        }else{
            //データが格納されていればそのデータの key と data を出力する
            str += "[" + table[i].key + ":" + table[i].data + "]¥n";
        }
        }
        //最後に、登録されている要素の個数と再ハッシュの回数を追加する
        str += "要素の個数:" + nElements + "¥n";
        str += "再ハッシュの回数:" + nRehash + "¥n";
        return str;
    }

    //テスト用メインルーチン
    public static void main(String args[]) {
        //ハッシュ表を生成する(バケットの個数:10)
        // 再ハッシュのときは隣のバケットを順番に調べていく
        HashOA table1 = new HashOA(10, null);
        table1.insert("one", 1);
        table1.insert("two", 2);
        table1.insert("three", 3);
        table1.insert("four", 4);
        table1.insert("five", 5);
        table1.insert("six", 6);
        table1.insert("seven", 7);
        table1.insert("eight", 8);
        table1.insert("nine", 9);
        table1.insert("ten", 10);
        System.out.println(table1); //問題 1-1 の答えを出力

        //再ハッシュで使う数列を生成する
        int[] sequence = {3, 2, 4, 7, 1, 9, 8, 6, 5};
        //ハッシュ表を生成する(バケットの個数:10)
        // 再ハッシュのときは数列に従って調べていく
        HashOA table2 = new HashOA(10, sequence);

        table2.insert("one", 1);
        table2.insert("two", 2);
        table2.insert("three", 3);
        table2.insert("four", 4);
        table2.insert("five", 5);
        table2.insert("six", 6);
        table2.insert("seven", 7);
        table2.insert("eight", 8);
        table2.insert("nine", 9);
        table2.insert("ten", 10);
        System.out.println(table2); //問題 1-2 の答えを出力
    }
}
