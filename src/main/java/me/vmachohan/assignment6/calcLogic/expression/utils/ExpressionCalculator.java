package me.vmachohan.assignment6.calcLogic.expression.utils;



import javafx.geometry.Pos;
import me.vmachohan.assignment6.calcLogic.functions.FunctionMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static me.vmachohan.assignment6.calcLogic.Constants.*;


/**
 * Class that calculate final result of the expression
 */
public class ExpressionCalculator {

    private PostFixConverter converter = new PostFixConverter();


    public double calculate(List<String> stdtokens) {
        List<String> tokens =  converter.convertToPostFix(stdtokens);

        //solving stack
        Stack<String> solve = new Stack<>();

        //go through each token in expression
        for (String s : tokens) {
            //if element isn't an operator we push it to solve stack
            if (!isOperator(s) && !FunctionMapper.isFunction(s)) {
                solve.push(s);
            } else {
                //if there is only 1 element left in the stack
                //and if there is minus, just negate last number
                if (solve.size() == 1 && s.equals(MINUS)) {
                    solve.push(String.valueOf(
                            -Double.parseDouble(solve.pop())
                    ));
                } else {
                    //if there is more, apply default operation
                    if (isOperator(s))
                        solve.push(String.valueOf(
                                applyOperator(s,
                                        Double.parseDouble(solve.pop()),
                                        Double.parseDouble(solve.pop()))));
                    else
                        solve.push(String.valueOf(
                                FunctionMapper.calculate(s, Double.parseDouble(solve.pop()))
                        ));
                }
            }
        }
        //return final result
        return Double.parseDouble(solve.peek());
    }


    /**
     * Method that apply provided operator to the provided numbers.
     *
     * @param operator operator for 2 numbers
     * @param a        number from expression to process
     * @param b        number from expression to process
     * @return result of operation with that 2 numbers
     */
    private double applyOperator(String operator, double a, double b) {
        return switch (operator) {
            case PLUS -> b + a;
            case MINUS -> b - a;
            case MULTIPLICATION -> b * a;
            case DIVISION -> {
                if (a == 0)
                    //handle case when user try to divide by 0;
                    throw new ArithmeticException("can't divide by 0");
                else
                    //use big decimal to save precision for repeated decimals
                    yield b / a;
            }
            case "^" -> Math.pow(b, a);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }


    /**
     * Method to check if provided token is operator
     *
     * @param token element of the expression
     * @return true if provided token is operator
     */
    private boolean isOperator(String token) {
        return token.matches(SIGN_REGEX);
    }

}
