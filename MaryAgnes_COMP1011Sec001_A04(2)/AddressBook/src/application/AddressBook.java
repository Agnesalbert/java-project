package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddressBook extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/AddressBook.fxml")); 
            
            // Create a scene and set it on the stage
            Scene scene = new Scene(root);
            stage.setTitle("Address Book");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}