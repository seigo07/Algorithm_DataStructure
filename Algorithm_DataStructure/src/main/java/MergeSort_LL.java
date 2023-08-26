//連結リストのセル
class MergeSortCell {
    MergeSortCell next;
    int data;//Object data;
    
    MergeSortCell(int data){//Cell(Object data){
        next = null;
        this.data = data;
    }
}

//連結リスト
class MyLinkedList{
    final MergeSortCell header;
    
    public MyLinkedList(){
        //header = new Cell("--List Head--");
        header = new MergeSortCell(0);
    }
    
    public void insert(int num){
        
        MergeSortCell newCell = new MergeSortCell(num);
        newCell.next = header.next;
        header.next = newCell;
    }
    
    public String toString(){
        String s = "[";
        for(MergeSortCell p = header.next; p!=null; p=p.next){
            s += p.data + " ";
        }
        s += "]";
        return s;
    }
}

//連結リスト版マージソート
class MergeSort_LL{

    //2つの連結リストaとbをマージする
    //マージして得られた連結リストの先頭要素へのリンクを返す
    private static MergeSortCell mergeList(MergeSortCell a, MergeSortCell b){
        //変数headはマージ済み連結リストの先頭にあるダミーのセルを指す
        MergeSortCell head = new MergeSortCell(0);
        //リンクpがダミーのセルを指すようにしておく
        MergeSortCell p = head;
        // 連結リストa，bのいずれかが空になるまで繰り返す
        while(a!=null && b!=null){
            //連結リストaとbの先頭の要素を比較する
            if(a.data<=b.data){
                //連結リストaの先頭の要素を取り除いて、マージ済み連結リストの末尾に連結する
                p.next = a;
                p = a;
                a = a.next;
            }else{
                //連結リストbの先頭の要素を取り除いて，マージ済み連結リストの末尾に連結する
                p.next = b;
                p = b;
                b = b.next;
            }
        }

        // 残っている要素をマージ済み連結リストの最後尾に連結する
        if(a==null) {
            p.next = b;
        }else{
            p.next = a;
        }

        // マージ済みの連結リストを戻り値として返す
        return head.next;
    }

    //連結リストxを整列する
    //整列された連結リストの先頭要素へのリンクを返す
    public static MergeSortCell mergeSortList(MergeSortCell x){
        //連結リストの要素がまったく無いか、1つしかないときはそのまま戻る
        if(x==null || x.next==null){
            return x;
        }

        // 連結リストをスキャンする変数を初期化する
        // aは1番目の要素を指す
        MergeSortCell a = x;

        // bは3番目の要素（もし連結リストの長さが2のときは2番目の要素）を指す
        MergeSortCell b = x.next;
        if(b!=null){
            b = b.next;
        }

        // 変数bが連結リストの末尾に到達するまで，変数aを1つ進め，
        // 変数bを2つ進める。変数bが末尾に到達したとき，変数aは
        // 連結リストのほぼ中央の要素を指しているはずである
        while(b!=null) {
            a = a.next;
            b = b.next;
            if(b!=null){
                b = b.next;
            }
        }

        // 連結リストを，変数aが指す要素の直後で2つに分割する
        // 変数pは，後半の連結リストの先頭の要素を指す
        MergeSortCell p = a.next;
        a.next = null;

        // 分割した連結リストをそれぞれ個別に整列して，その結果をマージする
        return mergeList(mergeSortList(x), mergeSortList(p));
    }
    
    //テスト用メインルーチン
    public static void main(String args[]){
        MyLinkedList list = new MyLinkedList();
        
        System.out.println("連結リストへのデータ挿入");
        System.out.println(list);
        list.insert(55);     System.out.println(list);
        list.insert(13);     System.out.println(list);
        list.insert(3);      System.out.println(list);
        list.insert(45);     System.out.println(list);
        list.insert(74);     System.out.println(list);
        list.insert(87);     System.out.println(list);
        list.insert(46);     System.out.println(list);
        list.insert(30);     System.out.println(list);
        
       
        MergeSort_LL.mergeSortList(list.header);
        System.out.println("連結リスト版マージソートの実行");
        System.out.println(list);
    }
    
    

}