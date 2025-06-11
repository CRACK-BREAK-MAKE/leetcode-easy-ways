package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class LetterCombinationPhoneNumber {

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return List.of();
        }
        var result = new ArrayList<String>();
        var digitToLetters = new String[]{ "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        generateCombinationsBacktracking(digits, 0, new StringBuilder(), result, digitToLetters);
        return result;
    }

    private void generateCombinationsBacktracking(String digits, int index, StringBuilder sb, List<String> result, String[] digitToLetters) {
        if (index == digits.length()) {
            result.add(sb.toString());
            return;
        }

        var word = digitToLetters[digits.charAt(index) - '0'];
        for (var letter :  word.toCharArray()) {
            sb.append(letter);
            generateCombinationsBacktracking(digits, index + 1, sb, result, digitToLetters);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }
    }

    public static void main(String[] args) {
        var obj = new LetterCombinationPhoneNumber();
        System.out.println(obj.letterCombinations("23")); // Output ["ad","ae","af","bd","be","bf","cd","ce","cf"]
        System.out.println(obj.letterCombinations("2")); // Output ["a","b","c"]
    }
}
