module dimitrios.github.ihatejavafxcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens dimitrios.github.ihatejavafxcalculator to javafx.fxml;
    exports dimitrios.github.ihatejavafxcalculator;
}