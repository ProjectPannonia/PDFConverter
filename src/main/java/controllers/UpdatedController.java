package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import service.panes.imageslicer.ImageSlicer;
import service.panes.mergeimagesintopdf.WriteImagesIntoFile;
import service.panes.mergeimagesintopdf.pdfmodel.PdfFile;
import service.panes.mergeimagesintopdf.read.ReadSourceImages;
import service.panes.pdftoimage.DataForImageGeneration;
import service.panes.pdftoimage.PdfToImage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatedController implements Initializable {

    private ObservableList<String> imageFormats, targetDpi;
    private ObservableList<PdfFile> sourceFilesForTable;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private VBox MainVerticalBox;

    @FXML
    private Pane MainPaneLogo;

    /**
     Main-menu buttons
                    **/
    @FXML
    private Button  MainButtonPDFToImage,
                    MainButtonImageSplitter,
                    MainButtonMergeImages,
                    MainButtonReadText,
                    MainButtonExit;

    /**
     Content panes
                **/
    @FXML
    private GridPane    P1GridPanePDFToImage,
                        P2GridPaneImageSlicer,
                        P3GridPaneMergeImages,
                        P4GridPaneReadText;


    /**
     P1 -> PDF to images
                        **/

    /* Buttons */
    @FXML
    private Button  P1ButtonChoosePDF,
                    P1ButtonChooseDestinationFolder,
                    P1ButtonConvert;
    /* Labels */
    @FXML
    private Label   P1LabelSourcePDFPath,
                    P1LabelDestinationFolderPath;
    /* Choice boxes */
    @FXML
    private ChoiceBox<String>   P1ChoiceBoxDestinationFormat,
                                P1ChoiceBoxDestinationDPI;
    /* Checkbox */
    @FXML
    private CheckBox P1CheckBoxSliceImage;


    /**
     P2 -> Image slicer
                        **/
    /* Buttons */
    @FXML
    private Button  P2ButtonChooseImagesFolder,
                    P2ButtonChooseDestinationFolder,
                    P2ButtonSliceImages;
    /* Labels */
    @FXML
    private Label   P2LabelImagesFolderPath,
                    P2LabelDestinationFolderPath;


    /**
     P3 -> Merge images into pdf
                                **/
    /* Buttons */
    @FXML
    private Button  P3ButtonChooseImagesFolder,
                    P3ButtonChooseDestinationFolder,
                    P3ButtonConvert;
    /* Labels */
    @FXML
    private Label   P3LabelSourceImagesPath,
                    P3LabelChooseConversionDestination;
    @FXML
    private TextField P3TextFieldDestinationName;
    @FXML
    private CheckBox P3CheckBoxSplitImage;

    //
    @FXML
    TableView<PdfFile> P3TableViewImagesToMerge = new TableView<PdfFile>();

    @FXML
    TableColumn<Object, Object>     P3TableColumnSourceImageId,
                                    P3TableColumnSourceImageName,
                                    P3TableColumnSourceImageFormat,
                                    P3TableColumnSourceImagePath;

    //
    /**
     P4 -> Read text from images
                                **/



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize default image formats
        imageFormats = FXCollections.observableArrayList("JPG","JPEG","PNG","TIF","TIFF","GIF","BMP");
        P1ChoiceBoxDestinationFormat.setItems(imageFormats);

        P1ChoiceBoxDestinationFormat.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());

        // Initialize default DPI values
        targetDpi = FXCollections.observableArrayList("100", "200", "300", "400", "500", "600");
        P1ChoiceBoxDestinationDPI.setItems(targetDpi);

        // Initialize an empty table for pane 3
        sourceFilesForTable = FXCollections.observableArrayList();
        P3TableViewImagesToMerge.setEditable(true);
        P3TableColumnSourceImageId.setCellValueFactory(new PropertyValueFactory<>("id"));
        P3TableColumnSourceImageName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        P3TableColumnSourceImageFormat.setCellValueFactory(new PropertyValueFactory<>("format"));
        P3TableColumnSourceImagePath.setCellValueFactory(new PropertyValueFactory<>("path"));
        P3TableViewImagesToMerge.setItems(sourceFilesForTable);

        P1GridPanePDFToImage.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
        P1GridPanePDFToImage.toFront();

        P1ButtonChooseDestinationFolder.setDisable(true);
        P1ButtonConvert.setDisable(true);
        P1ChoiceBoxDestinationFormat.setDisable(true);
        P1ChoiceBoxDestinationDPI.setDisable(true);

    }
    @FXML
    public void handleMainMenuClicks(ActionEvent event) {
        if(event.getSource() == MainButtonPDFToImage) {
            P1GridPanePDFToImage.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            P1GridPanePDFToImage.toFront();
        } else if(event.getSource() == MainButtonImageSplitter) {
            P2GridPaneImageSlicer.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            P2GridPaneImageSlicer.toFront();
        } else if(event.getSource() == MainButtonMergeImages) {
            P3GridPaneMergeImages.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            P3GridPaneMergeImages.toFront();
        } else if(event.getSource() == MainButtonReadText) {
            P4GridPaneReadText.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            P4GridPaneReadText.toFront();
        } else if(event.getSource() == MainButtonExit) {
            Platform.exit();
        }
    }
    /*** P1 -> PDF to images -> Button event handler ***/
    @FXML
    public void handleFirstPaneClicks(ActionEvent event) {
        if(event.getSource() == P1ButtonChoosePDF) {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                P1LabelSourcePDFPath.setText(file.getAbsolutePath());
                P1ButtonChooseDestinationFolder.setDisable(false);
            }
        } else if(event.getSource() == P1ButtonChooseDestinationFolder) {
            directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(null);
            if (directory != null) {
                P1LabelDestinationFolderPath.setText(directory.getAbsolutePath());
                P1ChoiceBoxDestinationDPI.setDisable(false);
                P1ChoiceBoxDestinationFormat.setDisable(false);
                P1ButtonConvert.setDisable(false);
            }
       } else if(event.getSource() == P1ButtonConvert) {
            P1ButtonConvert.setDisable(false);
            String pdfPath = P1LabelSourcePDFPath.getText();
            String destinationPath = P1LabelDestinationFolderPath.getText() + "\\";
            int destinationDpi = Integer.valueOf(P1ChoiceBoxDestinationDPI.getValue());
            boolean splitWanted = P1CheckBoxSliceImage.isSelected();
            String destinationFormat = P1ChoiceBoxDestinationFormat.getValue();

            if(pdfPath != null && destinationPath != null && destinationFormat != null) {
                DataForImageGeneration data = new DataForImageGeneration(pdfPath, destinationPath, splitWanted, destinationDpi, destinationFormat);
                PdfToImage.convert(data);
            }
        }
    }
    /*** P2 -> Image splitter -> Button event handler ***/
    @FXML
    public void handleSecondPaneClicks(ActionEvent event) {
        if(event.getSource() == P2ButtonChooseImagesFolder) {
            directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(null);
            String pathToDirectory = null;
            if (directory != null) {
                pathToDirectory = directory.getAbsolutePath() + "\\";
                P2LabelImagesFolderPath.setText(pathToDirectory);
            }
        } else if(event.getSource() == P2ButtonChooseDestinationFolder) {
            directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(null);
            String pathToDirectory = null;
            if (directory != null) {
                pathToDirectory = directory.getAbsolutePath() + "\\";
                P2LabelDestinationFolderPath.setText(pathToDirectory);
            }
        } else if (event.getSource() == P2ButtonSliceImages) {
            String pathToFile = P2LabelImagesFolderPath.getText();
            String conversionDestinationPath = P2LabelDestinationFolderPath.getText();
            if (pathToFile != null) {
                ImageSlicer.splitImage(pathToFile,conversionDestinationPath);
            }
        }
    }
    /*** P3 -> Merge images into a pdf -> Button event handler ***/
    @FXML
    public void handleThirdPaneClicks(ActionEvent event) {
        if(event.getSource() == P3ButtonChooseImagesFolder) {
            directoryChooser = new DirectoryChooser();
            File direcotry = directoryChooser.showDialog(null);
            String pathToDirectory;

            if (direcotry != null) {
                pathToDirectory = direcotry.getAbsolutePath() + "\\";
                P3LabelSourceImagesPath.setText(pathToDirectory);
                sourceFilesForTable.addAll(ReadSourceImages.readSourceFiles(pathToDirectory));
            }
        }else if(event.getSource() == P3ButtonChooseDestinationFolder) {
            directoryChooser = new DirectoryChooser();
            File direcotry = directoryChooser.showDialog(null);
            String pathToDirectory;

            if (direcotry != null) {
                pathToDirectory = direcotry.getAbsolutePath() + "\\";
                P3LabelChooseConversionDestination.setText(pathToDirectory);

            }
        }else if(event.getSource() == P3ButtonConvert) {
            String imagesPath = P3LabelChooseConversionDestination.getText();
            String destinationFileName = P3TextFieldDestinationName.getText();
            String destinationPath = P3LabelChooseConversionDestination.getText();
            boolean splitImages = P3CheckBoxSplitImage.isSelected();

            WriteImagesIntoFile.uniteFilesIntoPdf(imagesPath,destinationFileName,destinationPath, splitImages);
        }
    }
    /*** P4 -> Read text from images -> Button event handler ***/
    @FXML
    public void handleFourthPaneClicks(ActionEvent event) {

    }
}
