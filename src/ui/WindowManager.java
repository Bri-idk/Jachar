package ui;
import java.nio.file.Path;
import javax.swing.*;
import java.awt.*;


import io.FileManager;

public class WindowManager {
    //global instances
    public Path currentPath = null;
    private final JFrame window;
    private final JScrollPane scroll;
    private final JTextArea area;
    private final JPanel panel;
    private boolean isDark;
    private JButton btnOpenFile, btnNewFile, btnSave, btnTheme;

    public WindowManager(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.err.print("No se pudo cargar el Look & Feel del sistema: " + e.getMessage());
        }
        //initializer
        window = new JFrame("JaChar");
        window.setSize(1080, 980);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        area = new JTextArea();
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        scroll = new JScrollPane(area);

        initUI();

    }

    private void initUI(){
        // Área central
        Stylizer.stylizerArea(area);
        window.add(scroll, BorderLayout.CENTER);

        // Barra superior
        window.add(panel, BorderLayout.NORTH);
        btnOpenFile = new JButton("Open File");
        btnNewFile = new JButton("New File");
        btnSave = new JButton("Save");
        btnTheme = new JButton("Change Theme");

        Stylizer.stylizerBtn(btnNewFile);
        Stylizer.stylizerBtn(btnOpenFile);
        Stylizer.stylizerBtn(btnSave);
        Stylizer.stylizerBtn(btnTheme);
        Stylizer.stylizePanel(panel);

        panel.add(btnOpenFile);
        panel.add(btnNewFile);
        panel.add(btnSave);
        panel.add(btnTheme);

        // Listeners
        btnOpenFile.addActionListener(e -> openFile());
        btnSave.addActionListener(e -> save());
        btnNewFile.addActionListener(e -> newFile());
        btnTheme.addActionListener(e -> {
            isDark = !isDark; // Alternancia directa sin if/else redundante
            changeTheme(isDark);
        });

        changeTheme(isDark);
    }

    public void show(){
        window.setVisible(true);
    }

    public void save() {
        if (currentPath != null) {
            String text = area.getText();
            boolean saved = FileManager.writer(currentPath, text);
            notifyUser(saved);
        } else {
            FileDialog dialog = new FileDialog(window, "Save", FileDialog.SAVE);
            dialog.setVisible(true);
            if (dialog.getFile() != null) {
                String text = area.getText();
                currentPath = Path.of(dialog.getDirectory(), dialog.getFile());
                boolean saved = FileManager.writer(currentPath, text);
                notifyUser(saved);
            }
        }
    }

    private void notifyUser(boolean saved) {
        if (saved) {
            JOptionPane.showMessageDialog(window, "Saved correctly", "Confirm", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(window, "Error at saving", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openFile() {
        FileDialog dialog = new FileDialog(window, "Open", FileDialog.LOAD);
        dialog.setVisible(true);
        if (dialog.getFile() != null) {
            currentPath = Path.of(dialog.getDirectory(), dialog.getFile());
            String contenido = FileManager.reader(currentPath);
            area.setText(contenido);
        }
    }

    public void newFile() {
        currentPath = null;
        area.setText("");
    }

    public void changeTheme(boolean isDark) {
        if (isDark) {
            Stylizer.putDarkMode(area, panel, btnNewFile, btnSave, btnOpenFile, btnTheme);
        } else {
            Stylizer.putWhiteMode(area, panel, btnNewFile, btnSave, btnOpenFile, btnTheme);
        }
    }

}
