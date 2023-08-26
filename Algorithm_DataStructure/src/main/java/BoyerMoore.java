/*
Boyer-Mooreのアルゴリズムによる文字列探索
このプログラムはこのままでは実行できません。
第7回演習課題に対応する空欄を埋めてから実行してください。
*/

import java.util.Arrays;

public class BoyerMoore{

    //文字列textから文字列patternを探索する（BM法）
    public static int search(String text, String pattern)
    {
        int patLen = pattern.length();  // パターンの長さ
        int textLen = text.length();    // テキストの長さ

        //テキストとパターンの不一致が見つかったときに、
        //どれだけずらすかを示す表
        int [] skip = new int[65536];
 
        //表skipを作成する
        Arrays.fill(skip,patLen);
        for(int x=0; x<patLen-1; x++){
            skip[pattern.charAt(x)] = patLen - x - 1;
        }
 
        //注目しているテキストの位置を表すポインタ
        //パターンを後ろから前に向かって比較するので、
        //「パターンの長さ-1」に初期化する
        int i = patLen - 1;
 
        //テキストの最後尾に行き当たるまで繰り返す
        int x = 0;
        int y = 0;
        while(i<textLen){ //←α
            x++;
            //注目しているパターンの位置を表すポインタ
            //パターンの最後の文字を指すようにする
            int j = patLen - 1;
            // テキストとパターンが一致する間，繰り返す
            while(text.charAt(i) == pattern.charAt(j)){ //←β
                y++;
                // 最初の文字まで一致したら、探索は成功である
                if(j==0){
                    System.out.println("x:"+x);
                    System.out.println("y:"+y);
                    return i;
                }
 
                // ポインタiとjをそれぞれ1文字分戻す
                i--;
                j--;
            }

            // 一致しなかったので、パターンをずらす
            i = i + Math.max(skip[text.charAt(i)], patLen - j);//(3)
        }
        // 結局、見つからなかった
        return -1;
    }
    
    
    public static void main(String args[]){
        int pos;
        String text = "ABCABBACABACADEC";
        String pattern ="ABAC";
        pos = BoyerMoore.search(text, pattern);

        if(pos<0){
            System.out.println("search failed.");
        }else{
            System.out.println("position = " + pos);
        }
    }
    
    
}
