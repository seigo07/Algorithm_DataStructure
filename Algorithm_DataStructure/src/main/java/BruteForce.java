//力まかせ法による文字列探索
public class BruteForce{
    //文字列textから文字列patternを探索する  
    public static int search(String text, String pattern){
        int patLen = pattern.length();  // パターンの長さ
        int textLen = text.length();    // テキストの長さ
        int i = 0;  // 注目しているテキストの位置を表すポインタ
        int j = 0;  // 注目しているパターンの位置を表すポインタ
        int x = 0;

        //テキストの最後尾に行き当たるか、パターンが見つかるまで繰り返す
        while(i<textLen && j<patLen){
            x++;
            //テキストとパターンを1文字比較する
            if(text.charAt(i) == pattern.charAt(j)){
                //一致した！テキストとパターンのポインタを進める
                i++; j++;
            }else{ // 一致しなかった
                i = i - j + 1;  // テキストのポインタを現在注目している
                                // 先頭から1つ進める。
                j = 0; // パターンのポインタを先頭に戻す
            }
        }
        
        //もし探索が成功したら、パターンが見つかった位置を返す。
        if(j==patLen){
            System.out.println("x:"+x);
            return (i - j);
        }else{
            //失敗したら-1を返す
            return -1;
        }
    }
    
    public static void main(String args[]){
        int pos;
        String text = "ABCABBACABACADEC";
        String pattern ="ABAC";
        pos = BruteForce.search(text, pattern);

        if(pos<0){
            System.out.println("search failed.");
        }else{
            System.out.println("position = " + pos);
        }
    }
}
