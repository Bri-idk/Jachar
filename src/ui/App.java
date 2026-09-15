package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        WindowManager app = new WindowManager(primaryStage);
        app.show();
    }
}