package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/*The entry point into the program
 * This class, together with "Lander.fxml" make for the VIEWS*/
public class Lander extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Hook up the FXML file
			Parent root = FXMLLoader.load(getClass().getResource("Lander.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			//The window won't be resizable
			primaryStage.setResizable(false);
			//Set title for the window
			primaryStage.setTitle("Create Your Army");
			//Set Icon
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("spartan.png")));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
