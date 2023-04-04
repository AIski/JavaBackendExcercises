package pl.alski.excercises.medium;

// Have the function ArrayChallenge(arr), take the parameter
// being passed which will be an array of integers and determine the
//smallest positive integer, including zero, that can be added to the array
//to make the sum of all the numbers in the array add up to the closest fibonacci number.
// for example, if arr is [15,1,3], program should return 2,
//becouse 15+1+3 = 19, closest fibonacci number is 21, so the difference is 2
//examples {5,2,1} output 0,
//        {1,20,2,5} output 6

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FibonacciArrayChallenge {


    public int solve(int[] arr) {
        List<Integer> fibonacci = new ArrayList<>();
        fibonacci.add(0);
        fibonacci.add(1);

        int sum = Arrays.stream(arr).sum();
        System.out.println("Sum: "+sum);

        getAllFibonacciNumbers(fibonacci, sum);
        System.out.println("fibonaci"+fibonacci);

        int result = fibonacci.get(fibonacci.size() - 1) - sum;
        System.out.println(result);
        return result;
    }

    private static void getAllFibonacciNumbers(List<Integer> fibonacci, int sum) {
        while (fibonacci.get(fibonacci.size() - 1) < sum) {
            fibonacci.add(getAnotherFibonacciNumber(fibonacci));
        }
    }

    private static int getAnotherFibonacciNumber(List<Integer> fibonacci) {
       return fibonacci.get(fibonacci.size() - 1) + fibonacci.get(fibonacci.size() - 2);
    }
}
