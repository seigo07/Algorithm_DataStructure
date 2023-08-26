//スタックの実装

import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStack{
    private Object stack[]; //スタック本体
    private int stackSize;  //スタックの大きさ
    private int sp;      //スタックポインタ
    
    //大きさを指定してスタックを生成する
    public MyStack(int size){   //size：スタックの大きさ
        stackSize = size;
        stack = new Object[size];
        sp = 0;
    }
    
    //スタックの中身を捨てて空の状態にする
    public void clear(){
        Arrays.fill(stack, 0, sp, null);    //スタックをnullでクリアする
        sp = 0;                             //スタックポインタを0にする
    }
    
    //スタックにデータを積む
    public void push(Object x){ //x：積むデータ
        if(sp >= stackSize){
            throw new IllegalStateException("Stack Overflow");
        }
        stack[sp++] = x;
    }
    
    //スタックからデータを降ろす
    public Object pop(){
        if(sp <= 0){
            throw new EmptyStackException();
        }
        Object value = stack[--sp];
        stack[sp] = null;   //空いた要素をnullでクリアする
        return value;       //スタックから降ろしたデータを返す
    }
    
    //スタックが空かどうかを調べる
    public boolean isEmpty(){
        return sp == 0;     //スタックが空ならtrue、空でなければfalseを返す
    }
    
    //スタックの内容を表す文字列を返す
    public String toString(){
        int i;
        String s = "MyStack=[";
        for(i=0; i<sp; i++){
            s = s + stack[i];
            if(i<sp-1){
                s = s + ",";
            }
        }
        s = s + "]";
        return s;       //スタックの内容を表す文字列を返す
    }
    
    //テスト用メインルーチン
    public static void main(String args[]){
        MyStack stack = new MyStack(5);
        
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println(stack);
        System.out.println("pop:" + stack.pop());
        System.out.println(stack);
        stack.push("d");
        stack.push("e");
        stack.push("f");
        System.out.println(stack);
        while(!stack.isEmpty()){
            System.out.println("pop:" + stack.pop());
        }
        System.out.println("DONE! " + stack);
    }
}
