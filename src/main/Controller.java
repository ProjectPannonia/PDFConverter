package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    TextField P1PathToPDFtf, P1PathToResulttf;

    @FXML
    Button P1ConvertButton;

    @FXML
    Label P1ResultLabel;

    @FXML
    public void p1Convert(ActionEvent e) {
        String sourcePath = P1PathToPDFtf.getText();
        String destinationPath = P1PathToResulttf.getText();
        String conversionResult;
    }
}
