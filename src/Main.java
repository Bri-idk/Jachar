
//!My packages
import ui.WindowManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WindowManager app = new WindowManager();
            app.show();
        });
    }
}