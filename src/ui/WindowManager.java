package ui;

import io.ConfigManager;
import io.FileManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.concurrent.Task;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Optional;

public class WindowManager {

    private static final String APP_NAME = "JaChar";
    private static final String ICON_RESOURCE = "/assets/jachar_logo.png";

    private final ConfigManager configManager;
    private Path currentPath = null;
    private Theme currentTheme;
    private boolean isDirty = false;
    private boolean isBusy = false;

    private final Stage window;
    private final BorderPane root;
    private final Label statusLabel;
    private final Label titleLabel;
    private final MenuBar menuBar;
    private final Menu fileMenu, appearanceMenu;
    private final TextArea area;
    private final MenuItem btnOpenFile, btnNewFile, btnSave, btnSaveAs, btnTheme, btnFont, btnChangeFontSize;
    private final ExecutorService ioExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r, "jachar-file-io");
        thread.setDaemon(true);
        return thread;
    });

    public WindowManager(Stage stage) {
        this.configManager = new ConfigManager();
        this.configManager.load();
        this.currentTheme = configManager.getTheme();

        this.window = stage;
        this.root = new BorderPane();
        this.statusLabel = new Label();
        this.titleLabel = new Label(APP_NAME);
        this.area = new TextArea();
        this.menuBar = new MenuBar();
        this.fileMenu = new Menu("File");
        this.appearanceMenu = new Menu("Appearance");

        this.btnOpenFile = new MenuItem("Open File");
        this.btnNewFile = new MenuItem("New File");
        this.btnSave = new MenuItem("Save");
        this.btnSaveAs = new MenuItem("Save As");
        this.btnTheme = new MenuItem("Change Theme");
        this.btnFont = new MenuItem("Change Font");
        this.btnChangeFontSize = new MenuItem("Change Font Size");

        initUI();
    }

    private void initUI() {
        window.initStyle(StageStyle.UNDECORATED);
        updateTitle();
        URL iconUrl = WindowManager.class.getResource(ICON_RESOURCE);
        if (iconUrl != null) {
            window.getIcons().add(new Image(iconUrl.toExternalForm()));
        }

        // Apply configured font and font size
        area.setFont(Font.font(configManager.getFontFamily(), configManager.getFontSize()));
        root.setStyle("-fx-font-family: \"Consolas\";");

        HBox titleBar = createTitleBar();
        root.setTop(titleBar);
        root.setCenter(area);
        root.setBottom(statusLabel);

        menuBar.getMenus().addAll(fileMenu, appearanceMenu);
        fileMenu.getItems().addAll(btnNewFile, btnOpenFile, btnSave, btnSaveAs);
        appearanceMenu.getItems().addAll(btnTheme, btnFont, btnChangeFontSize);
        configureShortcuts();

        Scene sceneMain = new Scene(root, 1080, 720);
        Stylizer.changeTheme(sceneMain, currentTheme);
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
        btnSaveAs.setOnAction(e -> saveAs());
        btnTheme.setOnAction(e -> {
            Theme selected = Stylizer.chooseTheme(sceneMain, currentTheme);
            if (selected != currentTheme) {
                currentTheme = selected;
                configManager.setTheme(currentTheme);
                configManager.save();
            }
        });
        btnFont.setOnAction(e -> {
            Stylizer.changeFont(area).ifPresent(font -> {
                configManager.setFontFamily(font);
                configManager.save();
            });
        });
        btnChangeFontSize.setOnAction(e -> {
            Stylizer.changeFontSize(area).ifPresent(size -> {
                configManager.setFontSize(size);
                configManager.save();
            });
        });

        window.setOnCloseRequest(e -> {
            if (isBusy) {
                e.consume();
                return;
            }
            if (isDirty && !confirmDiscardChanges()) {
                e.consume();
                return;
            }
            ioExecutor.shutdown();
        });
    }

    private void configureShortcuts() {
        btnNewFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        btnOpenFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        btnSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        btnSaveAs.setAccelerator(new KeyCodeCombination(
            KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
    }

    public void show() {
        window.show();
    }

    private HBox createTitleBar() {
        menuBar.setUseSystemMenuBar(false);

        Button minimizeButton = createWindowButton("—", "Minimize window");
        Button maximizeButton = createWindowButton("□", "Maximize or restore window");
        Button closeButton = createWindowButton("×", "Close window");
        closeButton.getStyleClass().add("window-close-button");

        minimizeButton.setOnAction(e -> window.setIconified(true));
        maximizeButton.setOnAction(e -> window.setMaximized(!window.isMaximized()));
        closeButton.setOnAction(e -> window.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleLabel.setStyle("-fx-padding: 0 10 0 10; -fx-font-weight: bold;");

        HBox titleBar = new HBox(menuBar, spacer, titleLabel,
                minimizeButton, maximizeButton, closeButton);
        titleBar.getStyleClass().add("window-title-bar");
        titleBar.setMinHeight(32);
        titleBar.setAlignment(Pos.CENTER_LEFT);

        final double[] dragOffset = new double[2];
        titleBar.setOnMousePressed(e -> {
            dragOffset[0] = e.getScreenX() - window.getX();
            dragOffset[1] = e.getScreenY() - window.getY();
        });
        titleBar.setOnMouseDragged(e -> {
            if (!window.isMaximized()) {
                window.setX(e.getScreenX() - dragOffset[0]);
                window.setY(e.getScreenY() - dragOffset[1]);
            }
        });
        titleBar.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && e.getTarget() == titleBar) {
                window.setMaximized(!window.isMaximized());
            }
        });

        return titleBar;
    }

    private Button createWindowButton(String text, String accessibleText) {
        Button button = new Button(text);
        button.setAccessibleText(accessibleText);
        button.setFocusTraversable(false);
        button.getStyleClass().add("window-control-button");
        button.setMinSize(42, 32);
        return button;
    }

    private void updateTitle() {
        String fileName = (currentPath != null) ? currentPath.getFileName().toString() : "Untitled";
        String dirtyIndicator = isDirty ? "*" : "";
        String title = dirtyIndicator + fileName + " - " + APP_NAME;
        window.setTitle(title);
        titleLabel.setText(title);
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
        if (isDirty && !confirmDiscardChanges())
            return;

        currentPath = null;
        area.clear();
        isDirty = false;
        updateTitle();
    }

    private void openFile() {
        if (isDirty && !confirmDiscardChanges())
            return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            Path selectedPath = file.toPath();
            setBusy(true, "Opening file...");

            Task<Optional<String>> task = new Task<>() {
                @Override
                protected Optional<String> call() {
                    return FileManager.reader(selectedPath);
                }
            };
            task.setOnSucceeded(e -> {
                setBusy(false, "");
                Optional<String> content = task.getValue();
                if (content.isPresent()) {
                    currentPath = selectedPath;
                    area.setText(content.get());
                    isDirty = false;
                    updateTitle();
                } else {
                    notifyUser(false, "Failed to read file.");
                }
            });
            task.setOnFailed(e -> {
                setBusy(false, "");
                notifyUser(false, "Failed to read file.");
            });
            ioExecutor.submit(task);
        }
    }

    private void save() {
        if (currentPath == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            File file = fileChooser.showSaveDialog(window);
            if (file == null)
                return;
            currentPath = file.toPath();
        }
        saveTo(currentPath);
    }

    private void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        if (currentPath != null && currentPath.getParent() != null) {
            File parentDir = currentPath.getParent().toFile();
            if (parentDir.exists() && parentDir.isDirectory()) {
                fileChooser.setInitialDirectory(parentDir);
            }
            fileChooser.setInitialFileName(currentPath.getFileName().toString());
        }

        File file = fileChooser.showSaveDialog(window);
        if (file == null)
            return;

        saveTo(file.toPath());
    }

    private void saveTo(Path target) {
        String content = area.getText();
        setBusy(true, "Saving file...");

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return FileManager.writer(target, content);
            }
        };
        task.setOnSucceeded(e -> {
            setBusy(false, "");
            if (task.getValue()) {
                currentPath = target;
                isDirty = false;
                updateTitle();
            } else {
                notifyUser(false, "Error saving file.");
            }
        });
        task.setOnFailed(e -> {
            setBusy(false, "");
            notifyUser(false, "Error saving file.");
        });
        ioExecutor.submit(task);
    }

    private void setBusy(boolean busy, String message) {
        isBusy = busy;
        statusLabel.setText(message);
        btnNewFile.setDisable(busy);
        btnOpenFile.setDisable(busy);
        btnSave.setDisable(busy);
        btnSaveAs.setDisable(busy);
        btnTheme.setDisable(busy);
        btnFont.setDisable(busy);
        btnChangeFontSize.setDisable(busy);
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