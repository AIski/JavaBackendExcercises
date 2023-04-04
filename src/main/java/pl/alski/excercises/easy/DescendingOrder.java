package pl.alski.excercises.easy;


public class DescendingOrder {
//    Your task is to make a function that can take any non-negative integer as an argument and return it with its digits in descending order. Essentially, rearrange the digits to create the highest possible number.
//            Examples:
//    Input: 42145 Output: 54421
//    Input: 145263 Output: 654321
//    Input: 123456789 Output: 987654321

    public static int sortDesc(final int num) {
            StringBuilder sb = new StringBuilder();
            Integer.toString(num).chars()
                    .map(Character::getNumericValue)
                    .map(a -> -a)
                    .sorted()
                    .map(a -> -a)
                    .forEach(sb::append);
            return Integer.parseInt(sb.toString());
    }
}
