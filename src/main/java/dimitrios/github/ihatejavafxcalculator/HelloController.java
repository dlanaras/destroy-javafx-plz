package dimitrios.github.ihatejavafxcalculator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static javafx.scene.input.KeyCode.*;

public class HelloController {
    private final String operations = ".+-*/C√=+/-";
    private ArrayList<String> valuesEntered = new ArrayList<>();
    public Text resultsText;
    public GridPane buttonsBackground;
    private double res = 0;
    private boolean dotPressed = false;
    private String lastOperation = "";
    private String preOperationNum = "";

    public Button equals;
    public Button dot;
    public Button star;
    public Button slash;
    public Button cross;
    public Button dash;
    public Button one;
    public Button two;
    public Button three;
    public Button four;
    public Button five;
    public Button six;
    public Button seven;
    public Button eight;
    public Button nine;
    public Button zero;

    @FXML
    public void pressNumber(ActionEvent actionEvent) {
        Button pressedButt = ((Button) actionEvent.getSource());
        valuesEntered.add(pressedButt.getText());

        if(valuesEntered.size() == 1) {
            resultsText.setText(pressedButt.getText());
        } else {
            if(!lastOperation.equals("")) { //pressing another number
                try {
                    calculate(lastOperation, true);
                } catch (InvalidOperationException e) {
                    resultsText.setText("Invalid Operation!");
                } catch (DivisionByZeroException e) {
                    resultsText.setText("Cannot divide by 0");
                } catch (Exception e) {
                    resultsText.setText("Something went wrong");
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            } else {
                    resultsText.setText(resultsText.getText() + pressedButt.getText());
            }
        }
    }

    @FXML
    public void pressOperation(ActionEvent actionEvent) {
        Button pressedButt = ((Button) actionEvent.getSource());
        if(dotPressed) {
            dotPressed = false;
            setNumberFromOperation(".");
        }

        if(lastOperation.equals("")) {
            valuesEntered.add(resultsText.getText());
            preOperationNum = resultsText.getText();
            System.out.println(resultsText.getText() + " CURRENT TEXT");
        }
        valuesEntered.add(pressedButt.getText());


        try {

            calculate(pressedButt.getText(), false);
        } catch (InvalidOperationException e) {
            resultsText.setText("Invalid Operation!");
        } catch (DivisionByZeroException e) {
            resultsText.setText("Cannot divide by 0");
        } catch (Exception e) {
            resultsText.setText("Something went wrong");
            System.out.println(e);
        }
    }

    private void setNumberFromOperation(String operation) {
        StringBuilder str = new StringBuilder();
        for (int i = valuesEntered.size()-1; i >= 0; i--) {
            if(valuesEntered.get(i).equals(operation)) {

                if(operation.equals(".")) {
                    str.append(valuesEntered.get(i-1));

                    for (int j = i; j < valuesEntered.size(); j++) {
                        str.append(valuesEntered.get(j));
                    }
                } else {
                    for (int j = i+1; j < valuesEntered.size()-1; j++) {
                        str.append(valuesEntered.get(j));
                    }
                }
                valuesEntered.add(str.toString());
                break;


            }
        }
    }

    private boolean isOperation(String value) {
        return operations.contains(value);
    }

    private String getPreviousValue() {
        return valuesEntered.get(valuesEntered.size()-2);
    }

    private String getValue(int goBack) {
        return valuesEntered.get(valuesEntered.size()-goBack);
    }

    private void calculate(String textOperation, boolean postOperation) throws InvalidOperationException, DivisionByZeroException {
            switch (textOperation) {
                case ".":
                    this.handleDot();
                    break;
                case "+":
                    this.handleCross(postOperation);
                    break;
                case "-":
                    this.handleDash(postOperation);
                    break;
                case "*":
                    this.handleStar(postOperation);
                    break;
                case "/":
                    this.handleSlash(postOperation);
                    break;
                case "C":
                    this.handleCee();
                    break;
                case "√":
                    this.handleSqrt();
                    break;
                case "=":
                    this.handleEquals();
                    break;
                case "+/-":
                    this.handleCrossSlashDash();
                    break;
                default:
                    throw new InvalidOperationException();
            }
        }

    private void handleDot() throws InvalidOperationException {
        if(this.isOperation(getPreviousValue())) {
            throw new InvalidOperationException();
        } else {
            dotPressed = true;
            resultsText.setText(getPreviousValue() + ".");
        }
    }

    private void handleCross(boolean numberEntered) {
        if(numberEntered) {
            resultsText.setText(resultsText.getText() + getValue(1));
        } else {
            lastOperation = "+";
            resultsText.setText(getPreviousValue() + "+");
        }
    }

    private void handleDash(boolean numberEntered) {
        if(numberEntered) {
            resultsText.setText(resultsText.getText() + getValue(1));
        } else {
            lastOperation = "-";
            resultsText.setText(getPreviousValue() + "-");
        }
    }

    private void handleStar(boolean numberEntered) {
        if(numberEntered) {
            resultsText.setText(resultsText.getText() + getValue(1));
        } else {
            lastOperation = "*";
            resultsText.setText(getPreviousValue() + "*");
        }
    }

    private void handleSlash(boolean numberEntered) {
        if(numberEntered) {
            resultsText.setText(resultsText.getText() + getValue(1));
        } else {
            lastOperation = "/";
            resultsText.setText(getPreviousValue() + "/");
        }
    }

    private void handleCee() {
        resultsText.setText("Enter a Number:");
        valuesEntered.clear();
    }

    private void handleSqrt() {
        resultsText.setText("√" + resultsText.getText());
        res = Math.sqrt(Double.parseDouble(getPreviousValue()));
    }

    private void handleCrossSlashDash() {
        resultsText.setText("Function not implemented yet");
    }

    private void handleEquals() throws DivisionByZeroException {
        setNumberFromOperation(lastOperation);
        //System.out.println(valuesEntered.get(valuesEntered.size()-1) + " newest number" + " " + preOperationNum + " old num");
        this.tryCalculateTwoNumberOperation();
        lastOperation = "";
    }

    private void tryCalculateTwoNumberOperation() throws DivisionByZeroException {
        switch (lastOperation) {
            case "+":
                res = Double.parseDouble(preOperationNum) + Double.parseDouble(getValue(1));
                break;
            case "-":
                res = Double.parseDouble(preOperationNum) - Double.parseDouble(getValue(1));
                break;
            case "*":
                res = Double.parseDouble(preOperationNum) * Double.parseDouble(getValue(1));
                break;
            case "/":
                if(getValue(1).equals("0")) throw new DivisionByZeroException();
                res = Double.parseDouble(preOperationNum) / Double.parseDouble(getValue(1));
                break;
        }
        resultsText.setText(Double.toString(res));
        valuesEntered.add(Double.toString(res));
    }

    public void setGlobalKeyPressed(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case DIGIT0:
                        zero.fire();

                        break;
                    case DIGIT1:
                        one.fire();

                        break;
                    case DIGIT2:
                        two.fire();

                        break;
                    case DIGIT3:
                        three.fire();

                        break;
                    case DIGIT4:
                        four.fire();

                        break;
                    case DIGIT5:
                        five.fire();

                        break;
                    case DIGIT6:
                        six.fire();

                        break;
                    case DIGIT7:
                        seven.fire();

                    case DIGIT8:
                        eight.fire();

                        break;
                    case DIGIT9:
                        nine.fire();

                        break;
                    case PLUS:
                        cross.fire();

                        break;
                    case MINUS:
                        dash.fire();

                        break;
                    case MULTIPLY:
                        star.fire();

                        break;
                    case DIVIDE:
                        slash.fire();

                        break;
                    case ENTER:
                        equals.fire();

                        break;
                    case PERIOD:
                        dot.fire();

                        break;
            }
            }
        });
    }

    @FXML
    private void backGroundWindow() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("colorpicker.fxml"));
        fxmlLoader.setController(new ColorController(buttonsBackground));
        Scene scene = new Scene(fxmlLoader.load());

        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setScene(scene);
        newWindow.setTitle("Choose Your (Pokemon) I mean colour");

        newWindow.show();
    }

    public void focusOnEquals() {
        equals.requestFocus();
    }
}