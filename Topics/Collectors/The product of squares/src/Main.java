import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CollectorProduct {

    public static void main(String[] args) {


        Stream.of(-1, 2, -3, 4).map(x -> x > 0 ? x : -x).forEach(System.out::println);
        //System.out.println(val);
    }
}