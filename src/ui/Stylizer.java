package ui;

import javax.swing.*;
import java.awt.*;

public class Stylizer {
    public static void stylizerBtn(JButton btn){
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setContentAreaFilled(false); //*bug de windows
        btn.setOpaque(true);//*respeta diseño
    }

    public static void stylizerArea(JTextArea area){
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        area.setMargin(new Insets(10, 10, 10 , 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    public static void stylizePanel(JPanel panel) {
        panel.setBackground(Color.GRAY);
    }

    public static void putWhiteMode(JTextArea area,  JPanel panel, JButton... btns){
        Color negro = new Color(45,45,48);
        for(JButton btn : btns){
            btn.setBackground(Color.WHITE);
            btn.setForeground(negro);
        }
        area.setBackground(Color.WHITE);
        area.setForeground(negro);
        area.setCaretColor(negro);
        panel.setBackground(Color.WHITE);
        panel.setForeground(negro);

    }
    public static void putDarkMode(JTextArea area, JPanel panel, JButton... btns){
        Color negro = new Color(45,45,48);
        for(JButton btn : btns) {
            btn.setBackground(negro);
            btn.setForeground(Color.WHITE);
        }
        area.setBackground(negro);
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        panel.setBackground(negro);
        panel.setForeground(Color.WHITE);
    }


}
