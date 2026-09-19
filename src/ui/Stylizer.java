package ui;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public final class Stylizer {

    private static final String PATH_DARK = resolvePath("/styles/dark.css");
    private static final String PATH_LIGHT = resolvePath("/styles/light.css");

    private Stylizer() {
    }

    private static String resolvePath(String path) {
        URL resource = Stylizer.class.getResource(path);
        Objects.requireNonNull(resource, "No se encontró la hoja de estilos: " + path);
        return resource.toExternalForm();
    }

    public static void changeTheme(Scene scene, boolean isDarker) {
        String selectedTheme = isDarker ? PATH_DARK : PATH_LIGHT;
        List<String> stylesheets = scene.getStylesheets();

        if (stylesheets.isEmpty()) {
            stylesheets.add(selectedTheme);
        } else {
            stylesheets.set(0, selectedTheme);
        }
    }

    public static void changeFont(TextArea area) {
        List<String> fontList = Font.getFamilies();
        String currentFont = area.getFont().getName();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(currentFont, fontList);
        dialog.setTitle("Choose font");
        dialog.setHeaderText(null);
        dialog.initOwner(area.getScene().getWindow());

        dialog.showAndWait().ifPresent(font -> {
            double size = area.getFont().getSize();
            area.setFont(Font.font(font, size));
        });
    }

    public static void changeFontSize(TextArea area) {
        List<Integer> sizeList = List.of(12, 14, 16, 18, 20, 24, 28, 36, 48);
        int currentSize = (int) area.getFont().getSize();

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(currentSize, sizeList);
        dialog.setTitle("Choose font size");
        dialog.setHeaderText(null);
        dialog.initOwner(area.getScene().getWindow());

        dialog.showAndWait().ifPresent(size -> {
            String currentFont = area.getFont().getFamily();
            area.setFont(Font.font(currentFont, size));
        });
    }
}