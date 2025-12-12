public class RecursiveProgrammingRepeat {
    public static void main(String[] args) {
        System.out.println(repeat("hello", 3));
        System.out.println(repeat("this is fun", 1));
        System.out.println(repeat("wow", 0));
        System.out.println(repeat("hi ho! ", 5));
    }

    public static String repeat(String str, int count) throws IllegalArgumentException{
        if (count < 0) throw new IllegalArgumentException();

        if (count == 0) return "";

        if (count == 1) return str;

        if (count % 2 == 0) {
            String temp = str + str;
            return repeat(temp, count / 2);
        }

        return str + repeat(str, count - 1);
    }
}
