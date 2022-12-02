import java.util.Scanner;
import java.util.stream.*;
import java.util.*;

public class Main {

    /**
     * Calculates the sum of numbers in the range from a to b both inclusive
     * that are only divisible by n or m.
     *
     * @param a > 0
     * @param b > 0
     * @param n > 0
     * @param m > 0
     *
     * @return sum of numbers from the range that are only divisible by n or m
     */
    public static int sum(int a, int b, int n, int m) {
        // write your code here
        Stream<Integer> stm = Stream.iterate(a, nxt -> nxt + 1).limit(b-a+1);
        return stm.filter(no -> no % n == 0 || no % m == 0)
                  .reduce(0, Integer::sum);
    }

    // Don't change the code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        System.out.println(sum(a, b, n, m));
    }
}
