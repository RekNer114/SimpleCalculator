package me.vmachohan.assignment6;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import me.vmachohan.assignment6.calcLogic.expression.utils.ExpressionCalculator;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    TextField out;
    @FXML
    TextField out1;

    private final StringBuilder lastToken = new StringBuilder();
    private final List<String> tokens = new ArrayList<>();
    private boolean lastWithUnary = false;


    public void onNumberPartPressed(ActionEvent e){
        String text = ( (Button) e.getSource() ).getText();

        lastToken.append(text);

        isNumber(lastToken.toString());

        out.appendText(String.format("%s", text));


    }

    public void onFloatingPointPressed(){
        if(lastToken.toString().contains(".")){
            System.err.println("already contains floating point");
            return;
        }
        lastToken.append(".");

        out.appendText(",");

    }

    public void appendOperator(ActionEvent e){
        String op = ( (Button) e.getSource() ).getText();

        if (out.getText().contains("=") && !tokens.isEmpty())
            out.setText(tokens.getFirst());

        // unary minus
        if (isUnaryMinus(op)) {
            tokens.add("(");
            lastToken.append("-");
            lastWithUnary = true;
            out.appendText("(-");
            return;
        }

        //after first calc
        if (lastToken.isEmpty() && tokens.size() == 1) {
            tokens.add(op);
            out.setText(tokens.getFirst() + " " + op + " ");
            return;
        }

        if (lastToken.isEmpty()
                && !tokens.isEmpty()
                && ")".equals(tokens.getLast())) {

            tokens.add(op);
            out.appendText(" " + op + " ");
            return;
        }

        // def op
        if (!lastToken.isEmpty()) {
            appendToken();
            tokens.add(op);
            out.appendText(" " + op + " ");
        }
    }


    public void onEqualsPressed()
    {
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
           displayErrorMessage(e.getMessage());
           res = Double.NaN;
        }

        tokens.clear();
        lastToken.setLength(0);
        tokens.add(String.valueOf(res));

        prepareSolved(res);

    }

    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid expression");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void appendToken() {
        tokens.add(lastToken.toString());
        //clear stringbuilder
        lastToken.setLength(0);

        if (lastWithUnary) {
            tokens.add(")");
            out.appendText(")");
            lastWithUnary = false;
        }
    }

    private void prepareSolved(double res) {
        out1.setText(out.getText());
        out.setText(String.format("= %.2f", res));

    }

    public void onDeletePressed(){
        if (lastToken.length() > 0) {
            lastToken.deleteCharAt(lastToken.length() - 1);
        } else if (!tokens.isEmpty()) {
            String removed = tokens.removeLast();
            lastToken.append(removed);
            lastToken.deleteCharAt(lastToken.length() - 1);
        }

        rebuildOut();
    }

    private void rebuildOut() {
        StringBuilder sb = new StringBuilder();

        for (String t : tokens) {
            sb.append(t).append(" ");
        }
        sb.append(lastToken);

        out.setText(sb.toString());
    }

    public void onClearPressed(){
        tokens.clear();
        lastToken.setLength(0);
        out.setText("");
        out1.setText("");
        lastWithUnary = false;
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

    public void onSwapSignPressed(ActionEvent actionEvent) {
        // while typing num
        if (!lastToken.isEmpty()) {
            wrapUnary(lastToken.toString());
            lastToken.setLength(0);
            rebuildOut();
            return;
        }

        // after eq
        if (tokens.size() == 1 && isNumber(tokens.getFirst())) {
            String v = tokens.removeFirst();
            tokens.add("(");
            tokens.add("-" + v);
            tokens.add(")");
            rebuildOut();
        }
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            System.err.println("error");

            return false;
        }
    }


    private void wrapUnary(String value) {
        tokens.add("(");
        tokens.add("-" + value);
        tokens.add(")");
        lastWithUnary = false;
    }

}
