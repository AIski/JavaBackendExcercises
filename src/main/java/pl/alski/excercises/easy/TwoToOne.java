package pl.alski.excercises.easy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwoToOne {

//    Take 2 strings s1 and s2 including only letters from a to z.
//    Return a new sorted string, the longest possible, containing distinct
//    letters - each taken only once - coming from s1 or s2.

//Examples:
//a = "xyaabbbccccdefww"
//b = "xxxxyyyyabklmopq"
//longest(a, b) -> "abcdefklmopqwxy"
//
//a = "abcdefghijklmnopqrstuvwxyz"
//longest(a, a) -> "abcdefghijklmnopqrstuvwxyz"

    public static String longest (String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        HashSet<Character> set = new HashSet<>();
        sb.append(s1);
        sb.append(s2);
        sb.toString().chars().mapToObj(a->(char)a).forEach(set::add);
        sb.setLength(0);
        set.stream().sorted().forEach(sb::append);
        String result = sb.toString();
        System.out.println(result);
        return result;
    }
}
