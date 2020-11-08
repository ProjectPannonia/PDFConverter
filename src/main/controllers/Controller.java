package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.FileChooser;
import main.service.CheckConversionSuccesfulness;
import main.service.PDFtoImage;

import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    TextField P1PathToPDFtf, P1PathToResulttf;

    @FXML
    Button P1ConvertButton, P1MultipleFileChooser;

    @FXML
    Label P1ResultLabel;

    @FXML
    public void p1Convert(ActionEvent e) {
        String sourcePath = P1PathToPDFtf.getText();
        String destinationPath = P1PathToResulttf.getText();
        String response = "Working!";
        boolean ran = PDFtoImage.convertToImages(sourcePath,"JPG",destinationPath);

        while (!ran) {
            response = CheckConversionSuccesfulness.isWritingSuccesfully(destinationPath);
        }
    }
    @FXML
    public void multiFileChooser(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        List<File> f = fc.showOpenMultipleDialog(null);
        for(File file : f) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
