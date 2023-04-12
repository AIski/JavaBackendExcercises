package pl.alski.excercises.notFinished;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateFibonacciReturnCountOfDigitOccurrences {

//DESCRIPTION:
//Fibonacci numbers are generated by setting F0 = 0, F1 = 1, and then using the formula:
//
//Fn = Fn-1 + Fn-2
//Your task is to efficiently calculate the nth element in the Fibonacci sequence and then count the occurrence of each digit in the number. Return a list of integer pairs sorted in descending order.
//
//10 ≤ n ≤ 100000
//
//Examples
//f(10) = 55  # returns [(2, 5)], as there are two occurances of digit 5
//
//f(10000)    # returns:
//  [(254, 3),
//   (228, 2),
//   (217, 6),
//   (217, 0),
//   (202, 5),
//   (199, 1),
//   (198, 7),
//   (197, 8),
//   (194, 4),
//   (184, 9)]
//If two integers have the same count, sort them in descending order.
//
//Your algorithm must be efficient.

    public static int[][] FibDigits(int n) {
        List<Integer> fibonacciNumbers = getFibonacciNumbers(n);
        System.out.println(fibonacciNumbers);

        String nthFibonacciString = fibonacciNumbers.get(n).toString();
        System.out.println("string: " + nthFibonacciString);

        HashMap<Integer, Integer> results = countDigitsOccurrences(nthFibonacciString);
        System.out.println(results);
        results.entrySet().stream().sorted((a, b) -> a.getValue().compareTo(b.getValue())).collect(Collectors.toList());
        return new int[][]{{fibonacciNumbers.get(n), 0}};
    }
    //TODO:
    // rework the code, so no big number is being saved. instead,

    private static HashMap<Integer, Integer> countDigitsOccurrences(String fibonacciString) {
        HashMap<Integer, Integer> results = new HashMap<>();
        for (int i = 0; i < fibonacciString.length(); i++) {
            int currentInt = Character.getNumericValue(fibonacciString.charAt(i));
            if (results.containsKey(currentInt)) {
                results.put(currentInt, results.get(currentInt) + 1);
                System.out.println("adding more ints: " + currentInt);
                System.out.println();
            } else {
                results.put(currentInt, 1);
                System.out.println("adding 1st int: " + currentInt);
                System.out.println(results);
            }
        }
        return results;
    }


    private static List<Integer> getFibonacciNumbers(int n) {
        List<Integer> fibonacciNumbers = new ArrayList<>();
        fibonacciNumbers.add(0);
        fibonacciNumbers.add(1);
        if (n > 2) {
            for (int i = 2; i <= n; i++) {
                fibonacciNumbers.add(fibonacciNumbers.get(i - 1) + fibonacciNumbers.get(i - 2));
            }
        }
        return fibonacciNumbers;
    }


}