public class Test {

    public static <T> T test(int a){
       return (T)"";
    }

    public static void main(String[] args) {
        int a = test(666);//error
    }
}
