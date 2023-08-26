/*
二分木におけるなぞりの実装
このプログラムは第6回演習課題に対応しています。
空欄を含んでいますので、このままでは動きません。
*/
class BinaryTreeNode{
    String label;           //この節のラベル
    BinaryTreeNode left;    //左部分木
    BinaryTreeNode right;   //右部分木
    
    BinaryTreeNode(String label, BinaryTreeNode left, BinaryTreeNode right){
        this.left = left;
        this.right = right;
        this.label = label;
    }
    
	//①②③のいずれかに下記の出力分を挿入する
	//System.out.println("節" + p.label + "に立ち寄りました。");
	
    //行きがけ順
    static void traversePreorder(BinaryTreeNode p){ //p：なぞるべき木
        if(p==null){ //木が空ならなにもしない
            return;
        }
        System.out.println("節" + p.label + "に立ち寄りました。");
        traversePreorder(p.left);
        traversePreorder(p.right);
    }
    
    //通りがけ順
    static void traverseInorder(BinaryTreeNode p){ //p：なぞるべき木
        if(p==null){ //木が空ならなにもしない
            return;
        }
        traverseInorder(p.left);
        System.out.println("節" + p.label + "に立ち寄りました。");
        traverseInorder(p.right);
    }
    
    //帰りがけ順
    static void traversePostorder(BinaryTreeNode p){ //p：なぞるべき木
        if(p==null){ //木が空ならなにもしない
            return;
        }
        traversePostorder(p.left);
        traversePostorder(p.right);
        System.out.println("節" + p.label + "に立ち寄りました。");
    }
    
    //テスト用メインルーチン
    public static void main(String args[]){
        //第6回講義資料 p.47右下の二分木を作成する
        BinaryTreeNode tree = new BinaryTreeNode(
            "a",
            new BinaryTreeNode("b",
                new BinaryTreeNode("c", null, null),
                null),
            new BinaryTreeNode("d", null, null));

        System.out.println("〔行きがけ順〕");
        traversePreorder(tree);
        System.out.println("〔通りがけ順〕");
        traverseInorder(tree);
        System.out.println("〔帰りがけ順〕");
        traversePostorder(tree);
    }
}
