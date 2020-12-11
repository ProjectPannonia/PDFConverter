package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import service.panes.pane1.DataForImageGeneration;
import service.panes.pane1.PdfToImage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatedController implements Initializable {

    private ObservableList<String> fileFormats, targetDpi;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    @FXML
    private AnchorPane pnMain;

    @FXML
    private VBox vbMenuBackground;

    @FXML
    private Pane pnLogoBackground;

    /*** Main-menu buttons ***/
    @FXML
    private Button btPdfToImage, btImageSplitter, btMergeIntoPDF, btReadTextFromImages, btExit;

    /*** Content panes ***/
    @FXML
    private GridPane gridpanePdfToImage, gridpaneImageSplitter, gridpaneMergeImagesIntoPDF, gridpaneReadTextFromImages;



    /*** P1 -> PDF to images ***/
    /** buttons **/
    @FXML
    private Button btP1ChoosePDFPath, btP1ChooseDestinationFolder, btP1Convert;
    /** Labels **/
    @FXML
    private Label lbP1PDFPath, lbP1DestinationPath;
    /** choice boxes **/
    @FXML
    private ChoiceBox<String> cbP1DestinationFormat, cbP1DestinationDPI;
    /** checkbox **/
    @FXML
    private CheckBox cbP1SplitImage;

    /*** P2 -> Image splitter ***/

    /*** P3 -> Merge images into pdf ***/

    /*** P4 -> Read text from images ***/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileFormats = FXCollections.observableArrayList("JPG","JPEG","PNG","TIF","TIFF","GIF","BMP");
        cbP1DestinationFormat.setItems(fileFormats);
        cbP1DestinationFormat.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        targetDpi = FXCollections.observableArrayList("100", "200", "300", "400", "500", "600");
        cbP1DestinationDPI.setItems(targetDpi);
        gridpanePdfToImage.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
        gridpanePdfToImage.toFront();
        btP1ChooseDestinationFolder.setDisable(true);
        btP1Convert.setDisable(true);
        cbP1DestinationFormat.setDisable(true);
        cbP1DestinationDPI.setDisable(true);
    }
    @FXML
    public void handleMainMenuClicks(ActionEvent event) {
        if(event.getSource() == btPdfToImage) {
            gridpanePdfToImage.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            gridpanePdfToImage.toFront();
        } else if(event.getSource() == btImageSplitter) {
            gridpaneImageSplitter.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            gridpaneImageSplitter.toFront();
        } else if(event.getSource() == btMergeIntoPDF) {
            gridpaneMergeImagesIntoPDF.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            gridpaneMergeImagesIntoPDF.toFront();
        } else if(event.getSource() == btReadTextFromImages) {
            gridpaneReadTextFromImages.setBackground(new Background(new BackgroundFill(Color.rgb(99, 69, 153), CornerRadii.EMPTY, Insets.EMPTY)));
            gridpaneReadTextFromImages.toFront();
        } else if(event.getSource() == btExit) {
            Platform.exit();
        }
    }
    /*** P1 -> PDF to images -> Button event handler ***/
    @FXML
    public void handleFirstPaneClicks(ActionEvent event) {
        boolean sourceReady = false;
        boolean destinationReady = false;
        boolean destinationFormatReady = false;
        boolean destinationDpiReady = false;
        if(event.getSource() == btP1ChoosePDFPath) {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
            File file = fileChooser.showOpenDialog(null);
            System.out.println(file.getAbsolutePath());
            if (file != null) {
                lbP1PDFPath.setText(file.getAbsolutePath());
                sourceReady = true;
                file = null;
                btP1ChooseDestinationFolder.setDisable(false);
            }
        } else if(event.getSource() == btP1ChooseDestinationFolder) {
            directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(null);
            if (directory != null) {
                lbP1DestinationPath.setText(directory.getAbsolutePath());
                destinationReady = true;
                destinationFormatReady = true;
                destinationDpiReady = true;
                cbP1DestinationDPI.setDisable(false);
                cbP1DestinationFormat.setDisable(false);
            }
        } else if(event.getSource() == btP1Convert && sourceReady && destinationReady && destinationDpiReady && destinationFormatReady) {
            btP1Convert.setDisable(false);
            String pdfPath = lbP1PDFPath.getText();
            String destinationPath = lbP1DestinationPath.getText();
            DataForImageGeneration data = new DataForImageGeneration(pdfPath, destinationPath, cbP1SplitImage, destinationDpi, destinationFormat);
            PdfToImage.convert(data);
        }
    }
}
