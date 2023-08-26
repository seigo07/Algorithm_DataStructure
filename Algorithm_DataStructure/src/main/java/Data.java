public class Data {

    public static void main(String[] args){
        int[] a = new int[5];
        for(int i = 0; i < a.length; i++){
            a[i] = i;
            System.out.println("a[i] = " + a[i]);
        }
        int[] b = a;
        for(int i = 0; i < b.length; i++){
            System.out.println("b[i] = " + b[i]);
        }
        b[3] = 200;
        for(int i = 0; i < a.length; i++){
            System.out.println("a[i] = " + a[i]);
        }
        for(int i = 0; i < b.length; i++){
            System.out.println("b[i] = " + b[i]);
        }
        System.out.println("a[3] = " + a[3]);
        System.out.println("b[3] = " + b[3]);
    }
}
