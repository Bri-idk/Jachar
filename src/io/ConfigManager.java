package io;

import ui.Theme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE_NAME = "config.properties";

    public static final String DEFAULT_FONT_FAMILY = "Consolas";
    public static final int DEFAULT_FONT_SIZE = 14;
    public static final Theme DEFAULT_THEME = Theme.DARK;

    private static final String KEY_FONT_FAMILY = "font.family";
    private static final String KEY_FONT_SIZE = "font.size";
    private static final String KEY_THEME = "theme";

    private String fontFamily;
    private int fontSize;
    private Theme theme;
    private final Path configPath;

    public ConfigManager() {
        this(Paths.get(CONFIG_FILE_NAME));
    }

    public ConfigManager(Path configPath) {
        this.configPath = configPath;
        this.fontFamily = DEFAULT_FONT_FAMILY;
        this.fontSize = DEFAULT_FONT_SIZE;
        this.theme = DEFAULT_THEME;
    }

    public void load() {
        if (!Files.exists(configPath)) {
            save();
            return;
        }

        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(configPath)) {
            properties.load(in);

            this.fontFamily = properties.getProperty(KEY_FONT_FAMILY, DEFAULT_FONT_FAMILY);

            try {
                this.fontSize = Integer.parseInt(properties.getProperty(KEY_FONT_SIZE, String.valueOf(DEFAULT_FONT_SIZE)));
            } catch (NumberFormatException e) {
                this.fontSize = DEFAULT_FONT_SIZE;
            }

            String themeStr = properties.getProperty(KEY_THEME, DEFAULT_THEME.name());
            try {
                this.theme = Theme.valueOf(themeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                this.theme = DEFAULT_THEME;
            }
        } catch (IOException e) {
            System.err.println("Could not load configuration file: " + e.getMessage());
        }
    }

    public void save() {
        Properties properties = new Properties();
        properties.setProperty(KEY_FONT_FAMILY, fontFamily);
        properties.setProperty(KEY_FONT_SIZE, String.valueOf(fontSize));
        properties.setProperty(KEY_THEME, theme.name());

        try {
            Path parent = configPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (OutputStream out = Files.newOutputStream(configPath)) {
                properties.store(out, "JaChar User Configuration");
            }
        } catch (IOException e) {
            System.err.println("Could not save configuration file: " + e.getMessage());
        }
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Path getConfigPath() {
        return configPath;
    }
}
