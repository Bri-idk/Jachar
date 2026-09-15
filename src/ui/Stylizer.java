package ui;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import java.util.Optional;
import java.net.URL;
import java.util.List;

public class Stylizer {
    private static final String  pathDark = resolvePath("/styles/dark.css");
    private static final String pathLight = resolvePath("/styles/light.css");
    private int sizeFont;
    private static String resolvePath(String path){
        URL resource = Stylizer.class.getResource(path);
        assert resource != null;
        return resource.toExternalForm();
    }

    public static void changeTheme(Scene scene, boolean isDarker){
        String selectedTheme = isDarker ? pathDark : pathLight;
        List<String> styleList = scene.getStylesheets();

        if(styleList.isEmpty()){
            styleList.add(selectedTheme);
        }else{
            styleList.set(0, selectedTheme);
        }
    }

    public static void changeFont(TextArea area){
        List<String> fontList = Font.getFamilies();
        String currentFont = area.getFont().getName();

        ChoiceDialog<String> dialog = new ChoiceDialog<>( currentFont, fontList);
        dialog.setTitle("Chose font");
        dialog.initOwner(area.getScene().getWindow());
        Optional<String> result = dialog.showAndWait();
        result.ifPresent( font -> {
            double size = area.getFont().getSize();
            area.setFont(Font.font(font, size));
        });
    }

    public static void changeFontSize(TextArea area){
        List<Integer> sizeList = List.of(12,14,16,18,20,24,28,36,48);
        int currentSize = (int) area.getFont().getSize();
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>( currentSize, sizeList);
        dialog.setTitle("Chose font size");
        dialog.initOwner(area.getScene().getWindow());
        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent( font -> {
            String currentFont= area.getFont().getFamily();
            area.setFont(Font.font(currentFont, font));
        });
    }

}
