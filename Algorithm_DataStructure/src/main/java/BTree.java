import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BTree{
    private abstract class Node{
        int serial; //シリアル番号
    }
    
    //B木の内部節
    private class InternalNode extends Node{
        int nChilds;    //この節が持っている子の数
        Node[] child;   //部分木
        Integer[] low;  //各部分木の最小の要素
        
        //空の内部節を生成する（コンストラクタ）
        private InternalNode(){
            serial = serialNumber++;    //シリアル番号を付ける
            nChilds = 0;
            child = new Node[MAX_CHILD];
            low = new Integer[MAX_CHILD];
        }
        
        //キーkeyをもつデータは何番目の部分木に入るかを調べる
        private int locateSubtree(Integer key){
            for(int i=nChilds-1; i>0; i--){
                if(key.compareTo(low[i])>=0){
                    return i;
                }
            }
            return 0;
        }
    }
    
    //B木の葉
    private class Leaf extends Node{
        Integer key;
        Object data;
        
        //葉を生成する（コンストラクタ）
        private Leaf(Integer key, Object data){
            serial = serialNumber++;    //シリアル番号を付ける
            this.key = key;
            this.data = data;
        }
    }
    
    private Node root;  //B木の根
    private int serialNumber = 0;   //Nodeに付けるシリアル番号
    
    //searchメソッドは探索に成功するとその葉をcurrentLeafフィールドにセットする
    //deleteメソッドとinsertメソッドを呼び出すとこの変数はクリアされる
    private Leaf currentLeaf;

    final private static int MAX_CHILD = 5; //5分木
    final private static int HALF_CHILD = ((MAX_CHILD+1)/2);
    
    //空のB木を生成する（コンストラクタ）
    public BTree(){
        root = null;
    }
    
    //B木からキーを探索する
    //キーkeyをもつ葉が見つかったらcurrentLeafフィールドにセットする
    public boolean search(Integer key){
        currentLeaf = null;
        if(root==null){
            return false;
        }else{
            //根から始めて、葉にたどりつくまで内部節をたどる
            Node p = root;
            while(p instanceof InternalNode){
                InternalNode node = (InternalNode)p;
                p = node.child[node.locateSubtree(key)];
            }
            
            //与えられたキーと葉にセットされているキーを比較する
            Leaf leaf = (Leaf)p;
            if(key.compareTo(leaf.key)==0){
                //探索成功！
                //この葉をcurrentLeafフィールドにセットしてtrueを返す
                currentLeaf = leaf;
                return true;
            }else{
                //探索失敗
                return false;
            }
        }
    }
    
    
    //最後に成功したsearchメソッドが見つけた要素のデータを得る
    public Object getData(){
        if(currentLeaf==null){
            return null;
        }else{
            return currentLeaf.data;
        }
    }
    
    //最後に成功したsearchメソッドが見つけた要素にデータをセットする
    public boolean setData(Object data){
        if(currentLeaf==null){
            return false;
        }else{
            currentLeaf.data = data;
            return true;
        }
    }
    
    //InsertAuxメソッドの結果
    private static class InsertAuxResult{
        Node newNode;   //新しい節を作った場合に、その節が入る
        Integer lowest; //新しい節を作った場合に、newNodeが指す部分木の最小の要素が入る
        
        private InsertAuxResult(Node newNode, Integer lowest){
            this.newNode = newNode;
            this.lowest = lowest;
        }
    }
    
    //指定した節に対して、キーkeyをもつ要素を挿入する（insertの下請け）
    //内部節pnodeのnth番目の子に対して挿入を行う
    //pnodeがnullの場合には根が対象となる
    private InsertAuxResult insertAux(InternalNode pnode, int nth, Integer key, Object data){
        //要素の挿入の対象となる節へのリンクを変数thisNodeに入れる
        Node thisNode;
        if(pnode==null){
            thisNode = root;
        }else{
            thisNode = pnode.child[nth];
        }
        
        if(thisNode instanceof Leaf){ //この節は葉である
            //これ以降、この節を葉leafとして参照する
            Leaf leaf = (Leaf)thisNode;
            //すでに登録済みであれば、何もしないでnullを返す
            if(leaf.key.compareTo(key)==0){
                return null;
            }else{
                //新たに葉newLeafを割り当てる
                Leaf newLeaf = new Leaf(key, data);
                //もし割り当てた葉newLeafの方が葉leafよりも小さいなら、
                //newLeafとleafの位置を入れ替える
                if(key.compareTo(leaf.key)<0){
                    //元の節には、割り当てた葉newLeafを入れる
                    if(pnode==null){
                        root = newLeaf;
                    }else{
                        pnode.child[nth] = newLeaf;
                    }
                    //新たに割り当てた葉として、leafを返す
                    return new InsertAuxResult(leaf, leaf.key);
                }else{
                    //新たに割り当てた葉として、newLeafを返す
                    return new InsertAuxResult(newLeaf, key);
                }
            }
            
        }else{  //この節は内部節である
            //これ以降、この節を内部節nodeとして参照する
            InternalNode node = (InternalNode)thisNode;
            //何番目の部分木に挿入するかを決める
            int pos = node.locateSubtree(key);
            //部分木に対して、自分自身を再帰呼び出しする
            InsertAuxResult result = insertAux(node, pos, key, data);
            //もし分割が行われていなければ、そのまま戻る
            if(result==null || result.newNode==null){
                return result;
            }
            //分割が行われていたので、節nodeにそれ（result.newNode）を挿入する
            //節nodeに追加の余地はあるか？
            if(node.nChilds<MAX_CHILD){
                //追加の余地があったので、適切な位置に挿入する
                for(int i=node.nChilds-1; i>pos; i--){
                    node.child[i+1] = node.child[i];
                    node.low[i+1] = node.low[i];
                }
                node.child[pos+1] = result.newNode;
                node.low[pos+1] = result.lowest;
                node.nChilds++;
                return new InsertAuxResult(null, null);
            }else{
                //追加の余地がないので、節nodeを2つに分割しなければならない
                //新しい内部節newNodeを割り当てる
                InternalNode newNode = new InternalNode();
                //節result.newNodeがどちらの節に挿入されるかで場合分けする
                if(pos<HALF_CHILD-1){   //節result.newNodeは、節node側に挿入される
                    //まず、HALF_CHILD-1～MAX_CHILD-1番目の部分木を、
                    //節nodeから節newNodeへと移す
                    for(int i=HALF_CHILD-1, j=0; i<MAX_CHILD; i++, j++){
                        newNode.child[j] = node.child[i];
                        newNode.low[j] = node.low[i];
                    }
                    //0～HALF_CHILD-2番目の部分木の間の適切な位置に、
                    //節result.newNodeを挿入する
                    for(int i=HALF_CHILD-2; i>pos; i--){
                        node.child[i+1] = node.child[i];
                        node.low[i+1] = node.low[i];
                    }
                    node.child[pos+1] = result.newNode;
                    node.low[pos+1] = result.lowest;
                }else{  //節result.newNodeは、節newNode側に挿入される
                    //HALF_CHILD～MAX_CHILD-1番目の部分木を、節newNodeに移動する
                    //同時に、節result.newNodeを適切な位置に挿入する
                    int j = MAX_CHILD - HALF_CHILD;
                    for(int i=MAX_CHILD-1; i>=HALF_CHILD; i--){
                        if(i==pos){
                            newNode.child[j] = result.newNode;
                            newNode.low[j--] = result.lowest;
                        }
                        newNode.child[j] = node.child[i];
                        newNode.low[j--] = node.low[i];
                    }
                    if(pos<HALF_CHILD){
                        newNode.child[0] = result.newNode;
                        newNode.low[0] = result.lowest;
                    }
                }
                //子の数nChildを更新する
                node.nChilds = HALF_CHILD;
                newNode.nChilds = (MAX_CHILD + 1) - HALF_CHILD;
                
                //分割して作られた節をnewNodeフィールドに、
                //またその最小値をlowestフィールドに返す
                return new InsertAuxResult(newNode, newNode.low[0]);
            }
        }
    }
    
    //B木に要素を挿入する
    public boolean insert(Integer key, Object data){
        currentLeaf = null;
        
        if(root==null){
            //木が空の場合には、葉を作ってtrueを返す
            root = new Leaf(key, data);
            return true;
        }else{
            //木が空でない場合には、insertAuxメソッドを呼び出して要素の挿入を行う
            InsertAuxResult result = insertAux(null, -1, key, data);
            if(result==null){
                //もし結果がnullなら、すでにキーkeyは登録されているので、
                //そのままfalseを返す
                return false;
            }
            
            //もし分割が行われたら、気の高さを1段高くする
            if(result.newNode!=null){
               InternalNode newNode = new InternalNode();
               newNode.nChilds = 2;
               newNode.child[0] = root;
               newNode.child[1] = result.newNode;
               newNode.low[1] = result.lowest;
               root = newNode;
            }
            return true;
        }
    }
    
    //内部節pのx番目とx+1番目の部分木を再構成する
    //もし併合が必要なら、すべて要素を部分木x番目の部分木に集めてtrueを返す
    //併合が不要ならfalseを返す
    private static boolean mergeNodes(InternalNode p, int x){
        InternalNode a = (InternalNode)p.child[x];  //x番目の部分木
        InternalNode b = (InternalNode)p.child[x+1];//x+1番目の部分木
        b.low[0] = p.low[x+1];
        
        int an = a.nChilds; //部分木aの子の数
        int bn = b.nChilds; //部分木bの子の数
        
        if((an+bn)<=MAX_CHILD){ //部分木aとbを併合しなければならない
            //bの子をすべてaへ移動する
            for(int i=0; i<bn; i++){
                a.child[i+an] = b.child[i];
                b.child[i] = null;
                a.low[i+an] = b.low[i];
            }
            a.nChilds += bn;    //子の数を更新する
            return true;        //併合したことを通知する
        }else{  //部分木aとbとで、節を再分配する
            int move;   //移動する要素の個数
            //部分木aに分配すべき子の数を求める
            int n = (an + bn) / 2;
            if(an>n){   //部分木aから部分木bへと移動する
                move = an - n;  //move個の子をaからbへ移す
                //bの要素を右にずらす
                for(int i=bn-1; i>=0; i--){
                    b.child[i+move] = b.child[i];
                    b.low[i+move] = b.low[i];
                }
                //aからbへmove個の子を移動する
                for(int i=0; i<move; i++){
                    b.child[i] = a.child[i+n];
                    a.child[i+n] = null;
                    b.low[i] = a.low[i+n];
                }
            }else{  //部分木bから部分木aへと移動する
                move = n - an;  //move個の個をbからaへ移す
                //bからaへmove個の子を移動する
                for(int i=0; i<move; i++){
                    a.child[i+an] = b.child[i];
                    a.low[i+an] = b.low[i];
                }
                //bの要素を左へ詰め合わせる
                for(int i=0; i<bn-move; i++){
                    b.child[i] = b.child[i+move];
                    b.child[i+move] = null;
                    b.low[i] = b.low[i+move];
                }
            }
            
            a.nChilds = n;          //子の数を更新する
            b.nChilds = an + bn - n;//子の数を更新する
            p.low[x+1] = b.low[0];  //部分木bの最小値を節pにセットする
            return false;
        }
    }
    
    //deleteAuxメソッドの戻り値
    private enum Status{
        OK,             //削除に成功。thisNodeには何の変化もない
        OK_REMOVED,     //削除に成功。thisNodeそのものが削除された
        OK_NEED_REORG,  //削除に成功。thisNodeの子が少なくなったので、再編成が必要になった
        NOT_FOUND       //削除に失敗。キーkeyをもつ子は見つからなかった
    }
    
    //節thisNodeから、キーkeyをもつ要素を削除する　（deleteの下請け）
    private static Status deleteAux(Node thisNode, Integer key){
        if(thisNode instanceof Leaf){   //この節は葉である
            //これ以降、この節を葉leafとして参照する
            Leaf leaf = (Leaf)thisNode;
            //この葉のキーとkeyが等しければ、削除する
            if(leaf.key.compareTo(key)==0){
                return Status.OK_REMOVED;
            }else{  //キーが一致しない
                //与えられたキーをもつ要素は存在しなかった
                return Status.NOT_FOUND;
            }
        }else{  //この節は内部節である
            //これ以降、この節を内部節nodeとして参照する
            InternalNode node = (InternalNode)thisNode;
            boolean joined = false; //再編成の結果、部分木が併合されたか？
            //どの部分木から削除するかを決める
            int pos = node.locateSubtree(key);
            //その部分木に対して、自分自身を再帰呼び出しする
            Status result = deleteAux(node.child[pos], key);
            //部分木に何の変化もなければ、そのまま戻る
            if(result==Status.NOT_FOUND || result==Status.OK){
                return result;
            }
            //部分木posを再編成する必要があるか？
            if(result==Status.OK_NEED_REORG){
                int sub = (pos==0) ? 0 : pos-1;
                //部分木subとsub+1を再編成する
                joined = mergeNodes(node, sub);
                //もし、subとsub+1が併合されていたら、
                //部分木sub+1をnodeから削除する必要がある
                if(joined){
                    pos = sub + 1;
                }
            }
            
            Status myResult = Status.OK;    //このメソッドが返す戻り値
                                            //とりあえずOKとしておく
            //部分木posが削除された
            if(result == Status.OK_REMOVED || joined){ 
                //nodeの部分木を詰め合わせる
                for(int i = pos; i<node.nChilds-1; i++){
                    node.child[i] = node.child[i+1];
                    node.low[i] = node.low[i+1];
                }
                node.child[node.nChilds-1] = null;  //不要な参照を消す
                //もし、nodeの部分木の数のHALF_CHILDより小さいなら
                //再編成が必要である
                if(--node.nChilds < HALF_CHILD){
                    myResult = Status.OK_NEED_REORG;
                }
            }
        return myResult;
        }
    }
    
    //B木から要素を削除する
    public boolean delete(Integer key){
        currentLeaf = null;
        //木が空ならfalseを返す
        if(root==null){
            return false;
        }else{  
            //木が空でない場合
            //deleteAuxメソッドを呼び出して、キーkeyをもつ要素を削除する
            Status result = deleteAux(root, key);
            //見つからなければ、falseを返す
            if(result==Status.NOT_FOUND){
                return false;
            }
            if(result==Status.OK_REMOVED){
                //根が削除されたので、rootにnullを代入する（気が空になる）
                root = null;
            }else if(result==Status.OK_NEED_REORG && ((InternalNode)root).nChilds==1){
                //根が再編成された結果、根の子が1個だけになったら、
                //木の高さを1つ減らす
                root = ((InternalNode)root).child[0];
            }
            return true;
        }
    }
    
    //B木の内容を表す文字列を返す（toStringの下請け）
    private static String toStringAux(Node p){
        //葉か内部節かで処理を分ける
        if(p instanceof Leaf){  
            //葉である
            Leaf l = (Leaf)p;
            return "Leaf #" + l.serial + " key=" + l.key;// + " data=" + l.data;
        }else{
            //内部節である
            InternalNode n = (InternalNode)p;
            String s = "Node #" + n.serial + " (" + n.nChilds + " childs): ";
            s += "#" + n.child[0].serial + " ";
            for(int i=1; i<n.nChilds; i++){
                s += "[" + n.low[i] + "] #" + n.child[i].serial + " ";
            }
            s += "\n";
            for(int i=0; i<n.nChilds; i++){
                s += toStringAux(n.child[i]) + "\n";
            }
            return s;
        }
    }
    
    //B木の内容を表す文字列を返す（実際の処理はtoStringAuxメソッドが行う）
    public String toString(){
        if(root==null){
            return "< 木は空です >";
        }else{
            return toStringAux(root);
        }
    }
    
    
    //テスト用メインルーチン
    /*
    ">"というプロンプトが表示されるので、
    コマンドを入力すると実行結果が表示される
    
    コマンド一覧（nは整数）
        +n      : nを挿入する
        -n      : nを削除する
        /n      : nを探索する
        =string : 直前に成功した/コマンドで見つけた要素に対する値をstringにする
        p       : B木の内容を表示する
        q       : 終了する
        !       : 第2回演習課題3の解答を表示する
        
    コマンド実行例：以下の順番で処理を行う場合
    「データ2を登録→データ5を登録→データ10を登録→データ2を削除→B木を表示」
    CODEROOMの標準入力欄に下記のように記述しておく
    +2
    +5
    +10
    -2
    p
    q
    */
    public static void main(String[] args) throws IOException{
        BTree tree = new BTree();
        
        //コマンドを1行入力して、それを実行する
        //これをEOFになるまで繰り返す
        System.out.print(">");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while((str=input.readLine())!=null){
            if(str.length()==0){    //空行は読み飛ばす
                System.out.print(">");
                continue;
            }
            
            //先頭の1文字をcommandに入れる
            char command = str.charAt(0);
            //引数部分をargにいれる。その際に先頭のスペースを削除する
            String arg = str.substring(1).trim();
            
            System.out.println(str);    //入力されたコマンドを表示する
            
            //コマンドによって分岐する
            if(command == 'q'){
                //終了する
                System.out.println("プログラムを終了します。");
                break;
            }else if(command == '!'){
                //第2回演習課題3の解答を表示する
                tree.insert(2, "<2>");
                tree.insert(5, "<5>");
                tree.insert(7, "<7>");
                tree.insert(9, "<9>");
                System.out.println("【初期状態】");
                System.out.println(tree);
                tree.insert(14, "<14>");
                tree.insert(18, "<18>");
                tree.delete(5);
                tree.insert(8, "<8>");
                tree.delete(2);
                tree.insert(16, "<16>");
                System.out.println("【最終的な状態】（課題の答え）");
                System.out.println(tree);   
            }else if(command == 'p'){
                //B木の内容を表示する
                System.out.println(tree);
            }else if(command == '='){
                //直前に成功した/コマンドの要素のデータにセットする
                if(tree.setData(arg)){
                    System.out.println("値" + arg + "の設定に成功しました。");
                }else{
                    System.out.println("値" + arg + "の設定に失敗しました。");
                }
            }else if(command == '+' || command=='-' || command=='/'){
                //+、-、/コマンドならば、コマンドに続く数値をnumに入れる
                int num = 0;
                try{
                    num = Integer.parseInt(arg);
                }catch (NumberFormatException e){
                    System.err.println("整数以外のものが指定されました:" + arg);
                    continue;
                }
                
                if(command=='+'){
                    //要素を挿入する
                    if(tree.insert(num, "<" + num + ">")){
                        System.out.println(num + "の挿入に成功しました。");
                    }else{
                        System.out.println(num + "の挿入に失敗しました。");
                    }
                }else if(command=='-'){
                    //要素を削除する
                    if(tree.delete(num)){
                        System.out.println(num + "の削除に成功しました。");
                    }else{
                        System.out.println(num + "の削除に失敗しました。");
                    }
                }else if(command=='/'){
                    //要素を探索する
                    if(tree.search(num)){
                        System.out.println(num + "が見つかりました。値=" + tree.getData());
                    }else{
                        System.out.println(num + "が見つかりませんでした。");
                    }
                }
            }else{
                System.out.println("コマンド" + command + "はサポートされていません。");
            }
            System.out.print(">");
        }
        
    }
}