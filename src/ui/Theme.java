package ui;

import java.net.URL;

public enum Theme {
    LIGHT("Light", "/styles/light.css"),
    DARK("Dark", "/styles/dark.css"),
    DRACULA("Dracula", "/styles/dracula.css"),
    NORD("Nord", "/styles/nord.css"),
    SEPIA("Sepia", "/styles/sepia.css"),
    CYBERPUNK("Cyberpunk", "/styles/cyberpunk.css");

    private final String displayName;
    private final String stylesheet;

    Theme(String displayName, String resourcePath) {
        this.displayName = displayName;
        URL resource = Theme.class.getResource(resourcePath);
        if (resource == null) {
            throw new IllegalStateException("No se encontró el tema CSS: " + resourcePath);
        }
        this.stylesheet = resource.toExternalForm();
    }

    public String getStylesheet() {
        return stylesheet;
    }

    @Override
    public String toString() {
        return displayName;
    }
}