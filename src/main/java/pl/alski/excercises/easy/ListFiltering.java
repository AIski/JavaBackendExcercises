package pl.alski.excercises.easy;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListFiltering {

//In this kata you will create a function that takes a list of non-negative integers and strings and returns a new list with the strings filtered out.
//
//        Example
//        Kata.filterList(List.of(1, 2, "a", "b")) => List.of(1,2)
//        Kata.filterList(List.of(1, 2, "a", "b", 0, 15)) => List.of(1,2,0,15)
//        Kata.filterList(List.of(1, 2, "a", "b", "aasf", "1", "123", 231)) => List.of(1, 2, 231)


    public static List<Object> filterList(final List<Object> list) {
        List<Object> result = new ArrayList<>();
        result = list.stream()
                .filter(a -> (a.getClass().equals(Integer.class)))
                .collect(Collectors.toList());
        System.out.println(result);

        return null;
    }
}
