package com.crack.snap.make.easy.stack;

import java.util.Stack;

/**
 * @author Mohan Sharma
 */
public class ValidParentheses {

    /**
     * Problem: Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * An input string is valid if:
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     * Every close bracket has a corresponding open bracket of the same type.
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) return false;

        var stack = new Stack<Character>();
        for (var letter: s.toCharArray()) {
            if (letter == '(' || letter == '{' || letter == '[')
                stack.push(letter);
            else {
                if (stack.isEmpty()) return false;
                var ch = stack.pop();
                if (letter == ')' && ch != '(') return false;
                if (letter == '}' && ch != '{') return false;
                if (letter == ']' && ch != '[') return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        var obj = new ValidParentheses();
        System.out.println(obj.isValid("()")); // true
        System.out.println(obj.isValid("()[]{}")); // true
        System.out.println(obj.isValid("(]")); // false
    }
}
