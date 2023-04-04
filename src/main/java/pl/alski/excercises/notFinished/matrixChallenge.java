//package pl.alski.excercises;
//
////function takes as parameter 2D array of 0 and 1's.
////determine the area of the largest rectangular submatrix that contains all 1's
//// For exammple
//// 10100
//// 10111
//// 11111
//// 10010
//// should return 2x3= 6
//// you can assume input will not be empty
////examples new String[]{"1011","0011", "0111", "1111"} output: 8
///// new String[] {"101" "111", "001"} output 3
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class matrixChallenge {
//    String[] strArr = new String[]{"10100", "10111", "11111", "10010"};
//    int width = strArr[0].length();
//    List<Integer> results = new ArrayList<>();
//
//    //loop through row
//    public void countOnes(String[] strArr) {
//
//        for (int a = 0; a < strArr.length; a++) {
//
//            for (int i = 0; i < width; i++) {
//                int result= 0;
//                if (strArr[0].contains("1")) result =1;
//                if (i < width - 1){
//                    if ( (strArr[a].charAt(i) == 1 && strArr[a].charAt(i+1) == 1)) {
//                        result+=1;
//                    }
//                    else {
//                        results.add(result);
//                        if (a<strArr.length-1 &&
//                    }
//                }
//            }
//        } ;
//    }
//
//
//}
