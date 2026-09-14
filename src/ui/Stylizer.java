package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;

public class Stylizer {
    public static void stylizerBtn(JMenuItem btn){
        btn.setFocusPainted(false);
        //btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setContentAreaFilled(false); //*bug de windows
        btn.setOpaque(true);//*respeta diseño
    }

    public static void stylizerMenu(JMenu menu){
        menu.setContentAreaFilled(false);
        menu.setOpaque(true);
    }

    public static void stylizerMenuBar(JMenuBar menuBar){
        menuBar.setUI(new BasicMenuBarUI());
        menuBar.setOpaque(true);
        menuBar.setBorder(new EmptyBorder(0,0,0,0));
    }

    public static void stylizerArea(JTextArea area){
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        area.setMargin(new Insets(10, 10, 10 , 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    public static void stylizePanel(JMenuBar menuBar) {
        menuBar.setBackground(Color.GRAY);
    }

    public static void putWhiteMode(JTextArea area,  JMenuBar menuBar, JMenuItem... btns){
        Color negro = new Color(45,45,48);
        for(JMenuItem btn : btns){
            btn.setBackground(Color.WHITE);
            btn.setForeground(negro);
        }
        area.setBackground(Color.WHITE);
        area.setForeground(negro);
        area.setCaretColor(negro);
        menuBar.setBackground(Color.WHITE);
        menuBar.setForeground(negro);

    }
    public static void putDarkMode(JTextArea area, JMenuBar menuBar, JMenuItem... btns){
        Color negro = new Color(45,45,48);
        for(JMenuItem btn : btns) {
            btn.setBackground(negro);
            btn.setForeground(Color.WHITE);
        }
        area.setBackground(negro);
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        menuBar.setBackground(negro);
        menuBar.setForeground(Color.WHITE);
    }


}
