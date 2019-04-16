public class test {
    public static void main(String[] args) {
        String temp = "0/2";
        String[] split = temp.split("/");
        System.err.println(split.length);
        System.err.println(split[0] + split[1]);
    }
}
