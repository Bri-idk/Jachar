package ui;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import java.util.List;

public final class Stylizer {

    private Stylizer() {
    }

    public static void changeTheme(Scene scene, Theme theme) {
        List<String> stylesheets = scene.getStylesheets();
        String selectedTheme = theme.getStylesheet();

        if (stylesheets.isEmpty()) {
            stylesheets.add(selectedTheme);
        } else {
            stylesheets.set(0, selectedTheme);
        }
    }

    public static Theme chooseTheme(Scene scene, Theme currentTheme) {
        ChoiceDialog<Theme> dialog = new ChoiceDialog<>(currentTheme, Theme.values());
        dialog.setTitle("Choose theme");
        dialog.setHeaderText(null);
        dialog.initOwner(scene.getWindow());

        return dialog.showAndWait().map(theme -> {
            changeTheme(scene, theme);
            return theme;
        }).orElse(currentTheme);
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