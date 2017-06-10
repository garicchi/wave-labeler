package gui;

import lib.CsvManager;
import lib.DirManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import speechrecorder.WaveLabeler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    @FXML
    TableView<SourceFile> table_files;

    @FXML
    TableColumn pathColmun;

    @FXML
    TableColumn nameColmun;

    @FXML
    TableColumn isLabledColmun;

    @FXML
    ComboBox<String> combo_labels;


    @FXML
    Label label_fileName;

    MediaPlayer mediaPlayer;


    @FXML
    public void onOpenDir(){
        String filePath = showFolderChooser().getPath();
        if(filePath!=null) {
            List<String> files = DirManager.list_files(filePath,".wav");
            table_files.getItems().clear();
            for(String s:files){
                String ext = DirManager.getExt(s);
                if (ext.equals(".wav")) {
                    String labelName = DirManager.getNoExt(s) + ".label";
                    String labelValue = "";
                    if ((new File(labelName)).exists()) {
                        try {
                            List<String[]> lines = CsvManager.Read(labelName, ",", "UTF-8", false, (line, index) -> {

                            });
                            labelValue = lines.get(1)[0];
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    table_files.getItems().add(new SourceFile(s, (new File(s)).getName(), labelValue));
                }
            }
            table_files.getSelectionModel().select(0);


        }
    }

    private void playSelctedFile(){
        SourceFile source = table_files.getSelectionModel().getSelectedItem();
        if (source !=null) {
            if(mediaPlayer!=null){
                mediaPlayer.stop();
            }
            File file = new File(source.path.get());
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
        }
    }

    @FXML
    public void onPlay(){
        playSelctedFile();
    }

    @FXML
    public void onPrev(){
        saveSelectedFileLabel();
        int index = table_files.getSelectionModel().getSelectedIndex();
        index--;
        if (index>=0) {
            table_files.getSelectionModel().select(index);
            playSelctedFile();
        }
    }

    @FXML
    public void onNext(){
        saveSelectedFileLabel();
        int index = table_files.getSelectionModel().getSelectedIndex();
        index++;
        if (index<table_files.getItems().size()) {
            table_files.getSelectionModel().select(index);
            playSelctedFile();
        }
    }

    @FXML
    public void onSave(){
        saveSelectedFileLabel();
    }

    private void saveSelectedFileLabel(){
        SourceFile file = table_files.getSelectionModel().getSelectedItem();
        String currentLabel = combo_labels.getSelectionModel().getSelectedItem();
        if(currentLabel!=null) {
            file.label.set(currentLabel);
            String labelPath = DirManager.getNoExt(file.path.get()) + ".label";
            try {
                CsvManager.Write(labelPath, "UTF-8", "category", 1, (index) -> {
                    return file.label.get();
                });
                combo_labels.getSelectionModel().select(-1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pathColmun.setCellValueFactory(new PropertyValueFactory<SourceFile,String>("path"));
        nameColmun.setCellValueFactory(new PropertyValueFactory<SourceFile,String>("name"));
        isLabledColmun.setCellValueFactory(new PropertyValueFactory<SourceFile,String>("label"));

        combo_labels.getItems().add("Declarative");
        combo_labels.getItems().add("Question");
        combo_labels.getItems().add("Positive");
        combo_labels.getItems().add("Negative");
    }

    public MainWindowController(){
    }



    private File showFileChooser(){
        FileChooser chooser = new FileChooser();
        return chooser.showOpenDialog(WaveLabeler.mainStage);
    }

    private File showFolderChooser(){
        DirectoryChooser chooser = new DirectoryChooser();
        return chooser.showDialog(WaveLabeler.mainStage);
    }

    private void showAlert(Alert.AlertType type,String header,String content){
        Alert alert = new Alert(type);
        alert.setTitle("");
        alert.getDialogPane().setHeaderText(header);
        alert.getDialogPane().setContentText(content);
        alert.show();
    }


}
