package me.vmachohan.assignment6;

import me.vmachohan.assignment6.calcLogic.expression.utils.ExpressionCalculator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class CalculatorState {


    private final StringBuilder lastToken = new StringBuilder();
    private StringBuilder displayString = new StringBuilder();
    private final List<String> tokens = new ArrayList<>();
    private boolean lastWithUnary = false;

    public String getDisplayString(){
        return displayString.toString();
    }

    public void parseNumber(String text) {
        lastToken.append(text);
        isNumber(lastToken.toString());
        displayString.append(String.format("%s", text));

    }

    public void parseFloatingPoint() {
        if(lastToken.toString().contains(".")){
            System.err.println("already contains floating point");
            return;
        }
        lastToken.append(".");
        displayString.append(",");
    }

    public void parseOperator(String op) {
        if (displayString.toString().contains("=") && tokens.size() == 1) {
            displayStringSetText(tokens.getFirst());
        }

        // unary minus
        if (isUnaryMinus(op)) {
            tokens.add("(");
            lastToken.append("-");
            lastWithUnary = true;
            displayString.append("(-");
            return;
        }

        //after first calc
        if (lastToken.isEmpty() && tokens.size() == 1) {
            tokens.add(op);
            displayString.setLength(0);
            displayString.append(tokens.getFirst()).append(" ").append(op).append(" ");
            return;
        }

        if (lastToken.isEmpty()
                && !tokens.isEmpty()
                && ")".equals(tokens.getLast())) {

            tokens.add(op);
            displayString.append(" " + op + " ");
            return;
        }

        // def op
        if (!lastToken.isEmpty()) {
            appendToken();
            tokens.add(op);
            displayString.append(" " + op + " ");
        }

    }

    public void parseEquals() {
        if(tokens.isEmpty())
            return;

        if(!lastToken.isEmpty())
            appendToken();

        ExpressionCalculator calculator = new ExpressionCalculator();
        System.out.println(tokens);

        double res = 0.0;

        try {
            res = calculator.calculate(tokens);
        }catch (ArithmeticException e){
            res = Double.NaN;
            throw e;
        }finally {
            tokens.clear();
            lastToken.setLength(0);
            tokens.add(String.valueOf(res));
            prepareSolved(res);
        }

    }

    private void appendToken() {
        tokens.add(lastToken.toString());
        //clear stringbuilder
        lastToken.setLength(0);

        if (lastWithUnary) {
            tokens.add(")");
            displayString.append(")");
            lastWithUnary = false;
        }
    }

    //-----helper methods----
    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            System.err.println("error");

            return false;
        }
    }

    private boolean isUnaryMinus(String op) {
        return op.equals("-") &&
                (lastToken.isEmpty()
                        && (tokens.isEmpty() ||
                        isOperator(tokens.getLast())));
    }

    private boolean isOperator(String t) {
        return "+-*/(".contains(t);
    }


    private void prepareSolved(double res) {
        displayString.setLength(0);
        displayString.append(String.format("= %.2f", res));
    }

    public void deleteLast() {
        if (!lastToken.isEmpty()) {
            lastToken.deleteCharAt(lastToken.length() - 1);
        } else if (!tokens.isEmpty()) {
            String removed = tokens.removeLast();
            lastToken.append(removed);
            lastToken.deleteCharAt(lastToken.length() - 1);
        }

        rebuildDisplayString();
    }

    public void rebuildDisplayString(){

            StringBuilder sb = new StringBuilder();

            for (String t : tokens) {
                sb.append(t).append(" ");
            }
            sb.append(lastToken);


            displayStringSetText(sb.toString());

    }

    public void displayStringSetText(String text){
        displayString.setLength(0);
        displayString.append(text);
    }

    public void clear() {
        tokens.clear();
        lastToken.setLength(0);
        lastWithUnary = false;

    }

    public void swapSign() {
        // while typing num
        if (!lastToken.isEmpty()) {
            wrapUnary(lastToken.toString());
            lastToken.setLength(0);
            rebuildDisplayString();
            return;
        }

        // after eq
        if (tokens.size() == 1 && isNumber(tokens.getFirst())) {
            String v = tokens.removeFirst();
            tokens.add("(");
            tokens.add("-" + v);
            tokens.add(")");
            rebuildDisplayString();
        }
    }

    private void wrapUnary(String value) {
        tokens.add("(");
        tokens.add("-" + value);
        tokens.add(")");
        lastWithUnary = false;
    }
}
