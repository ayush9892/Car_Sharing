import java.util.Scanner;
import java.util.stream.*;

class Main {

    /**
     * Calculates the factorial of the given number n
     *
     * @param n >= 0
     *
     * @return factorial value
     */
    public static long factorial(long n) {
        // write your code here
        Stream<Long> str = Stream.iterate(1L, a -> a+1).limit(n);
        return str.reduce(1L, (mul, ech) -> mul * ech);
    }

    // Don't change the code below
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        long n = Integer.parseInt(scanner.nextLine().trim());

        System.out.println(factorial(n));
    }
}