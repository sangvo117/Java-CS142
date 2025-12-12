public class RecursiveTracingMystery {

    public static void main(String[] args) {
        mystery(3, 3);
        mystery(5, 1);
        mystery(1, 5);
        mystery(2, 7);
        mystery(1, 8);
    }

    public static void mystery(int x, int y) {
        if (x > y) {
            System.out.print("*");
        } else if (x == y) {
            System.out.print("=" + y + "=");
        } else {
            System.out.print(y + " ");
            mystery(x + 1, y - 1);
            System.out.print(" " + x);
        }
    }
}

//x=3,y=3->"=3="
//
//x=5,y=1->"*"
//
//x=1,y=5->"5 " x=2,y=4->"5 4 " x=3,y=3->"5 4 =3= 2 1"
//
//x=2,y=7->"7 6 5 * 4 3 2"
//
//x=1,y=8->"8 7 6 5 * 4 3 2 1"