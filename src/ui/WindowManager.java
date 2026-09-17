package ui;

import io.FileManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class WindowManager {

    private static final String APP_NAME = "JaChar";

    private Path currentPath = null;
    private boolean isDarker = false;
    private boolean isDirty = false;

    private final Stage window;
    private final BorderPane root;
    private final MenuBar menuBar;
    private final Menu fileMenu, appearanceMenu;
    private final TextArea area;
    private final MenuItem btnOpenFile, btnNewFile, btnSave, btnTheme, btnFont, btnChangeFontSize;

    public WindowManager(Stage stage) {
        this.window = stage;
        this.root = new BorderPane();
        this.area = new TextArea();
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu("File");
        this.appearanceMenu = new Menu("Appearance");

        this.btnOpenFile = new MenuItem("Open File");
        this.btnNewFile = new MenuItem("New File");
        this.btnSave = new MenuItem("Save");
        this.btnTheme = new MenuItem("Change Theme");
        this.btnFont = new MenuItem("Change Font");
        this.btnChangeFontSize = new MenuItem("Change Font Size");

        initUI();
    }

    private void initUI() {
        updateTitle();

        root.setTop(menuBar);
        root.setCenter(area);

        menuBar.getMenus().addAll(fileMenu, appearanceMenu);
        fileMenu.getItems().addAll(btnNewFile, btnOpenFile, btnSave);
        appearanceMenu.getItems().addAll(btnTheme, btnFont, btnChangeFontSize);

        Scene sceneMain = new Scene(root, 1080, 720);
        window.setScene(sceneMain);

        area.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isDirty) {
                isDirty = true;
                updateTitle();
            }
        });

        btnNewFile.setOnAction(e -> newFile());
        btnOpenFile.setOnAction(e -> openFile());
        btnSave.setOnAction(e -> save());
        btnTheme.setOnAction(e -> {
            isDarker = !isDarker;
            Stylizer.changeTheme(sceneMain, isDarker);
        });
        btnFont.setOnAction(e -> Stylizer.changeFont(area));
        btnChangeFontSize.setOnAction(e -> Stylizer.changeFontSize(area));

        window.setOnCloseRequest(e -> {
            if (isDirty && !confirmDiscardChanges()) {
                e.consume();
            }
        });
    }

    public void show() {
        window.show();
    }

    private void updateTitle() {
        String fileName = (currentPath != null) ? currentPath.getFileName().toString() : "Untitled";
        String dirtyIndicator = isDirty ? "*" : "";
        window.setTitle(dirtyIndicator + fileName + " - " + APP_NAME);
    }

    private boolean confirmDiscardChanges() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("You have unsaved changes.");
        alert.setContentText("Do you want to discard them and continue?");
        alert.initOwner(window);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void newFile() {
        if (isDirty && !confirmDiscardChanges()) return;

        currentPath = null;
        area.clear();
        isDirty = false;
        updateTitle();
    }

    private void openFile() {
        if (isDirty && !confirmDiscardChanges()) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            Path selectedPath = file.toPath();
            Optional<String> content = FileManager.reader(selectedPath);

            if (content.isPresent()) {
                currentPath = selectedPath;
                area.setText(content.get());
                isDirty = false;
                updateTitle();
            } else {
                notifyUser(false, "Failed to read file.");
            }
        }
    }

    private void save() {
        if (currentPath == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            File file = fileChooser.showSaveDialog(window);
            if (file == null) return;
            currentPath = file.toPath();
        }

        boolean saved = FileManager.writer(currentPath, area.getText());
        if (saved) {
            isDirty = false;
            updateTitle();
        }
        notifyUser(saved, saved ? "File saved successfully." : "Error saving file.");
    }

    private void notifyUser(boolean success, String message) {
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Success" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.showAndWait();
    }
}