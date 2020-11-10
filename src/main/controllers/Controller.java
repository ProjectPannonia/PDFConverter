package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.service.ImageSplitter;
import main.service.PDFtoImage;

import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    Button  P1ConvertButton,
            P1ChooseDestinationFolderBtn,
            P2ChooseOneFileBtn,
            P2ChooseMultipleFilesBtn,
            P2SplitImage,
            P2SelectConversionPath;
    @FXML
    Label   P1ResultLabel,
            P1SelectedPDFFile,
            P1ChooseDestinationFolderLb,
            P2ChooseFile,
            P2DestinationPathLb,
            P2ChooseDestinationPath;

    @FXML
    public void p1Convert(ActionEvent e) {
        String pdfChooserLabel = P1SelectedPDFFile.getText();
        String destinationLabel = P1ChooseDestinationFolderLb.getText() + "\\";

        if (pdfChooserLabel != null && pdfChooserLabel != "" && destinationLabel != null && destinationLabel != "") {
            P1ResultLabel.setText("Working");
            System.out.println(pdfChooserLabel + ", " + destinationLabel);
            PDFtoImage.convertToImages(pdfChooserLabel,"JPG",destinationLabel);
        }
    }
    @FXML
    public void p1multiFileChooser(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        List<File> f = fc.showOpenMultipleDialog(null);
        for(File file : f) {
            System.out.println(file.getAbsolutePath());
        }
    }
    @FXML
    public void p1singlePDFChooser(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
        File f = fc.showOpenDialog(null);
        if(f != null) {
            P1SelectedPDFFile.setText(f.getAbsolutePath());
        }
    }
    @FXML
    public void p1singleDirectoryChooser(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            String destinationFolderPath = directory.getAbsolutePath();
            P1ChooseDestinationFolderLb.setText(destinationFolderPath);
        }
    }
    @FXML
    public void p2ChooseFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if(directory != null) {
            pathToDirectory = directory.getAbsolutePath() + "\\";
            P2ChooseFile.setText(pathToDirectory);
        }
    }
    @FXML
    public void p2SelectDestination(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File direcotry = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if (direcotry != null) {
            pathToDirectory = direcotry.getAbsolutePath() + "\\";
            P2DestinationPathLb.setText(pathToDirectory);

        }
    }
    @FXML
    public void p2SplitImage(ActionEvent e) {
        String pathToFile = P2ChooseFile.getText();
        String conversionDestinationPath = P2DestinationPathLb.getText();
        if (pathToFile != null) {
            ImageSplitter.splitImage(pathToFile,conversionDestinationPath);
        }

    }

}
