package ui;
import java.nio.file.Path;
import javax.swing.*;
import java.awt.*;

import ui.*;

import io.FileManager;

public class WindowManager {
    //* Elementos globales
    public static Path currentPath = null;
    private static JFrame window;
    private static JTextArea area;
    private static boolean isDark = false;
    private static JButton btnOpenFile, btnNewFile, btnSave, btnTheme;
    private static JPanel panel;

    //* Func de crear ventana
    public static void createWindow(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
        }
        window = new JFrame("JaChar");
        window.setSize(1080, 980);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void confScrollArea(){
        area = new JTextArea();
        Stylizer.stylizerArea(area);
        var scroll = new JScrollPane(area);
        window.add(scroll, BorderLayout.CENTER);
    }

    public static void confBtnBar(){
         panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,5));
        window.add(panel, BorderLayout.NORTH);
         btnOpenFile = new JButton("Abrir");
         btnNewFile = new JButton("Nuevo archivo");
         btnSave = new JButton("Guardar");
         btnTheme = new JButton("Cambiar tema");

        Stylizer.stylizerBtn(btnNewFile);

        Stylizer.stylizerBtn(btnOpenFile);

        Stylizer.stylizerBtn(btnSave);

        Stylizer.stylizerBtn(btnTheme);

        Stylizer.stylizePanel(panel);


        panel.add(btnOpenFile);
        panel.add(btnNewFile);
        panel.add(btnSave);
        panel.add(btnTheme);

        btnOpenFile.addActionListener(e -> {
            openFile();
        });

        btnSave.addActionListener(e -> {
            save();
        });

        btnNewFile.addActionListener(e -> {
            newFile();
        });

        btnTheme.addActionListener(e -> {
            if(!isDark){
                isDark = true;
                changeTheme(isDark);
            }else{
                isDark =false;
                changeTheme(isDark);
            }
        });
    }

    public static void window(){

        createWindow();
        confScrollArea();
        confBtnBar();
        changeTheme(isDark);


        //? Hacemos visible la ventana
        window.setVisible(true);
    }

    public static void save(){
        if(currentPath != null){
            String text = area.getText();
            boolean saved = FileManager.writer(currentPath, text);
            if (saved){
                JOptionPane.showMessageDialog(
                        window,
                        "Salvado correctamente",
                        "Confirm",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                        window,
                        "Error al salvar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }else{
            var dialog = new FileDialog(window, "Guardar", FileDialog.SAVE);
            dialog.setVisible(true);
            if(dialog.getFile() != null){
                String text = area.getText();
                currentPath = Path.of(dialog.getDirectory(), dialog.getFile());
                boolean saved = FileManager.writer(currentPath, text);
                if (saved){
                    JOptionPane.showMessageDialog(
                            window,
                            "Salvado correctamente",
                            "Confirm",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            window,
                            "Error al salvar",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }

    }
    public static void openFile(){
        var dialog = new FileDialog(window, "Abrir", FileDialog.LOAD);
        dialog.setVisible(true);
        if (dialog.getFile() != null){
            currentPath = Path.of(dialog.getDirectory(), dialog.getFile());
            String contenido = FileManager.reader(currentPath);
            area.setText(contenido);
        }
    }
    public static void newFile(){
        currentPath = null;
        area.setText("");
    }
    public static void changeTheme(boolean isDark){
        if(isDark){
            Stylizer.putDarkMode(area, panel,btnNewFile, btnSave, btnOpenFile, btnTheme);
        }else{
            Stylizer.putWhiteMode(area, panel, btnNewFile, btnSave, btnOpenFile, btnTheme);
        }
    }
}
