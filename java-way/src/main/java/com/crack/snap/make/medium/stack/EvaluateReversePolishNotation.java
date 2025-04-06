package com.crack.snap.make.medium.stack;

import java.util.List;
import java.util.Stack;

/**
 * @author Mohan Sharma
 */
public class EvaluateReversePolishNotation {

    public int evalRPN(String[] tokens) {
        var stack = new Stack<Integer>();
        for (var token: tokens) {
            if (isOperator(token)) {
                var b = stack.pop();
                var a = stack.pop();
                stack.push(calculate(a, b, token));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    private boolean isOperator(final String token) {
        var operators = List.of("+", "-", "*", "/");
        return operators.contains(token);
    }

    private int calculate(final Integer a, final Integer b, final String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalStateException("Unexpected operator: " + operator);
        };
    }

    public static void main(String[] args) {
        var tokens = new String[]{"2", "1", "+", "3", "*"};
        var evaluator = new EvaluateReversePolishNotation();
        System.out.println(evaluator.evalRPN(tokens)); // Output: 9
    }
}
