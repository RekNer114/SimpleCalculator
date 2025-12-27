package me.vmachohan.assignment6;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class CalculatorController {

    @FXML
    TextField out;
    @FXML
    TextField out1;

    private final CalculatorState calculatorState = new CalculatorState();

    public void onNumberPartPressed(ActionEvent e){
        String text = ( (Button) e.getSource() ).getText();
        calculatorState.parseNumber(text);
        refresh();
    }

    public void onFloatingPointPressed(){
        calculatorState.parseFloatingPoint();
        refresh();
    }

    public void appendOperator(ActionEvent e){
        String op = ( (Button) e.getSource() ).getText();
        calculatorState.parseOperator(op);
        refresh();
    }


    public void onEqualsPressed()
    {
        try {
            out1.setText(out.getText());
            calculatorState.parseEquals();
        }catch (ArithmeticException e){
            displayErrorMessage(e.getMessage());
        }finally {
            refresh();
        }

    }

    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid expression");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onDeletePressed(){
       calculatorState.deleteLast();
       refresh();
    }

    public void onClearPressed(){
        calculatorState.clear();
        out.setText("");
        out1.setText("");
    }

    public void onSwapSignPressed(ActionEvent actionEvent) {
        calculatorState.swapSign();
        refresh();
    }

    public void refresh(){
        out.setText(calculatorState.getDisplayString());
    }
}
