package dimitrios.github.ihatejavafxcalculator;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

public class ColorController {
    public GridPane buttonsBackground;

    public ColorController(GridPane buttonsBackground) {
        this.buttonsBackground = buttonsBackground;
    }

    public void paintRed(ActionEvent actionEvent) {
        buttonsBackground.setStyle("-fx-background-color: red");
    }

    public void paintBlue(ActionEvent actionEvent) {
        buttonsBackground.setStyle("-fx-background-color: blue");
    }

    public void paintGreen(ActionEvent actionEvent) {
        buttonsBackground.setStyle("-fx-background-color: green");
    }

    public void resetColor(ActionEvent actionEvent) {
        buttonsBackground.setStyle("");
    }
}
