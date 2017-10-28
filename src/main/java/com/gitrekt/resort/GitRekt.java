
package com.gitrekt.resort;

import com.gitrekt.resort.controller.ScreenManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main application class shell.
 */

public class GitRekt extends Application {
    
    @Override
    public void start(Stage mainStage) throws IOException {
        ScreenManager screenManager = ScreenManager.getInstance();
        screenManager.initialize(mainStage);
        
        Parent root = 
            FXMLLoader.load(getClass().getResource("/fxml/HomeScreen.fxml"));
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        
        Image appLogo = new Image("images/Logo.png");
        mainStage.getIcons().add(appLogo);
        mainStage.setTitle("Git-Rektsort Booking Software");
        
        mainStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}