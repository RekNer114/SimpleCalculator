package me.vmachohan.assignment6.calcLogic.expression.utils;


import me.vmachohan.assignment6.calcLogic.functions.FunctionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static me.vmachohan.assignment6.calcLogic.Constants.*;

/**
 * Utility class that convert(sort) list of the tokens to the postfix notation
 * to make calculation easier later.
 */
public class PostFixConverter {
    /**
     * Converts token representation of the expression to the postfix notation
     *
     * @param tokens source list for conversion
     * @return list with provided tokens, but in postfix notation
     */
    public List<String> convertToPostFix(List<String> tokens) {
        // arraylist to save result
        ArrayList<String> out = new ArrayList<>();
        // stack to hold operators
        Stack<String> hold = new Stack<>();

        // go through each element of the expression
        for (String s : tokens) {
            if (isOperand(s)) {
                // we add it to the result
                out.add(s);
                // if it's "(" we add it to the stack as a break point for situation when we'll
                // reach ')'
            } else if (s.equals(OPEN_BRACKET)) {
                hold.push(s);
            } else if (s.equals(CLOSE_BRACKET)) {
                // while last element in stack not equals "("
                while (!hold.peek().equals(OPEN_BRACKET)) {
                    // add to the result last result
                    out.add(hold.pop());
                }
                // pop stack to remove "("
                hold.pop();
            } else {
                // and if it's operator
                // check if it's not empty and precedence of the current operator is greater
                // than others then
                while (!hold.isEmpty() && getPrecedence(hold.peek()) >= getPrecedence(s)) {
                    // add to the output
                    out.add(hold.pop());
                }
                // and push current operator
                hold.push(s);
            }
        }

        // in the end, add all operators that is in the hold to the result
        while (!hold.isEmpty()) {
            out.add(hold.pop());
        }

        return out;
    }


    /**
     * Method to get precedence of the provided operator token.
     *
     * @param op token that will be checked
     * @return precedence of current operator. if it's not operator, returns -1
     */
    private int getPrecedence(String op) {
        return switch (op) {
            case PLUS, MINUS -> 1;
            case MULTIPLICATION, DIVISION -> 2;
            case EXPONENT -> 3;
            case SIN, COS, TAN, ATAN, LOG10, LOG2, SQRT -> 4;
            default -> -1;
        };
    }


    /**
     * Checks if provided token is an operand(variable or number)
     *
     * @param token token to check
     * @return true if provided token if an operand, otherwise - false.
     */
    private boolean isOperand(String token) {
        return !token.matches(SIGN_REGEX) && !FunctionMapper.isFunction(token) &&
                !token.equals(OPEN_BRACKET) && !token.equals(CLOSE_BRACKET);
    }

}
