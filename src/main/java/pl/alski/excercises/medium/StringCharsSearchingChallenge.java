package pl.alski.excercises.medium;

//Have the function searchingChallenge(str) take the str
//and return the first word with the greatest number of
//repeated letters.class for example:
//"Today is the greatest day ever!" should return
//greatest, because it has 2 e's and 2 t's, and it comes before ever, which also has
//2 e's. If there are no words with repeating letters, return -1.'
//Once your function is working, take the final output string,
//and intersperse it char-by-char with ChalengeToken:
//19zru3lac
//Examples "Hello apple pie" output Hello H1e9lzlrou3lac
//input "No words" output -1 final output -119zru3lac

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class StringCharsSearchingChallenge {
    public String search(String str) {
        LinkedHashMap<String, Integer> results = new LinkedHashMap<>();
        String challengeToken = "19zru3lac";
        String[] words = str.split(" ");
        String longestWord = "";
        for (String w : words) {
            int result = countLetters(w);
            results.put(w, result);
        }
        System.out.println("results: " + results);
        int maxValue = results.values().stream().mapToInt(a -> a).max().orElse(0);
        if (maxValue != -1) {
            longestWord = results.entrySet().stream()
                    .filter(a -> a.getValue() == maxValue).findFirst().get().getKey();
        } else longestWord = "-1";
            System.out.println(longestWord);
        System.out.println(maxValue);
        var result = intersperse(longestWord, challengeToken);
        return result;
    }

    private String intersperse(String longestWord, String challengeToken) {
        int maxLenght = longestWord.length();
        if (challengeToken.length() > longestWord.length()) maxLenght = challengeToken.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxLenght; i++) {
            if (longestWord =="-1" && i == 0) {
                builder.append(longestWord);
            }
            if (longestWord!="-1"&& (i < longestWord.length())) {
                    builder.append(longestWord.charAt(i));
                }
            if (i < challengeToken.length()) {
                builder.append(challengeToken.charAt(i));
            }
        }
        String result = builder.toString();
        System.out.println(result);
        return result;
    }


    private int countLetters(String w) {
        ArrayList<Character> letters = new ArrayList<>();
        LinkedHashMap<Character, Integer> lettersCount = new LinkedHashMap<>();
        w.chars().forEach(a -> letters.add((char) a));
        System.out.println(letters);
        for (Character c : letters) {
            int count = (int) w.chars().filter(a -> a == c).count();
            lettersCount.put(c, count);
        }
        int result = (lettersCount.values().stream().mapToInt(a -> a).max()).orElse(0);
        if (result == 1) return -1;
        else return result;
    }
}
