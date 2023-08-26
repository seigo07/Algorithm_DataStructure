import java.util.Arrays;

public class Knapsack{
    int[] size;  //品物の大きさ
    int[] value; //品物の価値
    int N;       //品物の種類の数

    public Knapsack(int[] size, int[] value){
        if (size.length != value.length){
            throw new IllegalArgumentException(
                    "'size'と'value'の要素数が一致していません");
        }
        this.N = size.length;
        this.size = (int[])size.clone();
        this.value = (int[])value.clone();
    }

    public void solve(int m){
        int[] total = new int[m+1];
        int[] choice = new int[m+1];
        Arrays.fill(choice, -1);

        int repackTotal;

        System.out.printf("ナップザックの大きさは%d%n", m);

        for(int i=0; i<N; i++){
            for(int j=size[i]; j<=m; j++){
                repackTotal = total[j - size[i]] + value[i];
                if(repackTotal>total[j]){
                    total[j] = repackTotal;
                    choice[j] = i;
                }
            }

            System.out.printf("品物");
            for(int j=0; j<=i; j++){
                System.out.printf(" %d", j);
            }
            System.out.printf("%ntotal  = ");
            for(int j=0; j<=m; j++){
                System.out.printf("%4d", total[j]);
            }
            System.out.printf("%nchoice = ");
            for(int j=0; j<=m; j++){
                System.out.printf("%4d", choice[j]);
            }
            System.out.printf("%n");
        }

        System.out.printf("品物の内訳: %n");
        for(int i=m; choice[i]>=0; i-=size[choice[i]]){
            System.out.printf("品物%d（価値%d）%n", choice[i], value[choice[i]]);
        }
        System.out.printf("価値の合計: %d%n", total[m]);
    }

    public static void main(String args[]){
        Knapsack  knapsack = new Knapsack(
                new int[] {2, 3, 5, 7},
                new int[] {2, 4, 7, 11});
        int size = 16;
        knapsack.solve(size);
    }
}
