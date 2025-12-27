package me.vmachohan.assignment6;

import me.vmachohan.assignment6.calcLogic.expression.utils.ExpressionCalculator;

import java.util.ArrayList;
import java.util.List;

public class CalculatorState {

    private final List<String> tokens = new ArrayList<>();
    private final StringBuilder lastToken = new StringBuilder();

    private boolean isCalculated = false;


    public String getDisplayString() {
        StringBuilder sb = new StringBuilder();

        for (String t : tokens) {
            sb.append(t).append(" ");
        }

        if (!lastToken.isEmpty()) {
            sb.append(lastToken);
        }

        if (sb.isEmpty())
            return "";

        return sb.toString();
    }

    public void parseNumber(String text) {
        if (isCalculated) {
            clear();
        }
        lastToken.append(text);
    }

    public void parseFloatingPoint() {
        if (isCalculated) {
            clear();
            lastToken.append("0.");
            return;
        }

        if (lastToken.toString().contains(".")) {
            return;
        }

        if (lastToken.isEmpty()) {
            lastToken.append("0");
        }
        lastToken.append(".");
    }

    public void parseOperator(String op) {
        isCalculated = false;


        if (op.equals("-") && lastToken.isEmpty()) {
            lastToken.append("-");
            return;
        }


        if (!lastToken.isEmpty()) {
            if (lastToken.toString().equals("-")) return;

            tokens.add(lastToken.toString());
            lastToken.setLength(0);
        }

        if (tokens.isEmpty()) {
            return;
        }

        String lastStored = tokens.get(tokens.size() - 1);
        if (isOperator(lastStored)) {
            tokens.set(tokens.size() - 1, op);
        } else {
            tokens.add(op);
        }
    }

    public void parseEquals() {
        if (tokens.isEmpty() && lastToken.isEmpty()) return;

        if (!lastToken.isEmpty()) {
            if (lastToken.toString().equals("-")) return;
            tokens.add(lastToken.toString());
            lastToken.setLength(0);
        }

        System.out.println(tokens);
        ExpressionCalculator calculator = new ExpressionCalculator();

        try {
            double res = calculator.calculate(new ArrayList<>(tokens));
            tokens.clear();

            if (res % 1 == 0) {
                lastToken.append((int) res);
            } else {
                lastToken.append(res);
            }

            isCalculated = true; // Set flag
        } catch (ArithmeticException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Calculation error: " + e.getMessage());
        }
    }

    public void swapSign() {
        if (isCalculated) {
            isCalculated = false;
        }

        if (lastToken.isEmpty()) {
            lastToken.append("-");
            return;
        }

        if (lastToken.charAt(0) == '-') {
            lastToken.deleteCharAt(0);
        } else {
            lastToken.insert(0, "-");
        }
    }

    public void deleteLast() {
        isCalculated = false;

        if (!lastToken.isEmpty()) {
            lastToken.deleteCharAt(lastToken.length() - 1);
        } else if (!tokens.isEmpty()) {
            String removed = tokens.remove(tokens.size() - 1);
            lastToken.append(removed);
            if (!lastToken.isEmpty()) {
                lastToken.deleteCharAt(lastToken.length() - 1);
            }
        }
    }

    public void clear() {
        tokens.clear();
        lastToken.setLength(0);
        isCalculated = false;
    }

    //----- Helpers ----
    private boolean isOperator(String t) {
        return "+-*/".contains(t);
    }
}