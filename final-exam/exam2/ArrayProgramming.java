import java.util.ArrayList;
import java.util.List;

public class ArrayProgramming {
    public static void main(String[] args) {
        int[][] lis = {{1, 2, 3}, {4, 3, 2, 1}, {6, 7, 7}, {8}};

        System.out.println(numUnique(lis));
    }

    public static int numUnique(int[][] list) {
        List<Integer> numsUnique = new ArrayList<>();

        for (int[] nums : list) {
            for (int num : nums) {
                if (!numsUnique.contains(num)) {
                    numsUnique.add(num);
                }
            }
        }

        return numsUnique.size();
    }
}
