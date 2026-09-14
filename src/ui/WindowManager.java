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
    private final JMenuBar menuBar;
    private final JMenu menu, menuAppearance;
    private boolean isDark;
    private JMenuItem btnOpenFile, btnNewFile, btnSave, btnTheme;

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
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuAppearance = new JMenu("Appearance");
        scroll = new JScrollPane(area);

        initUI();

    }

    private void initUI(){

        Stylizer.stylizerArea(area);

        window.add(scroll, BorderLayout.CENTER);
        window.add(menuBar, BorderLayout.NORTH);
        btnOpenFile = new JMenuItem("Open File");
        btnNewFile = new JMenuItem("New File");
        btnSave = new JMenuItem("Save");
        btnTheme = new JMenuItem("Change Theme");

        Stylizer.stylizerBtn(btnNewFile);
        Stylizer.stylizerBtn(btnOpenFile);
        Stylizer.stylizerBtn(btnSave);
        Stylizer.stylizerBtn(btnTheme);
        Stylizer.stylizePanel(menuBar);
        Stylizer.stylizerMenu(menu);
        Stylizer.stylizerMenu(menuAppearance);
        Stylizer.stylizerMenuBar(menuBar);

        menuBar.add(menu);
        menuBar.add(menuAppearance);
        menu.add(btnOpenFile);
        menu.add(btnNewFile);
        menu.add(btnSave);
        menuAppearance.add(btnTheme);

        // Listeners
        btnOpenFile.addActionListener(e -> openFile());
        btnSave.addActionListener(e -> save());
        btnNewFile.addActionListener(e -> newFile());
        btnTheme.addActionListener(e -> {
            isDark = !isDark;
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
            Stylizer.putDarkMode(area, menuBar, menu, menuAppearance ,btnNewFile,btnSave, btnOpenFile, btnTheme);
        } else {
            Stylizer.putWhiteMode(area, menuBar, menu, menuAppearance , btnNewFile, btnSave, btnOpenFile, btnTheme);
        }
    }

}
