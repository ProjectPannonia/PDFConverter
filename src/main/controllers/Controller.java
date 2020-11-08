package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.service.PDFtoImage;

import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    Button P1ConvertButton, P1MultipleFileChooser, P1ChooseDestinationFolderBtn;

    @FXML
    Label P1ResultLabel, P1SelectedPDFFile, P1ChooseDestinationFolderLb;

    @FXML
    public void p1Convert(ActionEvent e) {
        String pdfChooserLabel = P1SelectedPDFFile.getText();
        String destinationLabel = P1ChooseDestinationFolderLb.getText() + "\\";
        if (pdfChooserLabel != null && pdfChooserLabel != "" && destinationLabel != null && destinationLabel != "") {
            System.out.println("kezdem");
            P1ResultLabel.setText("Working");
            System.out.println(pdfChooserLabel + ", " + destinationLabel);
            PDFtoImage.convertToImages(pdfChooserLabel,"JPG",destinationLabel);
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
    @FXML
    public void singlePDFChooser(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
        File f = fc.showOpenDialog(null);
        if(f != null) {
            P1SelectedPDFFile.setText(f.getAbsolutePath());
        }
    }
    @FXML
    public void singleDirectoryChooser(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            String destinationFolderPath = directory.getAbsolutePath();
            P1ChooseDestinationFolderLb.setText(destinationFolderPath);
        }
    }
}
