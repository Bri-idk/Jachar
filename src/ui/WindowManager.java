package ui;

import javafx.scene.Scene;

import java.io.File;
import java.nio.file.Path;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import io.FileManager;

public class WindowManager {
    //new instances
    public Path currentPath = null;
    private final Stage window;
    private final BorderPane root;
    private final MenuBar menuBar;
    private final Menu file, appearance;
    private final TextArea area;
    private final MenuItem btnOpenFile, btnNewFile, btnSave, btnTheme;

    public WindowManager(Stage stage){
        this.window = stage;
        root = new BorderPane();
        area = new TextArea();
        menuBar = new MenuBar();
        file = new Menu("File");
        appearance = new Menu("Appearance");
        btnOpenFile = new MenuItem("Open File");
        btnNewFile = new MenuItem("New File");
        btnSave = new MenuItem("Save");
        btnTheme = new MenuItem("Change theme");

        initUI();
        show();
    }

    private void initUI(){
        window.setTitle("JaChar");

        root.setTop(menuBar);
        root.setCenter(area);

        menuBar.getMenus().addAll(file, appearance);
        file.getItems().addAll(btnNewFile, btnOpenFile, btnSave);
        appearance.getItems().addAll(btnTheme);

        Scene sceneMain = new Scene(root, 1080, 720);
        window.setScene(sceneMain);

        //listeners
        btnNewFile.setOnAction(e -> newFile());
        btnOpenFile.setOnAction(e -> openFile());
        btnSave.setOnAction(e -> save());
        btnTheme.setOnAction(e -> changeTheme());
    }

    public void show(){
        window.show();
    }

    public void newFile(){
        currentPath = null;
        area.setText("");
    }

    public void openFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file  = fileChooser.showOpenDialog(window);
        if(file != null){
            currentPath = Path.of(file.getPath());
            String content = FileManager.reader(currentPath);
            area.setText(content);

        }
    }

    public void save(){
        if(currentPath != null){
            String text = area.getText();
            boolean saved = FileManager.writer(currentPath, text);
            notifyUser(saved);
        }else{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            File file = fileChooser.showSaveDialog(window);
            if(file != null){
                String text = area.getText();
                currentPath = file.toPath();

                boolean saved = FileManager.writer(currentPath, text);

                notifyUser(saved);
            }

        }
    }

    public void changeTheme(){

    }

    public void notifyUser(boolean saved){
        Alert alert;
        if(saved){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Saved correctly");
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error at saving");
        }
        alert.showAndWait();
    }
}
