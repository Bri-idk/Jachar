package ui;
import java.nio.file.Path;
import javax.swing.*;
import java.awt.*;

import io.FileManager;

public class WindowManager {
    //* Elementos globales
    public static Path currentPath = null;
    private static JFrame window;
    private static JTextArea area;

    //* Func de crear ventana
    public static void createWindow(){
        window = new JFrame("JaChar");
        window.setSize(1080, 980);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void confScrollArea(){
        area = new JTextArea();
        var scroll = new JScrollPane(area);
        window.add(scroll, BorderLayout.CENTER);
    }

    public static void confBtnBar(){
        var upperBar = new JPanel();
        window.add(upperBar, BorderLayout.NORTH);
        var btnOpenFile = new JButton("Abrir");
        var btnNewFile = new JButton("Nuevo archivo");
        var btnSave = new JButton("Guardar");
        upperBar.add(btnOpenFile);
        upperBar.add(btnNewFile);
        upperBar.add(btnSave);

        btnOpenFile.addActionListener(e -> {
            openFile();
        });

        btnSave.addActionListener(e -> {
            save();
        });

        btnNewFile.addActionListener(e -> {
            newFile();
        });
    }

    public static void window(){
        createWindow();
        confScrollArea();
        confBtnBar();



        //? Hacemos visible la ventana
        window.setVisible(true);
    }

    public static void openFile(){
        var selector = new JFileChooser();
        int resultado = selector.showOpenDialog(window);
        if (resultado == JFileChooser.APPROVE_OPTION){
            currentPath = selector.getSelectedFile().toPath();
            String contenido = FileManager.reader(currentPath);
            area.setText(contenido);
        }
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
            var selector = new JFileChooser();
            int resultado = selector.showSaveDialog(window);
            if(resultado == JFileChooser.APPROVE_OPTION){
                String text = area.getText();
                currentPath = selector.getSelectedFile().toPath();
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

    public static void newFile(){
        currentPath = null;
        area.setText("");
    }
}
