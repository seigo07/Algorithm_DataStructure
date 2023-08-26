
class Node{
    Integer data; //この節のラベル
    Node left; //左部分木
    Node right; //右部分木
    Node(Integer data){
        left = null;
        right = null;
        this.data = data;
    }
}

class BinSearchTree {
    private Node root; //二分探索木の根

    public BinSearchTree() {
        root = null;
    }

    //二分探索木からキーkey をもつ節を探索する
    public Node search(Integer key) { //key:探し出す節のキー
        Node p = root; //まず根に注目する
        while (p != null) { //注目している節がある限り繰り返す
            //注目している節とキーを比較する
            int result = key.compareTo(p.data);
            if (result == 0) { //節とキーが等しければ、
                return p; // その節へのリンクを返す(探索成功)
            } else if (result < 0) { // キーの方が小さければ、
                p = p.left; // 左部分木に進む
            } else { // キーの方が大きければ、
                p = p.right; // 右部分木に進む
            }
        }
        return null; //探索失敗
    }

    //二分探索木にキーkey をもつ節を挿入する
    public Node insert(Integer key) { //key:挿入する節のキー
        Node p = root; //まず根に注目する
        Node parent = null; //現在注目している節の親を指す
        boolean isLeftChild = false; // pがparentの左の子ならtrue、右の子なら false とする
        while (p != null) { //注目している節がある限り繰り返す
            //注目している節とキーを比較する
            int result = key.compareTo(p.data);
            if (result == 0) { //節とキーが等しければ、
                return null; // すでに登録されている(挿入失敗)
            } else if (result < 0) { //キーの方が小さければ、
                parent = p; // 左部分木に進む
                isLeftChild = true;
                p = p.left;
            } else { //キーの方が大きければ、
                parent = p; // 右部分木に進む
                isLeftChild = false;
                p = p.right;
            }
        }
        //新しい節を適切な場所に挿入する
        Node newNode = new Node(key); //新しい節を割り当てる
        if (parent == null) { //注目している節に親がなければ
            root = newNode; // 新しい節は根になる
        } else if (isLeftChild) { //親の左の子だったら、
            parent.left = newNode; // 節 parent の左の子になる
        } else { //親の右の子だったら、
            parent.right = newNode; // 節 parent の右の子になる
        }
        return newNode; //挿入した節へのリンクを返す(挿入成功)
    }

    //二分探索木からキーkey をもつ節を削除する
    public boolean delete(Integer key){
        Node p = root; //まず根に注目する
        Node parent = null; //現在注目している節の親を指す
        boolean isLeftChild = false; //p が parent の左の子なら true、
        while(p!=null){ //注目している節がある限り繰り返す
            // 注目している節とキーを比較する
            int result = key.compareTo(p.data);
            if(result==0){ //削除する節が見つかった
                if(p.left==null && p.right==null){ //葉である場合
                    // 葉を削除する
                    if(parent==null){
                        root = null;
                    }else if(isLeftChild){
                        parent.left = null;
                    }else{
                        parent.right = null;
                    }
                }else if(p.left==null){ //右の子のみをもつ場合
                    //右の子を親にする
                    if(parent==null){
                        root = p.right;
                    }else if(isLeftChild){
                        parent.left = p.right;
                    }else{
                        parent.right = p.right;
                    }
                }else if(p.right==null){ //左の子のみをもつ場合
                    //左の子を親にする
                    if(parent==null){
                        root = p.left;
                    }else if(isLeftChild){
                        parent.left = p.left;
                    }else{
                        parent.right = p.left;
                    }
                }else{ //左右 2 つの子をもつ場合
                    //右部分木の最小の要素を取り除いて変数 x に入れる
                    Node x = deleteMin(p, p.right);
                    //着目している節に最小の要素 x を入れる
                    if(parent==null){
                        root = x;
                    }else if(isLeftChild){
                        parent.left = x;
                    }else{
                        parent.right = x;
                    }
                    x.right = p.right;
                    x.left = p.left;
                }
                return true;
            }else if(result<0){ //キーの方が小さければ、
                parent=p; // 左部分木に進む
                isLeftChild = true;
                p = p.left;
            }else{ //キーの方が大きければ、
                parent=p; // 右部分木に進む
                isLeftChild = false;
                p = p.right;
            }
        }
        return false;
    }

    //二分探索木から最小の要素を削除する
    private static Node deleteMin(Node parent, Node p){
        boolean isLeftChild = false; //メソッドが呼ばれた時点で右の子なので、 //初期値は false とする
        while(p.left!=null){ //最小の要素を見つける
            parent = p;
            isLeftChild = true;
            p = p.left;
        }
        if(isLeftChild){ //最小の要素を削除する
            parent.left = p.right;
        }else{
            parent.right = p.right;
        }
        return p; //最小の要素を返す
    }

    //二分探索木をなぞる
    private static void traverse(Node p){
        if(p==null){
            return;
        }else{
            traverse(p.left); // 左部分木をなぞる
            System.out.println(p.data); // 節の値を表示
            traverse(p.right); // 右部分木をなぞる
        }
    }

    //二分探索木の全要素を昇順に表示する
    public void showAll() {
        traverse(root);
    }

    //テスト用メインルーチン
    public static void main(String args[]){
        BinSearchTree tree = new BinSearchTree();
        tree.insert(13);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(6);
        tree.insert(21);
        tree.insert(15);
        tree.showAll();
    }
}
