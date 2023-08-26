
//class ListCell {
//    MergeSortListCell next; //次のセルへのリンク
//    Object data; //このセルが持つデータ
//    ListCell(Object data){
//        next = null;
//        this.data = data;
//    }
//}
//
//class MyStack_LL{
//
//    final MergeSortListCell header;
//    //スタックを生成
//    public MyStack_LL(){
//        header = new MergeSortListCell("--List Head--"); //リストの頭
//    }
//    //スタックに値を積む
//    public void push(Object data){ //data:積むデータ
//        MergeSortListCell newMergeSortListCell = new MergeSortListCell(data);
//        newMergeSortListCell.next = header.next;
//        header.next = newMergeSortListCell;
//    }
//    //スタックから値を降ろす
//    public Object pop(){
//        if(header == null){
//            System.out.println("リストは空なので削除できません");
//            return null; //リストが空の場合、何もしない
//        }
//        MergeSortListCell p = header.next;
//        header.next = p.next;
//        return p.data; //降ろしたデータを返す
//    }
//    //スタックが空かどうかを調べる
//    public boolean isEmpty(){
//        //スタックが空なら true、空でなければ false を返す
//        return header.next == null;
//    }
//    //スタックの内容を表す文字列を返す
//    public String toString(){
//        String s = "[";
//        for(MergeSortListCell p = header.next; p != null; p = p.next) {
//            s += p.data + " ";
//        }
//        s += "]";
//        return s; //スタックの内容を表す文字列を返す
//    }
//    //テスト用メインルーチン
//    public static void main(String args[]){
//        MyStack_LL stack = new MyStack_LL();
//        System.out.println(stack);
//        stack.push("a");
//        System.out.println(stack);
//        stack.push("b");
//        System.out.println(stack);
//        stack.push("c");
//        System.out.println(stack);
//        stack.pop();
//        System.out.println(stack);
//        stack.push("d");
//        System.out.println(stack);
//        stack.push("e");
//        System.out.println(stack);
//        stack.push("f");
//        System.out.println(stack);
//        while(!stack.isEmpty()){
//            System.out.println("hoge");
//            stack.pop();
//            System.out.println(stack);
//        }
//        System.out.println("DONE! " + stack);
//    }
//}