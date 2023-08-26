/*
リングバッファを使ったキューの実装
このプログラムは第4回演習課題に対応しています。
空欄を含んでいますので、このままでは動きません。
*/

import java.util.Arrays;

public class MyQueue{
    private Object queue[]; //キュー本体
    private int queueSize;  //キューの大きさ
    private int front;      //キューの先頭
    private int rear;       //キューの末尾
    
    //大きさを指定してキューを生成する
    public MyQueue(int size){   //size：キューの大きさ
        queueSize = size;
        queue = new Object[size];
        front = 0;
        rear = 0;
    }
    
    //次の要素の添え字を求める
    private int next(int a){    //a：現在の要素の添え字
        return (a+1)%queueSize;
    }
    
    //キューが空かどうかを調べる
    public boolean isEmpty(){
        return front == rear;   //空ならture、空でなければfalseを返す
    }
    
    //キューの中身を捨てて空にする
    public void clear(){
        front = 0;
        rear = 0;
        Arrays.fill(queue, 0, queueSize, null);
        //System.out.println("リングバッファをクリアした");
    }
    
    //キューにデータを入れる
    public void enqueue(Object x){  //x：キューに入れるデータ
        if(next(rear)==front){
            throw new IllegalStateException("これ以上、キューに要素を追加できません。");
        }
        queue[rear] = x;
        rear = next(rear);
        //System.out.println(x+"を入れた");
    }
    
    //キューからデータを取り出す
    public Object dequeue(){
        if(rear==front){
            throw new IllegalStateException("キューが空なので要素を取り出せません。");
        }
        Object x = queue[front];
        queue[front] = null;
        front = next(front);
        //System.out.println(x+"を取り出した");
        return x;   //取り出したデータを返す
    }
    
    //キューの内容を表す文字列を返す
    public String toString(){
        int i;
        String s = "MyQueue[";
        for(i=front; i!=rear; i=next(i)){
            s += queue[i] + " ";
        }
        s += "] front=" + front + " rear=" + rear;
        return s;   //キューの内容を表す文字列を返す
    }
    
    //テスト用のメインルーチン
    public static void main(String args[]){
        MyQueue q = new MyQueue(5);
        
        q.enqueue("a");
        q.enqueue("b");
        q.enqueue("c");
        System.out.println(q);
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.enqueue("d");
        q.enqueue("e");
        System.out.println(q);
        q.enqueue("f");
        q.dequeue();
        q.dequeue();
        System.out.println(q);
        q.clear();
        q.enqueue("g");
        q.enqueue("h");
        System.out.println(q);
    }
}
