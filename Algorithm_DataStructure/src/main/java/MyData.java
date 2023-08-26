
class MyData{

    private String name;
    private int age;
    void setData(String name, int age){
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args){
        MyData[] data = new MyData[5];
        data[0].setData("J.S. Bach", 100);
    }
}
