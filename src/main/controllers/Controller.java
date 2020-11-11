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
            P2ChooseMultipleFilesBtn,
            P2SplitImage,
            P2SelectConversionPath;
    @FXML
    Label   P1OriginalPDFFilePathLabel,
            P1ChooseDestinationFolderLb,
            P2ChooseSourceFolderLb,
            P2ChooseDestinationPath;

    // PDF TO IMAGE
    @FXML
    public void p1ChooseSourcePDF(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
        File f = fc.showOpenDialog(null);
        if(f != null) {
            P1OriginalPDFFilePathLabel.setText(f.getAbsolutePath());
        }
    }
    @FXML
    public void p1ChooseConversionDestinationFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            String destinationFolderPath = directory.getAbsolutePath();
            P1ChooseDestinationFolderLb.setText(destinationFolderPath);
        }
    }
    @FXML
    public void p1Convert(ActionEvent e) {
        String pathToPDFFile = P1OriginalPDFFilePathLabel.getText();
        String pathToConversionDestination = P1ChooseDestinationFolderLb.getText() + "\\";

        if (pathToPDFFile != null && pathToPDFFile != "" && pathToConversionDestination != null && pathToConversionDestination != "") {
            System.out.println(pathToPDFFile + ", " + pathToConversionDestination);
            PDFtoImage.convertToImages(pathToPDFFile,"JPG",pathToConversionDestination);
        }
    }

    // SPLIT IMAGES
    @FXML
    public void p2ChooseSourceImagesFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if(directory != null) {
            pathToDirectory = directory.getAbsolutePath() + "\\";
            P2ChooseSourceFolderLb.setText(pathToDirectory);
        }
    }
    @FXML
    public void p2ChooseConversionDestinationFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File direcotry = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if (direcotry != null) {
            pathToDirectory = direcotry.getAbsolutePath() + "\\";
            P1ChooseDestinationFolderLb.setText(pathToDirectory);

        }
    }
    @FXML
    public void p2SplitImagesBtn(ActionEvent e) {
        String pathToFile = P2ChooseSourceFolderLb.getText();
        String conversionDestinationPath = P2ChooseDestinationPath.getText();
        if (pathToFile != null) {
            ImageSplitter.splitImage(pathToFile,conversionDestinationPath);
        }

    }

}
